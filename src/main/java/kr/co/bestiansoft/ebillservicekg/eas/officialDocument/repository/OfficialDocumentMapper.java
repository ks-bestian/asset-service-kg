package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.repository;

import com.aspose.cells.DateTime;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalLIstDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
