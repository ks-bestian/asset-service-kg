package kr.co.bestiansoft.ebillservicekg.eas.history.repository;

import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryRepository {
    int insertHistory (HistoryVo vo);
    List<HistoryVo> getHistory (String docId);
}
