package kr.co.bestiansoft.ebillservicekg.admin.billMng.service.impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.admin.billMng.repository.SystemBillMapper;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.service.SystemBillService;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class SystemBillServiceImpl implements SystemBillService {
	
    private final SystemBillMapper systemBillMapper;
    private final EDVHelper edv;
    
	@Override
	public SystemBillVo selectBillDetail(String billId, String lang) {
		return systemBillMapper.selectBillDetail(billId, lang);
	}

	@Transactional
	@Override
	public SystemBillVo createBillDetail(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(userId);
		systemBillVo.setClsCd("110");
		systemBillMapper.createBillDetail(systemBillVo);
		return systemBillVo;
	}

	@Override
	public List<EbsFileVo> selectOpinionFile(String billId) {
		return systemBillMapper.selectOpinionFile(billId);
	}

	@Transactional
	@Override
	public SystemBillVo createBillFile(SystemBillVo systemBillVo) {

		String[] fileKindCds = systemBillVo.getFileKindCds();
		String[] clsCds = systemBillVo.getClsCds();
		
		int idx = 0;
		for(MultipartFile file : systemBillVo.getFiles()) {
			String orgFileId = StringUtil.getUUUID();
			String orgFileNm = file.getOriginalFilename();
			String fileKindCd = fileKindCds[idx];
			String clsCd = clsCds[idx];
			
    		////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				edv.save(orgFileId, edvIs);
			} catch (Exception edvEx) {
				throw new RuntimeException("EDV_NOT_WORK", edvEx);
			}
    		////////////////////////
			
			EbsFileVo fileVo = new EbsFileVo();
			fileVo.setBillId(systemBillVo.getBillId());
			fileVo.setOrgFileId(orgFileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(file.getSize());
			fileVo.setDeleteYn("N");
			fileVo.setOpbYn("N");
			fileVo.setFileKindCd(fileKindCd);
			fileVo.setClsCd(clsCd);
			
			idx++;
			systemBillMapper.createBillFile(fileVo);
		}
		
		systemBillVo.setFiles(null);
		return systemBillVo;
	}

}