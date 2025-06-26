package kr.co.bestiansoft.ebillservicekg.eas.draftData.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.repository.DraftDataRepository;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.service.DraftDataService;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataAndComFormFieldDto;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataVo;
import kr.co.bestiansoft.ebillservicekg.form.service.FormService;
import kr.co.bestiansoft.ebillservicekg.formField.service.FormFieldService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DraftDataServiceImpl implements DraftDataService {

    private final DraftDataRepository draftDataRepository;

    @Override
    public void insertDraftData(DraftDataVo vo) {
        draftDataRepository.insertDraftData(vo);
    }

    @Override
    public List<DraftDataAndComFormFieldDto> getDraftData(int aarsDocId) {
        return draftDataRepository.getDraftDataAndComFormField(aarsDocId);
    }
}
