package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo.MtngAllVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;

@Mapper
public interface MtngAllMapper {
    List<MtngAllVo> selectListMtngAll (HashMap<String, Object> param);
    MtngAllVo selectMtngAll(HashMap<String, Object> param);
    List<MemberVo> selectListMtngAttendant (HashMap<String, Object> param);
    List<AgendaVo> selectListMtngAgenda (HashMap<String, Object> param);
    List<MtngFileVo> selectListMtngFile (HashMap<String, Object> param);
    List<MtngAllVo> selectMtngByBillId(HashMap<String, Object> param);
}
