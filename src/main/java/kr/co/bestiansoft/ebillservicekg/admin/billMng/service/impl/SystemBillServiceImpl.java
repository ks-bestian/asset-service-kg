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
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.repository.AgreeMapper;
import kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo.AgreeVo;
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
    private final AgreeMapper agreeMapper;
    private final EDVHelper edv;

	/**
	 * Fetches the detailed information of a bill along with its associated files.
	 *
	 * @param billId The unique identifier of the bill to fetch details for.
	 * @param param A HashMap used to include additional parameters for querying the bill details.
	 * @return A SystemBillResponse object containing the bill details and associated file list.
	 */
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

	/**
	 * Creates or updates the details of a system bill.
	 * If a bill with the specified details does not exist, it creates a new bill record.
	 * Otherwise, it updates the existing bill record with the provided details.
	 *
	 * @param systemBillVo the SystemBillVo object containing bill details such as bill ID and classification code
	 * @return the updated SystemBillVo object after the operation is performed
	 */
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

	/**
	 * Retrieves a list of opinion files associated with a specific bill ID.
	 *
	 * @param billId the unique identifier of the bill for which the opinion files are to be retrieved
	 * @return a list of EbsFileVo objects representing the opinion files associated with the given bill ID
	 */
	@Override
	public List<EbsFileVo> selectOpinionFile(String billId) {
		return systemBillMapper.selectBillFile(billId);
	}

	/**
	 * Creates and saves bill files associated with the provided system bill information.
	 *
	 * @param systemBillVo The SystemBillVo object containing the details of the bill, including files to be saved.
	 *                     It should also include information such as file types and classification codes.
	 * @return The updated SystemBillVo object, with any modifications or updates applied during the file creation process.
	 *         The files property of the returned object will be set to null after processing.
	 * @throws RuntimeException If there is an issue processing or saving the files.
	 */
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

	/**
	 * Updates the details of a system bill. If no existing bill details are found, creates a new bill record.
	 * Additionally, processes and stores the associated files for the system bill.
	 *
	 * @param systemBillVo The SystemBillVo object containing information about the system bill
	 *                     to be updated or created, including its ID, classification codes,
	 *                     and associated files to be saved.
	 * @return The updated SystemBillVo object with necessary modifications such as additional
	 *         file details and user identifiers.
	 */
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

	/**
	 * Retrieves bill meeting details and associated files based on the provided bill ID and parameters.
	 *
	 * @param billId the ID of the bill for which the meeting details are to be retrieved
	 * @param param a map containing additional parameters required for the query
	 * @return a SystemBillResponse object containing the meeting details, associated files, and comments-related files
	 */
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

	/**
	 * Creates and processes meeting-related files based on the provided SystemBillVo object.
	 * This method includes operations such as saving file details, uploading files, and updating database records.
	 *
	 * @param systemBillVo The SystemBillVo object containing information about the bill and associated files.
	 *                     It includes details like bill ID, classification codes, file kinds, and the list of files to process.
	 * @return A SystemBillVo object with updated data after the files are processed and stored.
	 */
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

	/**
	 * Creates and validates a department-related system bill by processing the provided data,
	 * including associated files, and saving the information to the database.
	 *
	 * @param systemBillVo the SystemBillVo object containing the attributes for the system bill
	 *                     such as bill ID, remarks, file information, etc.
	 * @return the updated SystemBillVo object after processing and saving the data.
	 */
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

	/**
	 * Updates the remark (rmk) of files associated with a given bill in the system.
	 * Iterates through the provided remarks to update each corresponding file's information.
	 *
	 * @param systemBillVo the SystemBillVo object containing the bill and file details, including the remarks to be updated
	 * @return the updated SystemBillVo object with the modifications applied
	 */
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

	/**
	 * Creates a master comment for the provided system bill.
	 *
	 * @param systemBillVo the SystemBillVo object containing the necessary details to create the master comment
	 * @return the updated SystemBillVo object after the master comment has been created
	 */
	@Override
	public SystemBillVo createMasterCmt(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(userId);
		systemBillVo.setCmtSeCd("R");

		systemBillMapper.createMasterCmt(systemBillVo);
		return systemBillVo;
	}

	/**
	 * Retrieves a list of bills and associated files based on the given bill ID and parameters.
	 *
	 * @param billId The unique identifier of the bill to retrieve.
	 * @param param A HashMap containing additional parameters required for the query.
	 * @return A SystemBillResponse object containing the list of bills and associated files.
	 */
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

	/**
	 * Creates or updates a maintenance master record and its associated details,
	 * including bill-related files, in the system.
	 *
	 * @param systemBillVo An instance of SystemBillVo containing the data
	 *                     for the maintenance master and associated files.
	 *                     Includes details like bill ID, classification codes,
	 *                     and file information.
	 * @return The updated SystemBillVo instance containing the processed data
	 *         after creation or update operations are completed.
	 * @throws RuntimeException If an error occurs while saving a file in the external EDV system.
	 */
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

	/**
	 * Retrieves the bill-related meeting list and associated file list based on the provided bill ID
	 * and additional parameters.
	 *
	 * @param billId The unique identifier of the bill.
	 * @param param A map containing additional parameters for the query.
	 * @return A SystemBillResponse object containing the meeting list and associated file list.
	 */
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

	/**
	 * Creates or updates a related meeting based on the provided {@code SystemBillVo} object.
	 * Updates details and file-related information and saves it to the database.
	 *
	 * @param systemBillVo the object containing information about the system bill,
	 *                     including details and associated files
	 * @return the updated {@code SystemBillVo} object with relevant information
	 */
	@Override
	public SystemBillVo cretaeRelateMtng(SystemBillVo systemBillVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		systemBillVo.setRegId(userId);
		systemBillVo.setModId(userId);

		HashMap<String, Object> param = new HashMap<>();
		param.put("billId", systemBillVo.getBillId());
		param.put("clsCd", systemBillVo.getClsCd());

		Long seq = 0L;
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

	/**
	 * Retrieves government bill details and associated files based on the provided bill ID
	 * and additional parameters.
	 *
	 * @param billId the unique identifier of the bill to retrieve
	 * @param param a map containing additional parameters for the query
	 * @return a {@code SystemBillResponse} object containing the bill details and associated files
	 */
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

	/**
	 * This method is responsible for creating or updating a government system bill based on the provided
	 * {@code systemBillVo} data. It manages the bill details, assigns necessary user IDs, handles file uploads,
	 * and interacts with the database to save the bill and associated files.
	 *
	 * @param systemBillVo The SystemBillVo object containing the details of the bill to be created or updated.
	 *                     It includes information such as bill ID, classification codes, file metadata, and uploaded files.
	 * @return A SystemBillVo object with updated or created bill details, confirming changes and any modifications.
	 */
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

	/**
	 * Retrieves the detailed information of a specific bill application.
	 *
	 * @param billId the unique identifier of the bill.
	 * @param lang the language in which the bill details are requested.
	 * @return a {@link SystemBillResponse} object containing the detailed bill information,
	 *         including bill details, file list, and proposer list.
	 */
	@Override
	public SystemBillResponse getApplyDetail(String billId, String lang) {

		SystemBillResponse result = new SystemBillResponse();

		//Agenda particular
		SystemBillVo billDetail = systemBillMapper.selectApplyDetail(billId, lang);
		result.setBillDetail(billDetail);

		//file List
		List<EbsFileVo> fileList = systemBillMapper.selectBillFile(billId);
		result.setFileList(fileList);

		//Voter Target
		List<AgreeVo> proposerList = agreeMapper.selectAgreeProposerList(billId);
		result.setProposerList(proposerList);

		return result;
	}
}