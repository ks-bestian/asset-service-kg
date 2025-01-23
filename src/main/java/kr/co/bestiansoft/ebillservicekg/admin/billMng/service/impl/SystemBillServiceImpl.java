package kr.co.bestiansoft.ebillservicekg.admin.billMng.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.admin.billMng.repository.SystemBillMapper;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.service.SystemBillService;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillResponse;
import kr.co.bestiansoft.ebillservicekg.admin.billMng.vo.SystemBillVo;
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
	public SystemBillResponse selectBillDetail(String billId, HashMap<String, Object> param) {
		SystemBillResponse response = new SystemBillResponse();
		
		param.put("billId", billId);
		SystemBillVo billDetail = systemBillMapper.selectBillDetail(param);
		response.setBillDetail(billDetail);
		
		List<EbsFileVo> fileList = systemBillMapper.selectOpinionFile(billId);
		response.setFileList(fileList);
		
		return response;
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
	
	@Transactional
	@Override
	public SystemBillVo updateBillDetail(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setClsCd("110");
		systemBillVo.setModId(userId);
		systemBillMapper.updateBillDetail(systemBillVo);
		return systemBillVo;
	}

	@Override
	public List<EbsFileVo> selectOpinionFile(String billId) {
		return systemBillMapper.selectOpinionFile(billId);
	}

	@Transactional
	@Override
	public SystemBillVo createBillFile(SystemBillVo systemBillVo) {
		String regId = new SecurityInfoUtil().getAccountId();
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
			fileVo.setRegId(regId);
			
			idx++;
			systemBillMapper.createBillFile(fileVo);
		}
		
		systemBillVo.setFiles(null);
		return systemBillVo;
	}

	@Override
	public SystemBillVo createBillLegal(SystemBillVo systemBillVo) {
		String regId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(regId);
		systemBillMapper.createBillDetail(systemBillVo);
		
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
			fileVo.setRegId(regId);
			
			idx++;
			systemBillMapper.createBillFile(fileVo);
		}
		
		systemBillVo.setFiles(null);
		return systemBillVo;
	}
	
	@Override
	public SystemBillVo updateBillLegal(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setModId(userId);
		systemBillMapper.updateBillDetail(systemBillVo);
		
		String[] fileKindCds = systemBillVo.getFileKindCds();
		String[] clsCds = systemBillVo.getClsCds();
		
		int idx = 0;
		if (systemBillVo.getFiles() != null) {
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
				fileVo.setRegId(userId);
				
				idx++;
				systemBillMapper.createBillFile(fileVo);
			}
		}
		
		systemBillVo.setFiles(null);
		return systemBillVo;
	}

}