package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.repository.LinkDocumentRepository;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.service.LinkDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class LinkDocumentServiceImpl implements LinkDocumentService {

    private final LinkDocumentRepository linkDocumentRepository;

    @Override
    public int insertLinkDocument(LinkDocumentVo vo) {
        return linkDocumentRepository.insertLinkDocument(vo);
    }

    @Override
    public void deleteLinkDocument(String fromDocId, String toDocId) {
        linkDocumentRepository.deleteLinkDocument(fromDocId, toDocId);
    }

    @Override
    public List<LinkDocumentVo> getLinkDocument(String docId) {
        return linkDocumentRepository.getLinkDocument(docId);
    }
}
