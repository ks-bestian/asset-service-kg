package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.repository.DraftDocumentRepository;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.DraftDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DraftDocumentServiceImpl implements DraftDocumentService {

    private final DraftDocumentRepository draftDocumentRepository;

    @Override
    public int insertDraftDocument(DraftDocumentVo vo) {
        return draftDocumentRepository.insertDraftDocument(vo);
    }
}
