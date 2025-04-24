package kr.co.bestiansoft.ebillservicekg.eas.file.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.eas.file.repository.EasFileRepository;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class EasFileServiceImpl implements EasFileService {

    private final EasFileRepository easFileRepository;
    private final EDVHelper edvHelper;


    @Override
    public EasFileVo uploadEasFile(EasFileVo vo){
        return vo;
    }

    public int saveEasFile(EasFileVo vo){
        return easFileRepository.insertEasFile(vo);
    }

    @Override
    public List<EasFileVo> getAttachFiles(String docId) {
        return easFileRepository.getAttachFiles(docId);
    }
}
