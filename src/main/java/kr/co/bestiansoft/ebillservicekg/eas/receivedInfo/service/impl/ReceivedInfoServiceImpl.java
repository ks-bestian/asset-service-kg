package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.impl;

import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.repository.ReceivedInfoRepository;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReceivedInfoServiceImpl implements ReceivedInfoService {

    private final ReceivedInfoRepository receivedInfoRepository;

    /**
     * Inserts the received information into the repository.
     *
     * @param vo the {@link ReceivedInfoVo} object containing the received information to be inserted
     * @return the number of rows affected by the insert operation
     */
    @Override
    public int insertReceivedInfo(ReceivedInfoVo vo) {
        return receivedInfoRepository.insertReceivedInfo(vo);
    }

    /**
     * Updates the received information in the repository.
     *
     * @param vo the {@link UpdateReceivedInfoVo} object containing the updated information
     * @return the number of rows affected by the update operation
     */
    @Override
    public int updateReceivedInfo(UpdateReceivedInfoVo vo) {
        return receivedInfoRepository.updateReceivedInfo(vo);
    }

}
