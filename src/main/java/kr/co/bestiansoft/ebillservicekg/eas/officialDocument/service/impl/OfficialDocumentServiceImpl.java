package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.repository.OfficialDocumentMapper;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OfficialDocumentServiceImpl implements OfficialDocumentService {

    private final OfficialDocumentMapper mapper;

    @Override
    public List<OfficialDocumentVo> getOfficialDocument(OfficialDocumentVo vo){
        return mapper.getOfficialDocument(vo);
    }
    public int saveOfficialDocument(OfficialDocumentVo vo){
        return mapper.saveOfficialDocument(vo);
    }
}
