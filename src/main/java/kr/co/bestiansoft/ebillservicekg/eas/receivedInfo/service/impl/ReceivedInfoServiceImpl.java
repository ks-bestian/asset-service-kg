package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.repository.ReceivedInfoRepository;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReceivedInfoServiceImpl implements ReceivedInfoService {

    private final ReceivedInfoRepository receivedInfoRepository;

    @Override
    public int insertReceivedInfo(ReceivedInfoVo vo) {
        return receivedInfoRepository.insertReceivedInfo(vo);
    }
}
