package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngToVo;

public interface MtngToService {

    List<MtngToVo> getMtngToList(HashMap<String, Object> param);
    MtngToVo getMtngToById(Long mtngId, HashMap<String, Object> param);
    MtngToVo createMtngResult(MtngToVo mtngFromVo) throws Exception;
    MtngToVo reportMtngTo(MtngToVo mtngFromVo) throws Exception ;

    List<MemberVo> getMemberList(HashMap<String, Object> param);
    void deleteMtng(List<Long> mtngIds);
	int updateMtngFileDel(HashMap<String, Object> param);

//	void sendLegalActMtngAgenda(Long mtngId);
	void sendLegalActMtngAgenda(List<AgendaVo> agendaList);
}
