package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.repository.MtngAllMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.service.MtngAllService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.repository.MtngFromMapper;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MtngAllServiceImpl implements MtngAllService {

    private final MtngAllMapper mtngAllMapper;
    private final MtngFromMapper mtngFromMapper;

    @Override
    public List<MtngAllVo> getMtngList(HashMap<String, Object> param) {
        List<MtngAllVo> result = mtngAllMapper.selectListMtngAll(param);
        return result;
    }

    @Override
    public MtngAllVo getMtngById(Long mtngId, HashMap<String, Object> param) {
    	param.put("mtngId", mtngId);

    	/* 회의 정보*/
    	MtngAllVo dto = mtngAllMapper.selectMtngAll(param);

    	/*회의 결과 문서*/

    	List<MtngFileVo> reportList = mtngAllMapper.selectListMtngFile(param);
    	dto.setReportList(reportList);

    	/* 안건  */
    	List<AgendaVo> agendaList = mtngAllMapper.selectListMtngAgenda(param);
    	dto.setAgendaList(agendaList);

    	/* 참석자 - selectListMtngAttendant */
    	List<MemberVo> attendantList = mtngAllMapper.selectListMtngAttendant(param);
    	dto.setAttendantList(attendantList);

        return dto;
    }

	@Override
	public List<MemberVo> getMtngParticipants(HashMap<String, Object> param) {
    	List<MemberVo> attendantList = mtngFromMapper.selectListMtngAttendant(param);
		return attendantList;
	}
}