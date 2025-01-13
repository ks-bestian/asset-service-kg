package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.file.repository.ComFileMapper;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ComFileServiceImpl implements ComFileService {

	private static final Logger logger = LoggerFactory.getLogger(ComFileServiceImpl.class);
    private final EDVHelper edv;
    private final ComFileMapper fileMapper;
	private final ExecutorService executorService;

	@Override
	public String saveFile(MultipartFile[] files) {
		String fileGroupId = StringUtil.getUUUID();

		for(MultipartFile file:files) {

			String fileId = StringUtil.getUUUID();
    		String orgFileNm = file.getOriginalFilename();

    		////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				edv.save(fileId, edvIs);
			} catch (Exception edvEx) {
				throw new RuntimeException("EDV_NOT_WORK", edvEx);
			}
    		////////////////////////

			ComFileVo fileVo = new ComFileVo();
			fileVo.setFileGroupId(fileGroupId);
			fileVo.setFileId(fileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(file.getSize());
			fileVo.setUploadYn("Y");
			fileVo.setDeleteYn("N");

			fileMapper.insertFile(fileVo);
		}
		return fileGroupId;
	}

	@Override
	public void saveFileEbs(MultipartFile[] files, String[] fileKindCdList, String billId) {

		String[] fileKindCds = fileKindCdList;
		int idx = 0;
		for(MultipartFile file:files) {

			String orgFileId = StringUtil.getUUUID();
    		String orgFileNm = file.getOriginalFilename();
    		String fileKindCd = fileKindCds[idx];

    		////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				edv.save(orgFileId, edvIs);
			} catch (Exception edvEx) {
				throw new RuntimeException("EDV_NOT_WORK", edvEx);
			}
    		////////////////////////

			EbsFileVo fileVo = new EbsFileVo();
			fileVo.setBillId(billId);

			fileVo.setOrgFileId(orgFileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(file.getSize());
			fileVo.setDeleteYn("N");
			fileVo.setOpbYn("N");
			fileVo.setFileKindCd(fileKindCd);
			idx++;
			fileMapper.insertFileEbs(fileVo);
		}
	}

	@Override
	public List<ComFileVo> getFileList(String fileGroupId) {
		return fileMapper.findByFileGroupId(fileGroupId);
	}

	@Override
	public ComFileVo getFile(String fileId) {
		return fileMapper.findByFileId(fileId);
	}

	@Override
	public void batchFileDelete() {
		List<ComFileVo> fileList = fileMapper.batchGetDeleteFileList();
		for(ComFileVo file : fileList) {
			executorService.submit(new FileDeleteTask(file.getFileId()));
		}
	}


	private class FileDeleteTask implements Callable<String> {

        private String fileId;

        public FileDeleteTask(String fileId) {
            this.fileId = fileId;
        }

        @Override
        public String call() {

    		try {
            	edv.delete(this.fileId);

            	fileMapper.deleteServerFile(this.fileId);
            	logger.info("Success to delete file edv: {}", this.fileId);
                return "SUCCESS";
            } catch (Exception e) {
            	logger.error("Failed to delete file edv: {}", this.fileId , e);
            	return "FAIL";
            }
        }

    }

}