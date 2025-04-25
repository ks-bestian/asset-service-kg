package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.service.ComCodeService;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.repository.OfficialDocumentMapper;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.*;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Slf4j
@RequiredArgsConstructor
@Service
public class OfficialDocumentServiceImpl implements OfficialDocumentService {

    private final OfficialDocumentMapper officialDocumentMapper;
    private final ApprovalService approvalService;
    private final ReceivedInfoService receivedInfoService;
    private final HistoryService historyService;
    private final UserService userService;

    /**
     * Retrieves a list of documents based on the search criteria provided.
     *
     * @param vo the search criteria encapsulated in a {@link SearchDocumentVo} object.
     *           This includes fields such as document attributes, time limits,
     *           document type, and received date ranges.
     *           The method also sets the user ID in the search criteria using the account information.
     *
     * @return a list of {@link DocumentListDto} objects that match the search criteria.
     *         Each object contains details such as document ID, attributes, sender information,
     *         and timestamps related to document receipt and checking.
     */
    @Override
    public List<DocumentListDto> getDocumentList(SearchDocumentVo vo) {
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        return officialDocumentMapper.getDocumentList(vo);
    }

    /**
     * Saves an official document to the database.
     *
     * @param vo an instance of {@link OfficialDocumentVo} containing the details
     *           of the document to be saved, such as ID, attributes, status, and other metadata.
     * @return an integer indicating the number of rows affected by the save operation.
     */
    public int saveOfficialDocument(OfficialDocumentVo vo){
        return officialDocumentMapper.saveOfficialDocument(vo);
    }

    /**
     * Saves all document-related data including official document, approvals, received information, and history.
     * The method performs transactional operations and integrates multiple services to store the provided document data.
     *
     * @param vo InsertDocumentVo containing the data required to save the document and related details:
     *           - Aars document ID
     *           - Bill ID
     *           - Document ID
     *           - Document number
     *           - Department code
     *           - Document type code
     *           - Document timeline and status
     *           - Approvals and receiver information
     *           - Other metadata for the document.
     * @return The total count of records affected across all the insert operations, including those for
     *         official documents, approvals, received info, and history.
     */
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    @Override
    public int saveAllDocument(InsertDocumentVo vo) {

        int result= 0 ;
        String loginId = new SecurityInfoUtil().getAccountId();
        UserMemberVo loginUser = userService.getUserMemberDetail(loginId);
        LocalDateTime now = LocalDateTime.now();
        /* save eas_document */

        OfficialDocumentVo documentVo = OfficialDocumentVo.builder()
                .aarsDocId(vo.getAarsDocId())
                .billId(vo.getBillId())
                .docId(vo.getDocId())
                .docNo(vo.getDocNo())
                .deptCd(vo.getDeptCd())
                .docTypeCd(vo.getDocTypeCd())
                .tmlmtYn(vo.getTmlmtYn())
                .tmlmtDtm(vo.getTmlmtDtm())
                .docSubtle(vo.getDocSubtle())
                .senderId(loginId)
                .docStatusCd("ds01")
                .digitalYn('N')
                .userId(loginId)
                .docLng(arrayToString(vo.getDocLng()))
                .docAttrbCd(arrayToString(vo.getDocAttrbCd()))
                .senderNm(loginUser.getUserNm())
                .regDtm(now)
                .build();



        result += saveOfficialDocument(documentVo);

        /* save eas_approval */
        String[] approvalIds = vo.getApprovalIds();
        for(int i =0 ; i < approvalIds.length ; i ++){
            UserMemberVo user = userService.getUserMemberDetail(approvalIds[i]);
            ApprovalVo approvalVo = ApprovalVo.builder()
                    .docId(vo.getDocId())
                    .apvlStatusCd("AS01")
                    .apvlOrd(i)
                    .userId(user.getUserId())
                    .userNm(user.getUserNm())
                    .deptCd(user.getDeptCd())
                    .jobCd(user.getJobCd())
                    .build();
            if(i ==0) approvalVo.setApvlDtm(now);

            result += approvalService.insertApproval(approvalVo);
        }


        /* eas_received_info */
        String[] receivedIds = vo.getReceiverIds();
        for(int i=0 ; i < receivedIds.length ; i++){
            if(vo.getExternalYn() == 'N'){
                UserMemberVo user = userService.getUserMemberDetail(receivedIds[i]);
                ReceivedInfoVo receivedInfoVo = ReceivedInfoVo.builder()
                        .docId(vo.getDocId())
                        .rcvStatus("RS001")
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .deptCd(user.getDeptCd())
                        .rcvOrd(i+1)
                        .build();

                result += receivedInfoService.insertReceivedInfo(receivedInfoVo);
            }else if(vo.getExternalYn() == 'Y'){
                /* todo 외부기관 */
            }

        }

        /* eas_history */
        String actionType = "AT01";
        HistoryVo historyVo = HistoryVo.builder()
                .docId(vo.getDocId())
                .userId(loginId)
                .actType(actionType)
                .actDtm(now)
                .actDetail(historyService.getActionDetail(actionType, loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();

        result += historyService.insertHistory(historyVo);

        return result;

    }

    /**
     * Updates the status of an official document in the database.
     *
     * @param docId the unique identifier of the document whose status is to be updated
     * @param status the new status to be applied to the specified document
     * @return an integer representing the number of rows affected by the update operation
     */
    @Override
    public int updateStatusOfficialDocument(String docId, String status) {
        return officialDocumentMapper.updateStatus(docId,status);
    }

    /**
     * Counts the total number of documents associated with the currently logged-in account.
     *
     * @return the count of documents as an integer.
     */
    @Override
    public int countDocumentList() {
        return officialDocumentMapper.countDocumentList(new SecurityInfoUtil().getAccountId());
    }

    /**
     * Retrieves the details of a document based on the given document ID.
     *
     * @param docId the unique identifier of the document whose details are to be fetched.
     * @return an instance of {@link DocumentDetailDto} containing the detailed information
     *         about the specified document.
     */
    @Override
    public DocumentDetailDto getDocumentDetail(String docId) {
        return officialDocumentMapper.getDocumentDetail(docId);
    }

    /**
     * Converts an array of Strings into a single, comma-separated String.
     *
     * @param arrayS the array of Strings to be concatenated into a single String
     * @return a comma-separated String containing all elements of the input array
     */
    public String arrayToString(String[] arrayS){
        return String.join(",", arrayS);
    }
}
