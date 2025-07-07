package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.repository;

import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentListDto;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LinkDocumentRepository {
    int insertLinkDocument (LinkDocumentVo vo);
    int deleteLinkDocument (String fromDocId, String toDocId);
    List<LinkDocumentListDto> getLinkDocumentByDocId (String docId);
    LinkDocumentVo getLinkDocumentByDocIdAndType (String docId, String linkType);
    List<LinkDocumentVo> getLinkDocument (int linkId);

}
