package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.repository.MtngFromMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.service.MtngFromService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MtngFromVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.process.repository.ProcessMapper;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MtngFromServiceImpl implements MtngFromService {

    private final MtngFromMapper mtngFromMapper;
    private final ProcessService processService;
    private final ProcessMapper processMapper;

	/**
	 * Retrieves a list of MtngFromVo objects based on the given parameters.
	 *
	 * @param param a HashMap containing key-value pairs to filter and retrieve the list of MtngFromVo objects
	 * @return a List of MtngFromVo objects retrieved from the data source
	 */
    @Override
    public List<MtngFromVo> getMtngFromList(HashMap<String, Object> param) {
        List<MtngFromVo> result = mtngFromMapper.selectListMtngFrom(param);
        return result;
    }

	/**
	 * Retrieves meeting information including agendas, participants, and result documents by meeting ID.
	 *
	 * @param mtngId the unique identifier of the meeting
	 * @param param  a map containing additional parameters for the meeting query
	 * @return MtngFromVo an object containing meeting information, agendas, participants, and documents
	 */
    @Override
    public MtngFromVo getMtngFromById(Long mtngId, HashMap<String, Object> param) {

    	param.put("mtngId", mtngId);

    	/* meeting information*/
    	MtngFromVo dto = mtngFromMapper.selectMtngFrom(param);

    	/* Agenda  */
    	List<AgendaVo> agendaList = mtngFromMapper.selectListMtngAgenda(param);
    	dto.setAgendaList(agendaList);

    	/* participant - selectListMtngAttendant */
    	List<MemberVo> attendantList = mtngFromMapper.selectListMtngAttendant(param);
    	dto.setAttendantList(attendantList);

    	/*meeting result document*/
    	List<MtngFileVo> reportList = mtngFromMapper.selectListMtngFile(param);
    	dto.setReportList(reportList);

        return dto;
    }

	/**
	 * Creates a meeting from the given MtngFromVo object and stores it in the database.
	 * This includes registering the meeting details, agenda items, and participants.
	 *
	 * @param mtngFromVo an object that contains the details of the meeting, including
	 *                   meeting metadata, a list of agenda items, and a list of participants
	 * @return the updated MtngFromVo object with populated meeting IDs and other relevant data
	 */
    @Transactional
	@Override
	public MtngFromVo createMtngFrom(MtngFromVo mtngFromVo) {

		/* registrant id Set */
		String regId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setRegId(regId);

		/*meeting*/
		mtngFromMapper.insertEbsMtng(mtngFromVo);
		log.info("mtngId2 : {}", mtngFromVo.getMtngId());

		/*Agenda*/
		List<AgendaVo> agendaList = mtngFromVo.getAgendaList();
		int idx = 1;
		for(AgendaVo aVo:agendaList) {
			AgendaVo agendaVo = new AgendaVo();
			agendaVo.setBillId(aVo.getBillId());
			agendaVo.setMtngId(mtngFromVo.getMtngId());
			agendaVo.setOrd(idx++);
			agendaVo.setRegId(regId);
			mtngFromMapper.insertEbsMtngAgenda(agendaVo);
		}
		idx = 1;

		/*participant*/
		List<MemberVo> attendantList = mtngFromVo.getAttendantList();
		for(MemberVo memVo:attendantList) {
			MemberVo memberVo = new MemberVo();
			memberVo.setMtngId(mtngFromVo.getMtngId());
			memberVo.setAtdtUserId(memVo.getMemberId());
			memberVo.setAtdtUserNm(memVo.getMemberNm());
			memberVo.setAtdtKind(memVo.getAtdtKind());
			memberVo.setAtdtDeptNm(memVo.getPolyNm());
			memberVo.setAtdtPosition(memVo.getAtdtPosition());
			memberVo.setRegId(regId);
			mtngFromMapper.insertEbsMtngAttendant(memberVo);
		}

//		if("2".equals(mtngFromVo.getMtngTypeCd())) { //Plenary
//			for(AgendaVo aVo:agendaList) {
//				ProcessVo pVo = new ProcessVo();
//				pVo.setBillId(aVo.getBillId());
//				pVo.setStepId("1700");//Request for review of the plenary session
//				processService.handleProcess(pVo);
//			}
//		}

		return mtngFromVo;
	}


	/**
	 * Retrieves a list of members based on the given parameters.
	 *
	 * @param param a HashMap containing key-value pairs used as filter criteria for fetching the member list
	 * @return a list of MemberVo objects representing the members that match the filter criteria
	 */
	@Override
	public List<MemberVo> getMemberList(HashMap<String, Object> param) {
		return mtngFromMapper.selectListMember(param);
	}

	/**
	 * Retrieves a list of department members based on the provided parameters.
	 *
	 * @param param a HashMap containing the parameters required for querying the department list
	 * @return a list of MemberVo objects representing the members of the department
	 */
	@Override
	public List<MemberVo> getDeptList(HashMap<String, Object> param) {
		return mtngFromMapper.selectListDept(param);
	}

	/**
	 * Deletes meetings based on the provided list of meeting IDs. This method removes
	 * all associated data including agendas, attendants, and the meeting itself.
	 * Additionally, it performs process rollback in certain conditions.
	 *
	 * @param mtngIds the list of meeting IDs to be deleted
	 */
	@Transactional
	@Override
	public void deleteMtng(List<Long> mtngIds) {
		// TODO 회의 취소 - 알림발송 구현 해야함

        for (Long mtngId : mtngIds) {

        	HashMap<String, Object> param = new HashMap<>();
    		param.put("mtngId", mtngId);
    		MtngFromVo mtng = mtngFromMapper.selectMtngFrom(param);
    		//process Rollback
    		if("2".equals(mtng.getMtngTypeCd())) { //Plenary
    			List<AgendaVo> list = mtngFromMapper.selectListMtngAgenda(param);
    			if(list != null) {
    				for(AgendaVo agenda : list) {
    					String undoStepId = "1700"; //Request for review of the plenary session
    					if(undoStepId.equals(agenda.getCurrentStepId())) {
    						processService.undoProcess(agenda.getBillId(), undoStepId);
    					}
    				}
    			}
    		}

    		mtngFromMapper.deleteMtngFromAgenda(mtngId);
    		mtngFromMapper.deleteMtngFromAttendant(mtngId);
    		mtngFromMapper.deleteMtngFrom(mtngId);
        }

	}

	/**
	 * Retrieves a list of meeting bills based on the provided parameters, and for each bill,
	 * fetches and sets the associated meeting details.
	 *
	 * @param param a HashMap containing key-value pairs used as filters or conditions
	 *              to query the meeting bills.
	 * @return a list of BillMngVo objects, each containing meeting bill details and
	 *         a list of associated meetings.
	 */
	@Override
	public List<BillMngVo> selectListMtngBill(HashMap<String, Object> param) {

		List<BillMngVo> list = mtngFromMapper.selectListMtngBill(param);
		for(BillMngVo bill : list) {
			String billId = bill.getBillId();
			List<MtngFromVo> mtngList = mtngFromMapper.selectListMtngByBillId(billId);
			bill.setMtngList(mtngList);
		}
		return list;
	}

	/**
	 * Retrieves a list of BillMngVo objects, including a list of associated MtngFromVo objects for each bill.
	 *
	 * @param param a HashMap containing the parameters for filtering and retrieving the list of BillMngVo objects.
	 * @return a List of BillMngVo objects with their associated meeting information.
	 */
	@Override
	public List<BillMngVo> selectListMainMtngBill(HashMap<String, Object> param) {

		List<BillMngVo> list = mtngFromMapper.selectListMainMtngBill(param);
		for(BillMngVo bill : list) {
			String billId = bill.getBillId();
			List<MtngFromVo> mtngList = mtngFromMapper.selectListMtngByBillId(billId);
			bill.setMtngList(mtngList);
		}
		return list;
	}

	/**
	 * Updates the meeting bill along with its participants and agenda details.
	 * Ensures the relevant mappings are updated or corrected in the database.
	 *
	 * @param mtngFromVo an object containing the meeting details, participant list, and agenda information
	 * @return the updated MtngFromVo object with modifications
	 */
	@Transactional
	@Override
	public MtngFromVo updateMtngBill(MtngFromVo mtngFromVo) {

		String userId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setModId(userId);
		mtngFromMapper.updateFromMtngBill(mtngFromVo);

		//participant correction
		mtngFromMapper.deleteMtngFromBillAttendant(mtngFromVo);

		List<MemberVo> attendantList = mtngFromVo.getAttendantList();
		if(attendantList!=null) {
			for(MemberVo vo:attendantList) {
				MemberVo memberVo = new MemberVo();
				memberVo.setMtngId(mtngFromVo.getMtngId());
				memberVo.setAtdtUserId(vo.getMemberId());
				memberVo.setAtdtUserNm(vo.getMemberNm());
				memberVo.setAtdtKind(vo.getAtdtKind());
				memberVo.setRegId(userId);
				memberVo.setAtdtDeptNm(vo.getPolyNm());
				memberVo.setAtdtPosition(vo.getAtdtPosition());
				mtngFromMapper.insertEbsMtngAttendant(memberVo);
			}
		}

		//Agenda correction
//		mtngFromMapper.deleteMtngFromBillAgenda(mtngFromVo);
//
//		List<AgendaVo> agendaList = mtngFromVo.getAgendaList();
//		int idx = 1;
//		if(agendaList!=null) {
//
//			for(AgendaVo vo:agendaList) {
//				AgendaVo agendaVo = new AgendaVo();
//				agendaVo.setBillId(vo.getBillId());
//				agendaVo.setMtngId(mtngFromVo.getMtngId());
//				agendaVo.setOrd(idx++);
//				agendaVo.setRegId(userId);
//				mtngFromMapper.insertEbsMtngAgenda(agendaVo);
//			}
//		}

		Set<String> oldset = new HashSet<>();
		Set<String> newset = new HashSet<>();
		int ord = 0;

		HashMap<String, Object> param = new HashMap<>();
		param.put("mtngId", mtngFromVo.getMtngId());
		param.put("lang", mtngFromVo.getLang());
		List<AgendaVo> oldlist = mtngFromMapper.selectListMtngAgenda(param);
		if(oldlist != null) {
			for(AgendaVo agenda : oldlist) {
				oldset.add(agenda.getBillId());
				ord = Math.max(ord, agenda.getOrd());
			}
		}

		List<AgendaVo> newlist = mtngFromVo.getAgendaList();
		if(newlist != null) {
			for(AgendaVo agenda : newlist) {
				newset.add(agenda.getBillId());
			}
		}

		if(newlist != null) {
			for(AgendaVo vo : newlist) {
				if(!oldset.contains(vo.getBillId())) {
					AgendaVo agendaVo = new AgendaVo();
					agendaVo.setBillId(vo.getBillId());
					agendaVo.setMtngId(mtngFromVo.getMtngId());
					agendaVo.setOrd(++ord);
					agendaVo.setRegId(userId);
					mtngFromMapper.insertEbsMtngAgenda(agendaVo);
				}
			}
		}

		if(oldlist != null) {
			for(AgendaVo agenda : oldlist) {
				if(!newset.contains(agenda.getBillId())) {
					mtngFromMapper.deleteMtngAgenda(mtngFromVo.getMtngId(), agenda.getBillId());

					String undoStepId = "1700"; //Request for review of the plenary session
					if(undoStepId.equals(agenda.getCurrentStepId())) {
						processService.undoProcess(agenda.getBillId(), undoStepId);
					}
				}
			}
		}

		return mtngFromVo;
	}

	/**
	 * Retrieves a list of meetings associated with a specific bill ID.
	 *
	 * @param billId the unique identifier of the bill used to find associated meetings
	 * @return a list of MtngFromVo objects representing the meetings associated with the given bill ID
	 */
	@Transactional
	@Override
	public List<MtngFromVo> selectListMtngByBillId(String billId) {
		return mtngFromMapper.selectListMtngByBillId(billId);
	}

	/**
	 * Creates a hall meeting by inserting meeting and agenda details into the database.
	 *
	 * @param mtngFromVo an object containing the meeting details and agenda list to be created
	 * @return the updated MtngFromVo object after creation, including generated identifiers
	 */
	@Transactional
	@Override
	public MtngFromVo createHallMtng(MtngFromVo mtngFromVo) {
		/* registrant id Set */
		String regId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setRegId(regId);

		/*meeting*/
		mtngFromMapper.insertEbsMtng(mtngFromVo);

		/*Agenda*/
		List<AgendaVo> agendaList = mtngFromVo.getAgendaList();
		int idx = 1;
		for(AgendaVo aVo:agendaList) {
			AgendaVo agendaVo = new AgendaVo();
			agendaVo.setBillId(aVo.getBillId());
			agendaVo.setMtngId(mtngFromVo.getMtngId());
			agendaVo.setAgendaOrd(idx++);
			agendaVo.setRegId(regId);
			mtngFromMapper.insertEbsMtngAgenda(agendaVo);
		}
		idx = 1;

		return mtngFromVo;
	}

	/**
	 * Submits the agenda for a specified meeting by processing each agenda item in the meeting.
	 * The method checks whether the agenda is already submitted or if its current step matches a specific condition.
	 * For agenda items that meet the criteria, it processes them by updating their step and submission status.
	 *
	 * @param mtngId the unique identifier of the meeting whose agenda is to be submitted
	 */
    @Override
    public void submitMtngAgenda(Long mtngId) {
    	HashMap<String, Object> param = new HashMap<>();
		param.put("mtngId", mtngId);
    	List<AgendaVo> list = mtngFromMapper.selectListMtngAgenda(param);
    	for(AgendaVo agenda : list) {
    		if(Boolean.TRUE.equals(agenda.getSubmitted()) || "1700".equals(agenda.getCurrentStepId())) {
    			continue;
    		}
    		ProcessVo pVo = new ProcessVo();
			pVo.setBillId(agenda.getBillId());
			pVo.setStepId("1700");//Request for review of the plenary session
			processService.handleProcess(pVo);
			
			MtngFromVo vo = new MtngFromVo();
			vo.setSubmitted(true);
			vo.setMtngId(mtngId);
			vo.setBillId(agenda.getBillId());
			vo.setModId(new SecurityInfoUtil().getAccountId());
			mtngFromMapper.updateHallMtngSubmitted(vo);
    	}
    }

	/**
	 * Retrieves a list of meetings scheduled in a hall based on the provided parameters.
	 * Each meeting includes its associated list of agendas.
	 *
	 * @param param a HashMap containing the parameters to filter the meetings.
	 *              The keys and values in this map are used to construct the query.
	 * @return a list of MtngFromVo objects representing the meetings,
	 *         each including its associated list of AgendaVo items.
	 */
	@Override
	public List<MtngFromVo> getHallMtngList(HashMap<String, Object> param) {

		List<MtngFromVo> mtngList = mtngFromMapper.selectListHallMtng(param);
		HashMap<String, Object> agendaParam = new HashMap<String, Object>();

		for(MtngFromVo vo:mtngList) {

			Long mtngId = vo.getMtngId();
			agendaParam.put("mtngId", mtngId);

			List<AgendaVo> agendaList = mtngFromMapper.selectListAgenda(agendaParam);
			vo.setAgendaList(agendaList);
		}

        return mtngList;
	}

	/**
	 * Retrieves a list of hall meeting agendas based on the provided parameters.
	 *
	 * @param param a HashMap containing key-value pairs of parameters used to filter or locate hall meeting agendas
	 * @return a list of AgendaVo objects representing the hall meeting agendas
	 */
	@Override
	public List<AgendaVo> getHallMtngBillList(HashMap<String, Object> param) {
		List<AgendaVo> agendaList = mtngFromMapper.selectListHallMtngAgenda(param);
		return agendaList;
	}

	/**
	 * Updates the meeting result for a hall by modifying the provided MtngFromVo object.
	 * The method updates the entity with the current modifier's login ID and persists changes.
	 *
	 * @param mtngFromVo the MtngFromVo object containing the meeting result data to be updated
	 * @return the updated MtngFromVo object after the modification
	 */
	@Transactional
	@Override
	public MtngFromVo updateHallMtngResult(MtngFromVo mtngFromVo) {

		String loginId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setModId(loginId);
		mtngFromMapper.updateHallMtngResult(mtngFromVo);
		return mtngFromVo;
	}

	/**
	 * Updates the hall meeting order based on the provided meeting information.
	 *
	 * @param mtngFromVo a value object containing meeting details and a list of agendas
	 */
	@Override
	public void updateHallMtngOrd(MtngFromVo mtngFromVo) {
		List<AgendaVo> agendaList = mtngFromVo.getAgendaList();

		int idx = 1;
		for(AgendaVo avo : agendaList) {
			AgendaVo agendaVo = new AgendaVo();

			agendaVo.setBillId(avo.getBillId());
			agendaVo.setMtngId(avo.getMtngId());
			agendaVo.setOrd(idx++);
			agendaVo.setModId(new SecurityInfoUtil().getAccountId());
			mtngFromMapper.updateHallMtngOrd(agendaVo);
		}
		idx=1;
	}

	/**
	 * Deletes the hall meeting bill associated with the provided meeting information.
	 * This method validates the existence of related agendas and processes before deletion.
	 * If the associated agenda is marked as submitted, the process is undone before proceeding.
	 *
	 * @param mtngFromVo The meeting information object containing details of the meeting and bill to be deleted.
	 * @return The updated meeting information object after successfully deleting the hall meeting bill.
	 * @throws IllegalArgumentException If no agenda is found for the specified meeting ID.
	 */
	@Transactional
	@Override
	public MtngFromVo deleteHallMtngBill(MtngFromVo mtngFromVo) {

		String loginId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setModId(loginId);

		HashMap<String, Object> param = new HashMap<>();
		param.put("mtngId", mtngFromVo.getMtngId());
		List<AgendaVo> list = mtngFromMapper.selectListMtngAgenda(param);
		if(list == null || list.isEmpty()) {
			throw new IllegalArgumentException();
		}
		AgendaVo agenda = list.get(0);
		if(Boolean.TRUE.equals(agenda.getSubmitted())) {
//			ProcessVo pVo = new ProcessVo();
//			pVo.setBillId(mtngFromVo.getBillId());
//			pVo.setStepId("1600");//committee Meeting
//			processService.handleProcess(pVo);

			processService.undoProcess(mtngFromVo.getBillId(), "1700");
		}
		
		mtngFromMapper.deleteMtngAgenda(mtngFromVo.getMtngId(),mtngFromVo.getBillId());

		return mtngFromVo;
	}

	/**
	 * Updates a hall meeting record with the provided meeting details, sets the modifier ID,
	 * updates the meeting bill, and processes the agenda list by inserting agendas with
	 * specific details into the database.
	 *
	 * @param mtngFromVo the meeting information object containing details to update the hall meeting
	 *                   and associated agendas
	 * @return the updated meeting information object after processing
	 */
	@Override
	public MtngFromVo updateHallMtng(MtngFromVo mtngFromVo) {

		String userId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setModId(userId);
		mtngFromMapper.updateFromMtngBill(mtngFromVo);

		/*Agenda*/
		int idx = 1;
		List<AgendaVo> agendaList = mtngFromVo.getAgendaList();

		for(AgendaVo aVo:agendaList) {
			AgendaVo agendaVo = new AgendaVo();
			agendaVo.setBillId(aVo.getBillId());
			agendaVo.setMtngId(mtngFromVo.getMtngId());
			agendaVo.setRegId(userId);
			agendaVo.setOrd(idx++);
			mtngFromMapper.insertEbsMtngAgenda(agendaVo);
		}
		idx=1;
		
		return null;
	}

	/**
	 * Adds the agenda items for the specified meeting.
	 *
	 * @param mtngFromVo an instance of {@code MtngFromVo} containing the meeting ID
	 *                   and a list of agenda items to be added. Each agenda item
	 *                   includes details such as the bill ID, agenda order, and more.
	 */
	@Override
	public void addHallMtngAgenda(MtngFromVo mtngFromVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		/*Agenda*/
		List<AgendaVo> agendaList = mtngFromVo.getAgendaList();
		for(AgendaVo aVo:agendaList) {
			AgendaVo agendaVo = new AgendaVo();
			agendaVo.setBillId(aVo.getBillId());
			agendaVo.setMtngId(mtngFromVo.getMtngId());
			agendaVo.setRegId(userId);
			agendaVo.setAgendaOrd(aVo.getAgendaOrd());
			mtngFromMapper.insertEbsMtngAgenda(agendaVo);
		}
	}

}