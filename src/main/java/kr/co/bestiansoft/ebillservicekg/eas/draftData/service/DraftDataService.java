package kr.co.bestiansoft.ebillservicekg.eas.draftData.service;

import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataVo;

import java.util.List;

public interface DraftDataService {
    void insertDraftData(DraftDataVo vo);
    List<DraftDataVo> getDraftData(int aarsDocId);
}
