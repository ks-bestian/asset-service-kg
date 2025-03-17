package kr.co.bestiansoft.ebillservicekg.eas.history.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.history.repository.HistoryRepository;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    public int insertHistory(HistoryVo vo) {
        return historyRepository.insertHistory(vo);
    }
}
