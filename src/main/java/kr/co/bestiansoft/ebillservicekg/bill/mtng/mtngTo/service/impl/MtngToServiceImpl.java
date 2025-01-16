package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.repository.MtngToMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.MtngToService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngToVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MtngToServiceImpl implements MtngToService {
    private final MtngToMapper mtngToMapper;

    @Override
    public List<MtngToVo> getMtngToList(HashMap<String, Object> param) {
        List<MtngToVo> result = mtngToMapper.selectListMtngTo(param);
        return result;
    }

    @Override
    public MtngToVo getMtngToById(Long mtngId, HashMap<String, Object> param) {
    	
    	param.put("mtngId", mtngId);
    	
    	/* 회의 정보*/
    	MtngToVo dto = mtngToMapper.selectMtngTo(param);
    	
    	/* 안건  */
    	List<AgendaVo> agendaList = mtngToMapper.selectListMtngAgenda(param);
    	dto.setAgendaList(agendaList);
    	
    	/* 참석자 - selectListMtngAttendant */
    	List<MemberVo> attendantList = mtngToMapper.selectListMtngAttendant(param);
    	dto.setAttendantList(attendantList);
    	
        return dto;
    }

	@Override
	public MtngToVo createMtngTo(MtngToVo mtngToVo) {
		
		/* 등록자 아이디 세팅 */
		String regId = new SecurityInfoUtil().getAccountId();
		mtngToVo.setRegId(regId);
		
		/*회의*/
		mtngToMapper.insertEbsMtng(mtngToVo);
		log.info("mtngId2 : {}", mtngToVo.getMtngId());
		
		/*안건*/
		List<AgendaVo> agendaList = mtngToVo.getAgendaList();
		for(int i=0;i<agendaList.size();i++) {
			log.info("billId : {}", agendaList.get(i).getBillId());
			log.info("agenda : {}", agendaList.get(i));
			AgendaVo agendaVo = new AgendaVo();
			agendaVo.setBillId(agendaList.get(i).getBillId());
			agendaVo.setMtngId(mtngToVo.getMtngId());
			agendaVo.setOrd(i+1);
			agendaVo.setRegId(regId);
			agendaList.set(i, agendaVo);
			mtngToMapper.insertEbsMtngAgenda(agendaList.get(i));
		}
		
		/*참석자*/
		List<MemberVo> attendantList = mtngToVo.getAttendantList();
		for(int i=0;i<attendantList.size();i++) {
			log.info("attendant : {}", attendantList.get(i));
			//attendantList.get(i).setMtngId(mtngId);
			MemberVo memberVo = new MemberVo();
			memberVo.setMtngId(mtngToVo.getMtngId());
			memberVo.setAtdtUserId(attendantList.get(i).getMemberId());
			memberVo.setAtdtUserNm(attendantList.get(i).getMemberNmKg());
			memberVo.setAtdtKind("ATT01");// 참석자 구분(의원)
			memberVo.setRegId(regId);
			attendantList.set(i, memberVo);
			mtngToMapper.insertEbsMtngAttendant(attendantList.get(i));
		}
		
		return mtngToVo;
	}

	@Override
	public List<MemberVo> getMemberList(HashMap<String, Object> param) {
		return mtngToMapper.selectListMember(param);
	}

	@Override
	public void deleteMtng(List<Long> mtngIds) {
		// TODO 회의 취소 - 알림발송 구현 해야함
		
        for (Long mtngId : mtngIds) {
    		mtngToMapper.deleteMtngToAgenda(mtngId);
    		mtngToMapper.deleteMtngToAttendant(mtngId);
    		mtngToMapper.deleteMtngTo(mtngId);
        }
        
	}
}