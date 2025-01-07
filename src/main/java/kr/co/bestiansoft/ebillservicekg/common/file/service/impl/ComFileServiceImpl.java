package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.file.repository.ComFileMapper;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ComFileServiceImpl implements ComFileService {
    private final EDVHelper edv;
    private final ComFileMapper fileMapper;

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
    		//executorService.submit(new FileUploadTask(file,fileEntity));

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
	public List<ComFileVo> getFileList(String fileGroupId) {
		return fileMapper.findByFileGroupId(fileGroupId);
	}

	@Override
	public ComFileVo getFile(String fileId) {
		return fileMapper.findByFileId(fileId);
	}
    
}