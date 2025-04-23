package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.repository;

import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OfficialDocumentMapper {
    List<OfficialDocumentVo> getOfficialDocument(OfficialDocumentVo vo);
    int saveOfficialDocument(OfficialDocumentVo vo);
    void updateStatus(String billId, String docStatusCd);

}
