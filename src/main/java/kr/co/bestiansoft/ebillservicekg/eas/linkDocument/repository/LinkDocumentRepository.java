package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentListDto;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentVo;

@Mapper
public interface LinkDocumentRepository {
    int insertLinkDocument (LinkDocumentVo vo);
    int deleteLinkDocument (String fromDocId, String toDocId);
    List<LinkDocumentListDto> getLinkDocumentByDocId (String docId);
    LinkDocumentVo getLinkDocumentByDocIdAndType (String docId, String linkType);
    List<LinkDocumentVo> getLinkDocument (int linkId);

}
