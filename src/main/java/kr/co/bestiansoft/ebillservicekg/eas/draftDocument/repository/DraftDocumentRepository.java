package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.repository;

import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DraftDocumentRepository {
    int insertDraftDocument(DraftDocumentVo vo);
    void updateDraftStatus(int aarsDocId, String aarsStatusCd);
    DraftDocumentVo getDraftDocument(int aarsDocId);
}
