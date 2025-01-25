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
		
		List<EbsFileVo> fileList = systemBillMapper.selectBillFile(billId);
		response.setFileList(fileList);
		
		return response;
	}

	@Transactional
	@Override
	public SystemBillVo createBillDetail(SystemBillVo systemBillVo) {
		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", systemBillVo.getBillId());
		param.put("clsCd", systemBillVo.getClsCd());
		
		SystemBillVo billDetail = systemBillMapper.selectBillDetail(param);
		
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(userId);
		
		if(billDetail == null) {
			systemBillMapper.createBillDetail(systemBillVo);
		} else {
			systemBillMapper.updateBillDetail(systemBillVo);
		}
		
		return systemBillVo;
	}
	
	@Override
	public List<EbsFileVo> selectOpinionFile(String billId) {
		return systemBillMapper.selectBillFile(billId);
	}

	@Transactional
	@Override
	public SystemBillVo createBillFile(SystemBillVo systemBillVo) {
		String regId = new SecurityInfoUtil().getAccountId();
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
				fileVo.setRegId(regId);
				
				idx++;
				systemBillMapper.createBillFile(fileVo);
			}
		}
		systemBillVo.setFiles(null);
		return systemBillVo;
	}

	@Override
	public SystemBillVo updateBillLegal(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		
		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", systemBillVo.getBillId());
		param.put("clsCd", systemBillVo.getClsCd());
		
		SystemBillVo billDetail = systemBillMapper.selectBillDetail(param);
		if(billDetail == null) {
			systemBillVo.setRegId(userId);
			systemBillMapper.createBillDetail(systemBillVo);
		} else {
			systemBillVo.setModId(userId);
			systemBillMapper.updateBillDetail(systemBillVo);
		}
		
		String[] fileKindCds = systemBillVo.getFileKindCds();
		String[] clsCds = systemBillVo.getClsCds();
		
		int idx = 0;
		if (systemBillVo.getFiles() != null) {
//			createBillFile(systemBillVo);
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

	@Override
	public SystemBillResponse selectBillMtng(String billId, HashMap<String, Object> param) {
		SystemBillResponse response = new SystemBillResponse();
		param.put("billId", billId);
		
		List<SystemBillVo> mtngList = systemBillMapper.selectBillMtngList(param);
		response.setMtngList(mtngList);
		
		List<EbsFileVo> fileList = systemBillMapper.selectBillFile(billId);
		response.setFileList(fileList);

		List<EbsFileVo> cmtFileList = systemBillMapper.selectBillFileByCmt(param);
		response.setCmtFileList(cmtFileList);
		return response;
	}
	
	@Override
	public SystemBillVo createMtngFile(SystemBillVo systemBillVo) {
		String regId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(regId);
		
		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", systemBillVo.getBillId());
		param.put("clsCd", systemBillVo.getClsCd());
		
		SystemBillVo billDetail = systemBillMapper.selectBillDetail(param);
		if(billDetail == null) {
			systemBillMapper.createBillDetail(systemBillVo);
		}
		
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
	public SystemBillVo createValidationDept(SystemBillVo systemBillVo) {
		String regId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(regId);
		String[] rmks = systemBillVo.getRmks();
		String[] fileKindCds = systemBillVo.getFileKindCds();
		String[] clsCds = systemBillVo.getClsCds();
		
		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", systemBillVo.getBillId());
		
		int idx = 0;
		if (systemBillVo.getFiles() != null) {
			for(MultipartFile file : systemBillVo.getFiles()) {
				String orgFileId = StringUtil.getUUUID();
				String orgFileNm = file.getOriginalFilename();
				String fileKindCd = fileKindCds[idx];
				String clsCd = clsCds[idx];
				String rmk = rmks[idx];
				
				//ebs_master_detail
				param.put("clsCd", clsCd);
				
				SystemBillVo billDetail = systemBillMapper.selectBillDetail(param);
				if(billDetail == null) {
					systemBillVo.setClsCd(clsCd);
					systemBillMapper.createBillDetail(systemBillVo);
				}
				
				
				//ebs_file
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
				fileVo.setRmk(rmk);
				
				idx++;
				systemBillMapper.createBillFile(fileVo);
			}
		}
		systemBillVo.setFiles(null);
		return systemBillVo;
	}

	@Transactional
	@Override
	public SystemBillVo updateFileRmk(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		String[] orgFileIds = systemBillVo.getOrgFileIds();
		
	    int index = 0;
	    for (String rmk : systemBillVo.getRmks()) {
	        String orgFileId = orgFileIds[index];
	        
	        EbsFileVo fileVo = new EbsFileVo();
	        fileVo.setBillId(systemBillVo.getBillId());
	        fileVo.setOrgFileId(orgFileId);
	        fileVo.setRmk(rmk);
	        fileVo.setModId(userId);
	        
	        systemBillMapper.updateFileRmk(fileVo);
	        index++;
	    }
		return systemBillVo;
	}

	@Override
	public SystemBillVo createMasterCmt(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(userId);
		systemBillVo.setCmtSeCd("R");

		systemBillMapper.createMasterCmt(systemBillVo);
		return systemBillVo;
	}

	@Override
	public SystemBillResponse selectBillMtnList(String billId, HashMap<String, Object> param) {
		SystemBillResponse response = new SystemBillResponse();
		param.put("billId", billId);
		
		List<SystemBillVo> mtngList = systemBillMapper.selectMtnBillList(param);
		response.setMtngList(mtngList);
		
		List<EbsFileVo> fileList = systemBillMapper.selectBillFile(billId);
		response.setFileList(fileList);

		return response;
	}

	@Override
	public SystemBillVo createMtnMaster(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(userId);
		systemBillVo.setModId(userId);
		
		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", systemBillVo.getBillId());
		param.put("clsCd", systemBillVo.getClsCd());
		
		SystemBillVo billDetail = systemBillMapper.selectBillDetail(param);
		if(billDetail == null) {
			systemBillMapper.createBillDetail(systemBillVo);
		} else {
			systemBillMapper.updateBillDetail(systemBillVo);
		}
		
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

	@Override
	public SystemBillResponse selectBillRelationMtngList(String billId, HashMap<String, Object> param) {
		SystemBillResponse response = new SystemBillResponse();
		param.put("billId", billId);
		
		List<SystemBillVo> mtngList = systemBillMapper.selectBillRelationMtngList(param);
		response.setMtngList(mtngList);
		
		List<EbsFileVo> fileList = systemBillMapper.selectBillFile(billId);
		response.setFileList(fileList);

		return response;
	}
	
	@Override
	public SystemBillVo cretaeRelateMtng(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(userId);
		systemBillVo.setModId(userId);
		
		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", systemBillVo.getBillId());
		param.put("clsCd", systemBillVo.getClsCd());
		
		int seq = 0;
		SystemBillVo billDetail = systemBillMapper.selectBillDetail(param);
		if(billDetail == null) {
			systemBillMapper.createBillDetail(systemBillVo);
			seq = systemBillVo.getSeq();
		} else {
			systemBillMapper.updateBillDetail(systemBillVo);
			seq = billDetail.getSeq();
		}
		
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
				fileVo.setDetailSeq(seq);
				
				idx++;
				systemBillMapper.createBillFile(fileVo);
			}
		}
		
		systemBillVo.setFiles(null);
		return systemBillVo;
	}
	
	@Override
	public SystemBillResponse selectBillGoverment(String billId, HashMap<String, Object> param) {
		SystemBillResponse response = new SystemBillResponse();
		param.put("billId", billId);
		
		List<SystemBillVo> billDetailList = systemBillMapper.selectBillGovermentList(param);
		response.setBillDetailList(billDetailList);
		
		List<EbsFileVo> fileList = systemBillMapper.selectBillFile(billId);
		response.setFileList(fileList);

		return response;
	}
	
	@Override
	public SystemBillVo cretaeGoverment(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(userId);
		systemBillVo.setModId(userId);
		
		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", systemBillVo.getBillId());
		param.put("clsCd", systemBillVo.getClsCd());
		
		SystemBillVo billDetail = systemBillMapper.selectBillDetail(param);
		if(billDetail == null) {
			systemBillMapper.createBillDetail(systemBillVo);
		} else {
			systemBillMapper.updateBillDetail(systemBillVo);
		}
		
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