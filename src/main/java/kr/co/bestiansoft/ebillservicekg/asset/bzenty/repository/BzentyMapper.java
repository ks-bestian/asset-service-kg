package kr.co.bestiansoft.ebillservicekg.asset.bzenty.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo.BzentyVo;

@Mapper
public interface BzentyMapper {

    List<BzentyVo> selectListBzenty(HashMap<String, Object> param);
    BzentyVo bzentyDetail(HashMap<String, Object> param);
    BzentyVo selectBzenty(Long seq);
    int insertBzenty(BzentyVo BzentyVo);
    int updateBzenty(BzentyVo BzentyVo);
    void deleteBzenty(String bzentyId);

}
