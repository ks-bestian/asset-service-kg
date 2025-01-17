package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngToVo;

@Mapper
public interface MtngToMapper {
    List<MtngToVo> selectListMtngTo (HashMap<String, Object> param);
    MtngToVo selectMtngTo(HashMap<String, Object> param);
    List<MemberVo> selectListMtngAttendant (HashMap<String, Object> param);
    List<AgendaVo> selectListMtngAgenda (HashMap<String, Object> param);
    Long insertEbsMtng(MtngToVo mtngToVo);
    List<MemberVo> selectListMember(HashMap<String, Object> param);
    void insertEbsMtngAttendant(MemberVo memberVo);
    void insertEbsMtngAgenda(AgendaVo agendaVo);
    void deleteMtngTo(Long mtngId);
    void deleteMtngToAttendant(Long mtngId);
    void deleteMtngToAgenda(Long mtngId);
}
