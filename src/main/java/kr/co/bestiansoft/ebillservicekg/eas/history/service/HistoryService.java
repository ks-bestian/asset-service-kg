package kr.co.bestiansoft.ebillservicekg.eas.history.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;

public interface HistoryService {
    int insertHistory(HistoryVo historyVo);
    String getActionDetail(String actionType, String userNm);
    List<HistoryVo> getHistory(String docId);
    List<HistoryVo> getHistoryByUserId(String docId);
    void deleteDocument(String docId);
}
