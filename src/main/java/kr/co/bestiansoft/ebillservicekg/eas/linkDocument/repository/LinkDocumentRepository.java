package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.repository;

import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LinkDocumentRepository {
    int insertLinkDocument (LinkDocumentVo vo);
    void deleteLinkDocument (String fromDocId, String toDocId);
    List<LinkDocumentVo> getLinkDocument (String docId);
}
