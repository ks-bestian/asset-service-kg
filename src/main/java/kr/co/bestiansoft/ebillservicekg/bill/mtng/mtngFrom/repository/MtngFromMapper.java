package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MtngFromVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;

@Mapper
public interface MtngFromMapper {
    List<MtngFromVo> selectListMtngFrom (HashMap<String, Object> param);
    MtngFromVo selectMtngFrom(HashMap<String, Object> param);
    List<MemberVo> selectListMtngAttendant (HashMap<String, Object> param);
    List<AgendaVo> selectListMtngAgenda (HashMap<String, Object> param);
    Long insertEbsMtng(MtngFromVo mtngFromVo);
    List<MemberVo> selectListMember(HashMap<String, Object> param);
    List<MemberVo> selectListDept(HashMap<String, Object> param);
    void insertEbsMtngAttendant(MemberVo memberVo);
    void insertEbsMtngAgenda(AgendaVo agendaVo);
    void deleteMtngFrom(Long mtngId);
    void deleteMtngFromAttendant(Long mtngId);
    void deleteMtngFromAgenda(Long mtngId);

    List<BillMngVo> selectListMtngBill(HashMap<String, Object> param);
    List<BillMngVo> selectListMainMtngBill(HashMap<String, Object> param);



}
