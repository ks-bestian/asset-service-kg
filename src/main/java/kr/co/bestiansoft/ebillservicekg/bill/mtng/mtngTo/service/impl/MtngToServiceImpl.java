package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.slides.internal.ax.av;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.repository.MtngToMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.MtngToService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.repository.MtngAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.repository.MtngFromMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngToVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.repository.BillMngMapper;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MtngToServiceImpl implements MtngToService {

	private final MtngFromMapper mtngFromMapper;
	private final MtngToMapper mtngToMapper;
    private final MtngAllMapper mtngAllMapper;
    private final ComFileService comFileService;
    private final ProcessService processService;
    private final BillMngMapper billMngMapper;

	/**
	 * Retrieves a list of MtngToVo objects based on the specified parameters.
	 *
	 * @param param a HashMap containing the parameters for retrieving the list of MtngToVo objects
	 * @return a List of MtngToVo objects matching the specified parameters
	 */
    @Override
    public List<MtngToVo> getMtngToList(HashMap<String, Object> param) {
        List<MtngToVo> result = mtngToMapper.selectListMtngTo(param);
        return result;
    }

	/**
	 * Retrieves meeting information, including associated reports, agendas, and participants,
	 * based on the meeting ID provided.
	 *
	 * @param mtngId the unique identifier of the meeting
	 * @param param a map containing additional parameters for retrieval, where the method
	 *              will also add the meeting ID for querying
	 * @return an instance of {@code MtngToVo} containing detailed meeting information,
	 *         such as reports, agendas, and participants
	 */
    @Override
    public MtngToVo getMtngToById(Long mtngId, HashMap<String, Object> param) {

    	param.put("mtngId", mtngId);

    	/* meeting information*/
    	MtngToVo dto = mtngToMapper.selectMtngTo(param);

    	/*meeting result document*/
    	List<MtngFileVo> reportList = mtngAllMapper.selectListMtngFile(param);
    	dto.setReportList(reportList);

    	/* Agenda  */
    	List<AgendaVo> agendaList = mtngToMapper.selectListMtngAgenda(param);
    	dto.setAgendaList(agendaList);

    	/* participant - selectListMtngAttendant */
    	List<MemberVo> attendantList = mtngToMapper.selectListMtngAttendant(param);
    	String names = attendantList.stream().map(MemberVo::getMemberNm).collect(Collectors.joining(", "));
    	dto.setAttendants(names);
    	dto.setAttendantList(attendantList);

        return dto;
    }

	/**
	 * Creates and updates a meeting result based on the given meeting data.
	 *
	 * @param paramVo The {@link MtngToVo} object containing the meeting data, including
	 *                agenda list, attendant list, and file information.
	 * @return The updated {@link MtngToVo} object after applying changes and persisting data.
	 * @throws Exception If an error occurs during processing or data handling.
	 */
    @Transactional
	@Override
	public MtngToVo createMtngResult(MtngToVo paramVo) throws Exception {

		String loginUserId = new SecurityInfoUtil().getAccountId();

		String jsonString = paramVo.getJsonData();
		ObjectMapper objectMapper = new ObjectMapper();
		MtngToVo mtngToVo = objectMapper.readValue(jsonString, MtngToVo.class);

		List<AgendaVo> agendaList = mtngToVo.getAgendaList();
		List<MemberVo> attendantList = mtngToVo.getAttendantList();

		mtngToVo.setRegId(loginUserId);
		mtngToVo.setModId(loginUserId);
		mtngToMapper.updateMtngTo(mtngToVo);

		//participant correction
		mtngToMapper.deleteMtngToAttendant(mtngToVo);

		for(MemberVo vo:attendantList) {
			vo.setRegId(loginUserId);
			vo.setMtngId(mtngToVo.getMtngId());
			vo.setAtdtDeptNm(vo.getDeptNm());
			vo.setAtdtUserId(vo.getMemberId());
			vo.setAtdtUserNm(vo.getMemberNm());
			mtngToMapper.insertEbsMtngAttendant(vo);
		}

		//Agenda correction
		mtngToMapper.deleteMtngToAgenda(mtngToVo);

		int idx = 1;
		for(AgendaVo vo:agendaList) {
			vo.setMtngId(mtngToVo.getMtngId());
			vo.setOrd(idx++);
			vo.setRegId(loginUserId);
			mtngToMapper.insertMtngToAgenda(vo);
		}

		//meeting Results file registration
		comFileService.saveFileEbsMtng(paramVo.getFiles(), paramVo.getFileKindCds(), mtngToVo.getMtngId());

		//addition - my In the document box file Upload(20250221 Jinho Cho)
		comFileService.saveFileEbsMtng(paramVo.getMyFileIds(), paramVo.getFileKindCds2(), mtngToVo.getMtngId());

//		if("2".equals(mtngToVo.getMtngTypeCd())) { //Plenary
//			for(AgendaVo aVo:agendaList) {
//				ProcessVo pVo = new ProcessVo();
//				pVo.setBillId(aVo.getBillId());
//				pVo.setStepId("1900");//Legal behavior management
//				processService.handleProcess(pVo);	
//			}
//		}
		
		return mtngToVo;
	}


//    @Override
//    public void sendLegalActMtngAgenda(Long mtngId) {
//    	HashMap<String, Object> param = new HashMap<>();
//		param.put("mtngId", mtngId);
//    	List<AgendaVo> list = mtngFromMapper.selectListMtngAgenda(param);
//    	for(AgendaVo agenda : list) {
//    		if("1900".equals(agenda.getCurrentStepId())) {
//    			continue;
//    		}
//    		ProcessVo pVo = new ProcessVo();
//			pVo.setBillId(agenda.getBillId());
//			pVo.setStepId("1900");//Legal behavior management
//			processService.handleProcess(pVo);
//    	}
//    }

	/**
	 * Sends the agenda list for a legal act meeting.
	 * Processes each agenda item by validating and updating its state
	 * based on the current step in the legal management process flow.
	 *
	 * @param agendaList the list of agenda items to be processed. Each item contains details about a bill.
	 *                   If the list is null, processing is skipped.
	 */
	@Override
    public void sendLegalActMtngAgenda(List<AgendaVo> agendaList) {
    	if(agendaList == null) {
    		return;
    	}
    	for(AgendaVo agenda : agendaList) {
//    		BillMngVo vo = new BillMngVo();
//    		vo.setBillId(agenda.getBillId());
//    		BillMngVo bill = billMngMapper.selectOneBill(vo);
    		
    		HashMap<String, Object> param = new HashMap<>();
    		param.put("billId", agenda.getBillId());
    		BillMngVo bill = billMngMapper.selectBill(param);
    		
    		if(!"1700".equals(bill.getCurrentStepId()) && !"1800".equals(bill.getCurrentStepId())) {
    			continue;
    		}
    		ProcessVo pVo = new ProcessVo();
			pVo.setBillId(agenda.getBillId());
			pVo.setStepId("1900");//Legal behavior management
			processService.handleProcess(pVo);
    	}
    }


	/**
	 * Processes the reporting of a meeting by updating its status and handling additional logic such as
	 * updating processes related to agendas within the meeting.
	 *
	 * @param mtngFromVo the meeting transfer object containing original data, including JSON data of the meeting details
	 * @return an updated meeting transfer object after processing and status updates
	 * @throws Exception if any error occurs during processing or JSON deserialization
	 */
	@Override
	public MtngToVo reportMtngTo(MtngToVo mtngFromVo) throws Exception {

		String userId = new SecurityInfoUtil().getAccountId();

		String jsonString = mtngFromVo.getJsonData();
		ObjectMapper objectMapper = new ObjectMapper();
		MtngToVo agendaVo = objectMapper.readValue(jsonString, MtngToVo.class);
		String deptCd = agendaVo.getDeptCd();//commitee or edt deptCd
		Long mtngId = agendaVo.getMtngId();
		String statCd = agendaVo.getStatCd();

		MtngToVo mtParam = new MtngToVo();
		mtParam.setMtngId(mtngId);
		mtParam.setModId(userId);
		mtParam.setStatCd(statCd);
		mtngToMapper.updateMtngToStatus(mtParam);// commitee meeting status update

		for(AgendaVo aVo :agendaVo.getAgendaList()) {

			if("M".equals(aVo.getCmtSeCd())) {// competent committee
				ProcessVo pVo = new ProcessVo();
				pVo.setBillId(aVo.getBillId());
				pVo.setStepId("1500");//Registration of the results of the committee
				processService.handleProcess(pVo);
			} else {//Relevant committee

			}
		}

		return agendaVo;
	}

	/**
	 * Retrieves a list of member information based on the provided parameters.
	 *
	 * @param param a HashMap containing the parameters used to filter the list of members
	 * @return a list of MemberVo objects representing the members that match the given parameters
	 */
	@Override
	public List<MemberVo> getMemberList(HashMap<String, Object> param) {
		return mtngToMapper.selectListMember(param);
	}

	/**
	 * Deletes meetings and associated data for the given list of meeting IDs.
	 *
	 * @param mtngIds a list of meeting IDs to be deleted
	 */
	@Override
	public void deleteMtng(List<Long> mtngIds) {
		// TODO 회의 취소 - 알림발송 구현 해야함

        for (Long mtngId : mtngIds) {
    		mtngToMapper.deleteMtngToAgenda(mtngId);
    		mtngToMapper.deleteMtngToAttendant(mtngId);
    		mtngToMapper.deleteMtngTo(mtngId);
        }

	}

	/**
	 * Updates meeting file deletion information in the database.
	 *
	 * @param param a HashMap containing the parameters needed for the update operation,
	 *              including details required for meeting file deletion.
	 * @return an integer representing the number of rows affected by the update.
	 */
	@Override
	public int updateMtngFileDel(HashMap<String, Object> param) {
		String modId = new SecurityInfoUtil().getAccountId();
		param.put("modId", modId);
		return mtngToMapper.updateMtngFileDel(param);
	}

}