package kr.co.bestiansoft.ebillservicekg.eas.draftData.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataAndComFormFieldDto;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataVo;

public interface DraftDataService {
    void insertDraftData(DraftDataVo vo);
    List<DraftDataAndComFormFieldDto> getDraftData(int aarsDocId);
}
