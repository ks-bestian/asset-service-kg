package kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.repository.ReceivedInfoRepository;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReceivedInfoServiceImpl implements ReceivedInfoService {

    private final ReceivedInfoRepository receivedInfoRepository;
    private final UserService userService;

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

    /**
     * Retrieves a list of received information records based on the provided document ID.
     *
     * @param docId the identifier of the document for which the received information is to be retrieved
     * @return a list of {@link ReceivedInfoVo} objects containing the received information
     */
    @Override
    public List<ReceivedInfoVo> getReceivedInfo(String docId) {
        return receivedInfoRepository.getReceivedInfo(docId);
    }
    @Override
	public boolean isReceipt(int docId) {
        return receivedInfoRepository.isReceipt(docId);
    }

    /**
     * Retrieves the main worker information associated with the given received ID.
     *
     * @param rcvId the identifier of the received information for which to retrieve the main worker details
     * @return a {@link UserMemberVo} object containing the main worker's details, or null if no main worker is found
     */
    @Override
    public UserMemberVo getMainWorkerInfo(int rcvId) {
        String mainWorker = receivedInfoRepository.getMainWorker(rcvId);
        if(mainWorker == null) {
            return null;
        }
        return userService.getUserMemberDetail(mainWorker);
    }

    /**
     * Retrieves the received information associated with the specified document ID and user ID.
     *
     * @param docId the identifier of the document for which the received information is to be retrieved
     * @return the {@link ReceivedInfoVo} object containing the received information, or null if no record is found
     */
    @Override
    public ReceivedInfoVo getReceivedInfoByUserId(String docId) {
        return receivedInfoRepository.getReceivedInfoByUserId(docId, new SecurityInfoUtil().getAccountId());
    }

    /**
     * Retrieves the received information associated with the specified recipient document ID.
     *
     * @param rcpDocId the recipient document ID for which the received information is to be retrieved
     * @return the {@link ReceivedInfoVo} object containing the received information, or null if no record is found
     */
    @Override
    public ReceivedInfoVo getReceivedInfoByRcpDocId(String rcpDocId) {
        return receivedInfoRepository.getReceivedInfoByRcpDocId(rcpDocId);
    }

    /**
     * Deletes the document associated with the specified document ID from the repository.
     *
     * @param docId the identifier of the document to be deleted
     */
    @Override
    public void deleteDocument(String docId) {
        receivedInfoRepository.deleteDocument(docId);
    }

    /**
     * Retrieves the document ID associated with the specified received ID.
     *
     * @param rcvId the identifier of the received information for which the document ID is to be retrieved
     * @return the document ID as a string, or null if no document ID is found
     */
    @Override
    public String getDocIdByRcvId(int rcvId) {
        return receivedInfoRepository.getDocIdByRcvId(rcvId);
    }
}
