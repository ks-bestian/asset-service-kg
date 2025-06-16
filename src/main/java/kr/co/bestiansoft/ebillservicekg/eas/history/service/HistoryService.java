package kr.co.bestiansoft.ebillservicekg.eas.history.service;

import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;

import java.util.List;

public interface HistoryService {
    int insertHistory(HistoryVo historyVo);
    String getActionDetail(String actionType, String userNm);
    List<HistoryVo> getHistory(String docId);
    List<HistoryVo> getHistoryByUserId(String docId);
}
