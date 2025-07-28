package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalLIstDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.DocumentDetailDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.DocumentListDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.DocumentUserDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.SearchDocumentVo;

@Mapper
public interface OfficialDocumentMapper {
    List<DocumentListDto> getDocumentList(SearchDocumentVo vo);
    int saveOfficialDocument(OfficialDocumentVo vo);
    int updateStatus(String docId, String docStatusCd);
    int countDocumentList(String userId);
    List<DocumentUserDto> getDocumentUser(String docId);
    DocumentDetailDto getDocumentDetail(String docId);
    boolean isReject(String docId);
    List<DocumentListDto> getRejectDocumentList(SearchDocumentVo vo);
    int countRejectDocument(String userId);
    List<DocumentListDto> getMyDocumentList(SearchDocumentVo vo);
    List<DocumentListDto> getWorkList(SearchDocumentVo vo);
    int countWorkList(String userId);
    List<DocumentListDto> getProcessedList(SearchDocumentVo vo);
    void deleteDocument(String docId);
    List<ApprovalLIstDto> getApprovalList(SearchDocumentVo vo);
    int countApprovalList(String userId);
}
