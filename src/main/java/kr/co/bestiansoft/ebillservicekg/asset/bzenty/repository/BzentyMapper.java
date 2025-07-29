package kr.co.bestiansoft.ebillservicekg.asset.bzenty.repository;

import kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo.BzentyVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BzentyMapper {

    List<BzentyVo> selectListBzenty(HashMap<String, Object> param);
    BzentyVo bzentyDetail(HashMap<String, Object> param);
    BzentyVo selectBzenty(String bzentyId);
    int insertBzenty(BzentyVo BzentyVo);
    int updateBzenty(BzentyVo BzentyVo);
    void deleteBzenty(String bzentyId);

}
