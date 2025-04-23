package kr.co.bestiansoft.ebillservicekg.eas.history.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.service.ComCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.eas.history.repository.HistoryRepository;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final ComCodeService codeService;
    @Override
    public int insertHistory(HistoryVo vo) {
        return historyRepository.insertHistory(vo);
    }

    public String getActionDetail(String actionType, String userNm){
        return Optional.ofNullable(codeService.getComCodeById(actionType))
                .map(ComCodeDetailVo::getCodeNm1)
                .map(codeName -> userNm + " " + codeName)
                .orElse("");
    }

}
