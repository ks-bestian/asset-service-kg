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

    /**
     * Inserts a draft document into the repository with necessary metadata such as status,
     * registration ID, and registration time automatically populated.
     *
     * @param vo the draft document object containing necessary document details
     * @return the number of records inserted in the repository
     */
    @Override
    public int insertDraftDocument(DraftDocumentVo vo) {
        String loginId = new SecurityInfoUtil().getAccountId();
        vo.setAarsStatusCd("ds001");
        vo.setRegId(loginId);
        vo.setRegDt(LocalDateTime.now());
        return draftDocumentRepository.insertDraftDocument(vo);
    }

    /**
     * Updates the status of a draft document in the repository.
     *
     * @param aarsDocId    the unique identifier of the draft document to be updated
     * @param aarsStatusCd the new status code to update the draft document with
     */
    @Override
    public void updateDraftStatus(String aarsDocId, String aarsStatusCd) {
        draftDocumentRepository.updateDraftStatus(aarsDocId, aarsStatusCd);
    }
}
