package kr.co.bestiansoft.ebillservicekg.eas.history.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.service.ComCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.history.repository.HistoryRepository;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final ComCodeService codeService;

    /**
     * Inserts a history record into the database.
     *
     * @param vo the HistoryVo object containing the details of the history to be inserted
     * @return an integer representing the number of rows affected by the insert operation
     */
    @Override
    public int insertHistory(HistoryVo vo) {
        return historyRepository.insertHistory(vo);
    }

    /**
     * Retrieves the action detail by combining a username and a code name associated with the given action type.
     *
     * @param actionType the type of the action whose code name is to be retrieved
     * @param userNm     the name of the user to be included in the action detail
     * @return a string combining the username and the code name associated with the action type,
     *         or an empty string if no code name is found for the given action type
     */
    public String getActionDetail(String actionType, String userNm){
        //todo 배포하기전 nm1 으로 바꾸기
        return Optional.ofNullable(codeService.getComCodeById(actionType))
                .map(ComCodeDetailVo::getCodeNm3)
                .map(codeName -> userNm + " " + codeName)
                .orElse("");
    }

    @Override
    public List<HistoryVo> getHistory(String docId) {
        return historyRepository.getHistory(docId);
    }

    public List<HistoryVo> getHistoryByUserId(String docId){
        return historyRepository.getHistoryByUserId(docId, new SecurityInfoUtil().getAccountId());
    }

    @Override
    public void deleteDocument(String docId) {
        historyRepository.deleteDocument(docId);
    }

}
