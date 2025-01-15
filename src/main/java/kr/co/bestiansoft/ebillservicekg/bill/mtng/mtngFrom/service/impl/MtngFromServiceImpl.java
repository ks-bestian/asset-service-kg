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
    	
        return dto;
    }

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
		for(int i=0;i<agendaList.size();i++) {
			log.info("billId : {}", agendaList.get(i).getBillId());
			log.info("agenda : {}", agendaList.get(i));
			AgendaVo agendaVo = new AgendaVo();
			agendaVo.setBillId(agendaList.get(i).getBillId());
			agendaVo.setMtngId(mtngFromVo.getMtngId());
			agendaVo.setOrd(i+1);
			agendaVo.setRegId(regId);
			agendaList.set(i, agendaVo);
			mtngFromMapper.insertEbsMtngAgenda(agendaList.get(i));
		}
		
		/*참석자*/
		List<MemberVo> attendantList = mtngFromVo.getAttendantList();
		for(int i=0;i<attendantList.size();i++) {
			log.info("attendant : {}", attendantList.get(i));
			//attendantList.get(i).setMtngId(mtngId);
			MemberVo memberVo = new MemberVo();
			memberVo.setMtngId(mtngFromVo.getMtngId());
			memberVo.setAtdtUserId(attendantList.get(i).getMemberId());
			memberVo.setAtdtUserNm(attendantList.get(i).getMemberNmKg());
			memberVo.setAtdtKind("ATT01");// 참석자 구분(의원)
			memberVo.setRegId(regId);
			attendantList.set(i, memberVo);
			mtngFromMapper.insertEbsMtngAttendant(attendantList.get(i));
		}
		
		return mtngFromVo;
	}

	@Override
	public List<MemberVo> getMemberList(HashMap<String, Object> param) {
		return mtngFromMapper.selectListMember(param);
	}
}