package kr.co.bestiansoft.ebillservicekg.eas.history.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;

@Mapper
public interface HistoryRepository {
    int insertHistory (HistoryVo vo);
    List<HistoryVo> getHistory (String docId);
    List<HistoryVo> getHistoryByUserId (String docId, String userId);
    void deleteDocument(String docId);
}
