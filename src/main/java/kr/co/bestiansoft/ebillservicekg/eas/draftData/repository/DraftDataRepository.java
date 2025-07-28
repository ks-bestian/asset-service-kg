package kr.co.bestiansoft.ebillservicekg.eas.draftData.repository;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataAndComFormFieldDto;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataVo;

public interface DraftDataRepository {
    void insertDraftData(DraftDataVo vo);
    List<DraftDataVo> getDraftData(int aarsDocId);
    List<DraftDataAndComFormFieldDto> getDraftDataAndComFormField(int aarsDocId);
}
