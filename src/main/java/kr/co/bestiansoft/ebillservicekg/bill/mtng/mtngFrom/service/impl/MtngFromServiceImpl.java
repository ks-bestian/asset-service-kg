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

    @Override
    public List<MtngFromVo> getMtngFromList(HashMap<String, Object> param) {
        List<MtngFromVo> result = mtngFromMapper.selectListMtngFrom(param);
        return result;
    }

    @Override
    public MtngFromVo getMtngFromById(Long mtngId, HashMap<String, Object> param) {

    	param.put("mtngId", mtngId);

    	/* 회의 정보*/
    	MtngFromVo dto = mtngFromMapper.selectMtngFrom(param);

    	/* 안건  */
    	List<AgendaVo> agendaList = mtngFromMapper.selectListMtngAgenda(param);
    	dto.setAgendaList(agendaList);

    	/* 참석자 - selectListMtngAttendant */
    	List<MemberVo> attendantList = mtngFromMapper.selectListMtngAttendant(param);
    	dto.setAttendantList(attendantList);

    	/*회의 결과 문서*/
    	List<MtngFileVo> reportList = mtngFromMapper.selectListMtngFile(param);
    	dto.setReportList(reportList);

        return dto;
    }

    @Transactional
	@Override
	public MtngFromVo createMtngFrom(MtngFromVo mtngFromVo) {

		/* 등록자 아이디 세팅 */
		String regId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setRegId(regId);

		/*회의*/
		mtngFromMapper.insertEbsMtng(mtngFromVo);
		log.info("mtngId2 : {}", mtngFromVo.getMtngId());

		/*안건*/
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

		/*참석자*/
		List<MemberVo> attendantList = mtngFromVo.getAttendantList();
		for(MemberVo memVo:attendantList) {
			MemberVo memberVo = new MemberVo();
			memberVo.setMtngId(mtngFromVo.getMtngId());
			memberVo.setAtdtUserId(memVo.getMemberId());
			memberVo.setAtdtUserNm(memVo.getMemberNm());
			memberVo.setAtdtKind(memVo.getAtdtKind());
			memberVo.setAtdtDeptNm(memVo.getPolyNm());
			memberVo.setRegId(regId);
			mtngFromMapper.insertEbsMtngAttendant(memberVo);
		}

//		if("2".equals(mtngFromVo.getMtngTypeCd())) { //본회의
//			for(AgendaVo aVo:agendaList) {
//				ProcessVo pVo = new ProcessVo();
//				pVo.setBillId(aVo.getBillId());
//				pVo.setStepId("1700");//본회의심사요청
//				processService.handleProcess(pVo);
//			}
//		}

		return mtngFromVo;
	}



	@Override
	public List<MemberVo> getMemberList(HashMap<String, Object> param) {
		return mtngFromMapper.selectListMember(param);
	}

	@Override
	public List<MemberVo> getDeptList(HashMap<String, Object> param) {
		return mtngFromMapper.selectListDept(param);
	}

	@Transactional
	@Override
	public void deleteMtng(List<Long> mtngIds) {
		// TODO 회의 취소 - 알림발송 구현 해야함

        for (Long mtngId : mtngIds) {

        	HashMap<String, Object> param = new HashMap<>();
    		param.put("mtngId", mtngId);
    		MtngFromVo mtng = mtngFromMapper.selectMtngFrom(param);
    		//프로세스 롤백
    		if("2".equals(mtng.getMtngTypeCd())) { //본회의
    			List<AgendaVo> list = mtngFromMapper.selectListMtngAgenda(param);
    			if(list != null) {
    				for(AgendaVo agenda : list) {
    					String undoStepId = "1700"; //본회의심사요청
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

	@Transactional
	@Override
	public MtngFromVo updateMtngBill(MtngFromVo mtngFromVo) {

		String userId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setModId(userId);
		mtngFromMapper.updateFromMtngBill(mtngFromVo);

		//참석자 수정
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
				mtngFromMapper.insertEbsMtngAttendant(memberVo);
			}
		}

		//안건 수정
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

					String undoStepId = "1700"; //본회의심사요청
					if(undoStepId.equals(agenda.getCurrentStepId())) {
						processService.undoProcess(agenda.getBillId(), undoStepId);
					}
				}
			}
		}

		return mtngFromVo;
	}

	@Transactional
	@Override
	public List<MtngFromVo> selectListMtngByBillId(String billId) {
		return mtngFromMapper.selectListMtngByBillId(billId);
	}



	/*Hall meeting*/

	@Transactional
	@Override
	public MtngFromVo createHallMtng(MtngFromVo mtngFromVo) {
		/* 등록자 아이디 세팅 */
		String regId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setRegId(regId);

		/*회의*/
		mtngFromMapper.insertEbsMtng(mtngFromVo);

		/*안건*/
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
			pVo.setStepId("1700");//본회의심사요청
			processService.handleProcess(pVo);
			
			MtngFromVo vo = new MtngFromVo();
			vo.setSubmitted(true);
			vo.setMtngId(mtngId);
			vo.setBillId(agenda.getBillId());
			vo.setModId(new SecurityInfoUtil().getAccountId());
			mtngFromMapper.updateHallMtngSubmitted(vo);
    	}
    }

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

	@Override
	public List<AgendaVo> getHallMtngBillList(HashMap<String, Object> param) {
		List<AgendaVo> agendaList = mtngFromMapper.selectListHallMtngAgenda(param);
		return agendaList;
	}

	@Transactional
	@Override
	public MtngFromVo updateHallMtngResult(MtngFromVo mtngFromVo) {

		String loginId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setModId(loginId);
		mtngFromMapper.updateHallMtngResult(mtngFromVo);
		return mtngFromVo;
	}

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
//			pVo.setStepId("1600");//위원회 회의심사보고
//			processService.handleProcess(pVo);

			processService.undoProcess(mtngFromVo.getBillId(), "1700");
		}
		
		mtngFromMapper.deleteMtngAgenda(mtngFromVo.getMtngId(),mtngFromVo.getBillId());

		return mtngFromVo;
	}

	@Override
	public MtngFromVo updateHallMtng(MtngFromVo mtngFromVo) {

		String userId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setModId(userId);
		mtngFromMapper.updateFromMtngBill(mtngFromVo);

		/*안건*/
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
	
	@Override
	public void addHallMtngAgenda(MtngFromVo mtngFromVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		/*안건*/
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