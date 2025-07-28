package kr.co.bestiansoft.ebillservicekg.eas.draftData.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.bestiansoft.ebillservicekg.eas.draftData.repository.DraftDataRepository;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.service.DraftDataService;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataAndComFormFieldDto;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataVo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DraftDataServiceImpl implements DraftDataService {

    private final DraftDataRepository draftDataRepository;

    /**
     * Inserts draft data into the database.
     *
     * @param vo an instance of {@link DraftDataVo} containing the draft data to be inserted
     */
    @Override
    public void insertDraftData(DraftDataVo vo) {
        draftDataRepository.insertDraftData(vo);
    }

    /**
     * Retrieves a list of draft data and associated form field information for the specified document ID.
     *
     * @param aarsDocId the ID of the document for which draft data is to be retrieved
     * @return a list of {@link DraftDataAndComFormFieldDto} containing draft data and related form field details
     */
    @Override
    public List<DraftDataAndComFormFieldDto> getDraftData(int aarsDocId) {
        return draftDataRepository.getDraftDataAndComFormField(aarsDocId);
    }
}
