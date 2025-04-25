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

    /**
     * Inserts a new link document record into the repository.
     *
     * @param vo the LinkDocumentVo object containing the details to be inserted
     * @return the number of rows affected by the insert operation
     */
    @Override
    public int insertLinkDocument(LinkDocumentVo vo) {
        return linkDocumentRepository.insertLinkDocument(vo);
    }

    /**
     * Deletes a link document record from the repository based on the provided document IDs.
     *
     * @param fromDocId the ID of the source document
     * @param toDocId the ID of the target document
     * @return the number of rows affected by the delete operation
     */
    @Override
    public int deleteLinkDocument(String fromDocId, String toDocId) {
        return linkDocumentRepository.deleteLinkDocument(fromDocId, toDocId);
    }

    /**
     * Retrieves a list of linked document records based on the provided document ID.
     *
     * @param docId the ID of the document for which linked documents are to be retrieved
     * @return a list of LinkDocumentVo objects representing the linked documents
     */
    @Override
    public List<LinkDocumentVo> getLinkDocument(String docId) {
        return linkDocumentRepository.getLinkDocument(docId);
    }
}
