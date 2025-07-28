package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalLIstDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.DocumentDetailDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.DocumentListDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.DocumentUserDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.SearchDocumentVo;

public interface OfficialDocumentService {

    List<DocumentListDto> getDocumentList(SearchDocumentVo vo);
    int saveOfficialDocument(OfficialDocumentVo vo);
    int updateStatusOfficialDocument(String docId, String status);
    int countDocumentList();
    DocumentDetailDto getDocumentDetail(String docId);
    List<DocumentUserDto> getDocumentUser(String docId);
    Boolean isReject(String docId);
    List<DocumentListDto> getRejectDocumentList(SearchDocumentVo vo);
    int countRejectDocument();
    List<DocumentListDto> getMyDocumentList(SearchDocumentVo vo);
    List<DocumentListDto> getWorkList(SearchDocumentVo vo);
    List<DocumentListDto> getProcessedList(SearchDocumentVo vo);
    void deleteDocument(String docId);
    int countWorkList();
    int countApprovalList();
    List<ApprovalLIstDto> getApprovalList(SearchDocumentVo vo);
}
