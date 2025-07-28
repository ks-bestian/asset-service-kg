package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.repository;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;

@Mapper
public interface DraftDocumentRepository {
    int insertDraftDocument(DraftDocumentVo vo);
    void updateDraftStatus(int aarsDocId, String aarsStatusCd);
    DraftDocumentVo getDraftDocument(int aarsDocId);
    void updateDraftPdfFileId(int aarsDocId, String pdfFileId);
}
