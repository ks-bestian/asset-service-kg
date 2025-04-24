package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.repository.DraftDocumentRepository;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.DraftDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class DraftDocumentServiceImpl implements DraftDocumentService {

    private final DraftDocumentRepository draftDocumentRepository;

    @Override
    public int insertDraftDocument(DraftDocumentVo vo) {
        String loginId = new SecurityInfoUtil().getAccountId();
        vo.setAarsStatusCd("ds001");
        vo.setRegId(loginId);
        vo.setRegDt(LocalDateTime.now());
        return draftDocumentRepository.insertDraftDocument(vo);
    }

    @Override
    public void updateDraftStatus(String aarsDocId, String aarsStatusCd) {
        draftDocumentRepository.updateDraftStatus(aarsDocId, aarsStatusCd);
    }
}
