package kr.co.bestiansoft.ebillservicekg.eas.draftData.repository;

import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataAndComFormFieldDto;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataVo;

import java.util.List;

public interface DraftDataRepository {
    void insertDraftData(DraftDataVo vo);
    List<DraftDataVo> getDraftData(int aarsDocId);
    List<DraftDataAndComFormFieldDto> getDraftDataAndComFormField(int aarsDocId);
}
