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

	/**
	 * Retrieves a list of meeting information based on the provided parameters.
	 *
	 * @param param a HashMap containing various filtering criteria for retrieving the meeting list
	 * @return a list of MtngAllVo objects representing the meeting information
	 */
    @Override
    public List<MtngAllVo> getMtngList(HashMap<String, Object> param) {
        List<MtngAllVo> result = mtngAllMapper.selectListMtngAll(param);
        return result;
    }

	/**
	 * Retrieves detailed information about a specific meeting based on the provided meeting ID.
	 * The method fetches the meeting details, associated reports, agendas, and participants.
	 *
	 * @param mtngId the unique identifier of the meeting to retrieve
	 * @param param a HashMap containing additional parameters and context needed for fetching the meeting details
	 * @return an MtngAllVo object containing the meeting details, including its report list, agenda list, and participant list
	 */
    @Override
    public MtngAllVo getMtngById(Long mtngId, HashMap<String, Object> param) {
    	param.put("mtngId", mtngId);

    	/* meeting information*/
    	MtngAllVo dto = mtngAllMapper.selectMtngAll(param);

    	/*meeting result document*/

    	List<MtngFileVo> reportList = mtngAllMapper.selectListMtngFile(param);
    	dto.setReportList(reportList);

    	/* Agenda  */
    	List<AgendaVo> agendaList = mtngAllMapper.selectListMtngAgenda(param);
    	dto.setAgendaList(agendaList);

    	/* participant - selectListMtngAttendant */
    	List<MemberVo> attendantList = mtngAllMapper.selectListMtngAttendant(param);
    	dto.setAttendantList(attendantList);

        return dto;
    }

	/**
	 * Retrieves a list of meeting participants based on the provided parameters.
	 *
	 * @param param a HashMap containing criteria to filter the participants of the meeting
	 * @return a list of MemberVo objects representing the participants of the meeting
	 */
	@Override
	public List<MemberVo> getMtngParticipants(HashMap<String, Object> param) {
    	List<MemberVo> attendantList = mtngFromMapper.selectListMtngAttendant(param);
		return attendantList;
	}

	/**
	 * Retrieves a list of meetings associated with the given bill ID.
	 *
	 * @param param a HashMap containing the criteria for filtering meetings, including the bill ID
	 * @return a list of MtngAllVo objects representing the meetings associated with the specified bill ID
	 */
	@Override
	public List<MtngAllVo> selectMtngByBillId(HashMap<String, Object> param) {
		List<MtngAllVo> result = mtngAllMapper.selectMtngByBillId(param);
		return result;
	}
}