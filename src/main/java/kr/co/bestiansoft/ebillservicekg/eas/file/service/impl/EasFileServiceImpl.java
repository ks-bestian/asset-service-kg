package kr.co.bestiansoft.ebillservicekg.eas.file.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.file.service.PdfService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.eas.file.repository.EasFileRepository;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.SaveFileDto;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.UpdatePdfFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;


@RequiredArgsConstructor
@Slf4j
@Service
public class EasFileServiceImpl implements EasFileService {

    private final EasFileRepository easFileRepository;
    private final EDVHelper edv;
    private final PdfService pdfService;
    private final ExecutorService executorService;

    @Override
    public int uploadEasFile(EasFileVo vo){
        String userId = new SecurityInfoUtil().getAccountId();
        vo.setRegId(userId);
        vo.setRegDt(LocalDateTime.now());
        vo.setDeleteYn('N');

        for(MultipartFile file : vo.getFiles()){
            EasFileVo fileVo = createFileVoFrom(vo);

            fileVo.fromSaveFileDto(saveFile(file), fileVo);

            saveEasFile(fileVo);

            if (!isPdfFile(fileVo.getFileExt())) {
                String fileId = fileVo.getFileId();
                executorService.submit(() -> {
                    try {
                        UpdatePdfFileDto pdfDto = savePdfFile(file);
                        updatePdfInfo(fileId, pdfDto);
                        log.info("PDF 변환 완료: 원본 파일 ID {}, PDF 파일 ID {}", fileId, pdfDto.getPdfFileId());
                    } catch (Exception e) {
                        log.error("PDF 변환 실패: 파일 ID {}, 오류: {}", fileId, e.getMessage(), e);
                    }
                });
            }
        }
        return 0;
    }

    public int saveEasFile(EasFileVo vo){
        return easFileRepository.insertEasFile(vo);
    }
    @Transactional
    public void updatePdfInfo(String fileId, UpdatePdfFileDto dto){
        EasFileVo fileVo = easFileRepository.getFileById(fileId);
        if (fileVo != null) {
            // PDF 정보 업데이트
            fileVo.setPdfFileId(dto.getPdfFileId());
            fileVo.setPdfFileNm(dto.getPdfFileNm());

            easFileRepository.updatePdfFileInfo(fileVo);
        }

    }
    @Override
    public List<EasFileVo> getAttachFiles(String docId) {
        return easFileRepository.getAttachFiles(docId);
    }

    public SaveFileDto saveFile(MultipartFile file){
        SaveFileDto result = new SaveFileDto();
        String fileId = StringUtil.getUUUID();

        try(InputStream edvIs = file.getInputStream()){
            edv.save(fileId, edvIs);
        }catch (Exception e){
            throw new RuntimeException("EDV_NOT_WORK", e);
        }

        String originalFilename  = file.getOriginalFilename();
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);


        result.setFileExt(fileExt);
        result.setFileId(fileId);
        result.setFileSize((int) file.getSize());
        result.setOrgFileName(originalFilename);

        return result;
    }

    public UpdatePdfFileDto savePdfFile(MultipartFile file) {
        UpdatePdfFileDto result = new UpdatePdfFileDto();
        String fileId = StringUtil.getUUUID();
        String fileName = file.getOriginalFilename();
        File tmpFile = null;
        File tmpPdfFile = null;

        try{
            tmpFile =File.createTempFile("temp", null);
            tmpPdfFile = File.createTempFile("pdfTemp", null);
            file.transferTo(tmpFile);
            boolean pdfResult = pdfService.convertToPdf(tmpFile.getAbsolutePath(), fileName, tmpPdfFile.getAbsolutePath());
            if(pdfResult){
                edv.save(fileId, new FileInputStream(tmpPdfFile));
            }
        }catch (Exception e){
            throw new RuntimeException("FILE_NOT_SAVE", e);
        }finally {
            tmpFile.delete();
            tmpPdfFile.delete();
        }

        result.setPdfFileId(fileId);
        result.setPdfFileNm(fileName);
        return result;
    }
    public boolean isPdfFile(String fileExt){
        return fileExt.equals(".pdf") || fileExt.equals(".PDF");
    }
    public EasFileVo createFileVoFrom(EasFileVo easFileVo){
        return  EasFileVo.builder()
                .docId(easFileVo.getDocId())
                .fileType(easFileVo.getFileType())
                .deleteYn(easFileVo.getDeleteYn())
                .regId(easFileVo.getRegId())
                .regDt(easFileVo.getRegDt())
                .build();
    }
}
