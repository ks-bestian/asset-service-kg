package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.service.impl;

import java.util.HashMap;
import java.util.List;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MtngFromServiceImpl implements MtngFromService {

    private final MtngFromMapper mtngFromMapper;

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
			memberVo.setRegId(regId);
			mtngFromMapper.insertEbsMtngAttendant(memberVo);
		}

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

	@Override
	public void deleteMtng(List<Long> mtngIds) {
		// TODO 회의 취소 - 알림발송 구현 해야함

        for (Long mtngId : mtngIds) {
    		mtngFromMapper.deleteMtngFromAgenda(mtngId);
    		mtngFromMapper.deleteMtngFromAttendant(mtngId);
    		mtngFromMapper.deleteMtngFrom(mtngId);
        }

	}

	@Override
	public List<BillMngVo> selectListMtngBill(HashMap<String, Object> param) {

		List<BillMngVo> list = mtngFromMapper.selectListMtngBill(param);
		return list;
	}

	@Transactional
	@Override
	public MtngFromVo updateMtngBill(MtngFromVo mtngFromVo) {
		String userId = new SecurityInfoUtil().getAccountId();
		mtngFromVo.setModId(userId);
		mtngFromMapper.updateFromMtngBill(mtngFromVo);
		
		//존재하는 행인 경우 업데이트 존재하지 않으면 인서트
		
		//참석자 수정
		mtngFromMapper.deleteMtngFromBillAttendant(mtngFromVo);

		List<MemberVo> attendantList = mtngFromVo.getAttendantList();
		if(attendantList!=null) {
			for(int i=0;i<attendantList.size();i++) {
				MemberVo memberVo = new MemberVo();
				memberVo.setMtngId(mtngFromVo.getMtngId());
				memberVo.setAtdtUserId(attendantList.get(i).getMemberId());
				memberVo.setAtdtUserNm(attendantList.get(i).getMemberNm());
				memberVo.setAtdtKind(attendantList.get(i).getAtdtKind());
				memberVo.setRegId(userId);
				attendantList.set(i, memberVo);
				mtngFromMapper.updateMtngFromAttendant(attendantList.get(i));
			}
		}
		
		//안건 수정
		mtngFromMapper.deleteMtngFromBillAgenda(mtngFromVo);

		List<AgendaVo> agendaList = mtngFromVo.getAgendaList();
		if(agendaList!=null) {
			for(int i=0;i<agendaList.size();i++) {
				AgendaVo agendaVo = new AgendaVo();
				agendaVo.setBillId(agendaList.get(i).getBillId());
				agendaVo.setMtngId(mtngFromVo.getMtngId());
				agendaVo.setOrd(i+1);
				agendaVo.setRegId(userId);
				agendaList.set(i, agendaVo);
				mtngFromMapper.updateMtngFromAgenda(agendaList.get(i));
			}
		}
		
		return mtngFromVo;
	}


}