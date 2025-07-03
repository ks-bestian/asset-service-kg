package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.Impl;

import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums.*;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.DraftDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.service.LinkDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.DocumentDetailDto;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.InsertDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.service.impl.WorkRequestServiceImpl;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestAndResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo.WorkRequestVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.impl.WorkResponseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class DocumentWorkFlowServiceImpl implements DocumentWorkFlowService {
    private final ApprovalService approvalService;
    private final ReceivedInfoService receivedInfoService;
    private final HistoryService historyService;
    private final UserService userService;
    private final OfficialDocumentService documentService;
    private final DraftDocumentService draftDocumentService;
    private final WorkRequestServiceImpl workRequestService;
    private final WorkResponseServiceImpl workResponseService;
    private final LinkDocumentService linkDocumentService;
    private final EasFileService easFileService;


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
                .senderDeptCd(vo.getSenderDeptCd())
                .docTypeCd(vo.getDocTypeCd())
                .tmlmtYn(vo.getTmlmtYn())
                .tmlmtDtm(vo.getTmlmtDtm() != null ?vo.getTmlmtDtm().atStartOfDay() : null)
                .docSubtle(vo.getDocSubtle())
                .senderId(loginId)
                .docStatusCd(DocumentStatus.APPROVING.getCodeId())
                .digitalYn('Y')
                .userId(loginId)
                .docLng(arrayToString(vo.getDocLng()))
                .docAttrbCd(arrayToString(vo.getDocAttrbCd()))
                .senderNm(loginUser.getUserNm())
                .regDtm(now)
                .build();



        result += documentService.saveOfficialDocument(documentVo);

        draftDocumentService.updateDraftStatus(vo.getAarsDocId(), DraftStatus.DISPATCHED.getCodeId());

        int addApprovalCount = 1;

        /* save eas_approval */
        String[] approvalIds = vo.getApprovalIds();

        for (String approvalId : approvalIds) {
            if (approvalId != null) {
                UserMemberVo user = userService.getUserMemberDetail(approvalId);
                ApprovalVo approvalVo = ApprovalVo.builder()
                        .docId(vo.getDocId())
                        .apvlStatusCd(ApprovalStatus.PENDING.getCodeId())
                        .apvlOrd(addApprovalCount)
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .deptCd(user.getDeptCd())
                        .jobCd(user.getJobCd())
                        .apvlType(ApprovalType.REQUEST_APPROVAL.getCodeId())
                        .build();
                if (addApprovalCount == 1) {
                    approvalVo.setRcvDtm(now);
                    approvalVo.setApvlStatusCd(ApprovalStatus.SENT.getCodeId());
                }
                addApprovalCount++;
                result += approvalService.insertApproval(approvalVo);
            }
        }
        // document Attribute

        for(String attrbCd : vo.getDocAttrbCd() ){
            if(attrbCd.equals("DMA02")){
                //todo userId, userNm, deptCd, jobCd ;
                ApprovalVo approvalVo = ApprovalVo.builder()
                        .docId(vo.getDocId())
                        .apvlOrd(addApprovalCount)
                        .apvlType(ApprovalType.REQUEST_REVIEW_AND_APPROVAL.getCodeId())
                        .userId("gduser1")
                        .build();

                if(addApprovalCount == 1){
                    approvalVo.setRcvDtm(now);
                    approvalVo.setApvlStatusCd(ApprovalStatus.SENT.getCodeId());
                }else{
                    approvalVo.setApvlStatusCd(ApprovalStatus.PENDING.getCodeId());
                }
                addApprovalCount++;
                approvalService.insertApproval(approvalVo);
            }
        }

        /* eas_received_info */
        List<Map<String, String>> receivedIds = vo.getReceiverIds();
        for(int i=0 ; i < receivedIds.size() ; i++){
            if(receivedIds.get(i).get("isExternal").equals("N")){
                UserMemberVo user = userService.getUserMemberDetail(receivedIds.get(i).get("userId"));
                ReceivedInfoVo receivedInfoVo = ReceivedInfoVo.builder()
                        .docId(vo.getDocId())
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .deptCd(user.getDeptCd())
                        .rcvOrd(i+1)
                        .build();
                if(addApprovalCount ==1){
                    receivedInfoVo.setRcvStatus(ReceiveStatus.SENT.getCodeId());
                }else{
                    receivedInfoVo.setRcvStatus(ReceiveStatus.BEFORE_SEND.getCodeId());
                }

                result += receivedInfoService.insertReceivedInfo(receivedInfoVo);
            }else{
                /* todo 외부기관 */
            }

        }

        /* eas_history */
        HistoryVo historyVo = HistoryVo.builder()
                .docId(vo.getDocId())
                .userId(loginId)
                .actType(ActionType.CREATE_DOCUMENT.getCodeId())
                .actDtm(now)
                .actDetail(historyService.getActionDetail(ActionType.CREATE_DOCUMENT.getCodeId(), loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();

        result += historyService.insertHistory(historyVo);

        if(addApprovalCount ==1){
            documentService.updateStatusOfficialDocument(documentVo.getDocId(), DocumentStatus.TRANSMITTED.getCodeId());
        }

        return result;

    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    @Override
    public void approve(UpdateApprovalVo vo) {
        if(vo.getApvlType().equals(ApprovalType.REQUEST_APPROVAL.getCodeId()) ){
            RequestApprove(vo);
        }else if(vo.getApvlType().equals(ApprovalType.REQUEST_REPLY_APPROVAL.getCodeId())){
            ReplyApprove(vo);
        }else if(vo.getApvlType().equals(ApprovalType.REQUEST_REVIEW_AND_APPROVAL.getCodeId())){
            RequestReviewAndApprove(vo);
        }
    }
    // DMS04
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void approveReject(UpdateApprovalVo vo){
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());

        vo.setResDtm(LocalDateTime.now());
        vo.setApvlStatusCd(ApprovalStatus.REJECTED.getCodeId());
        // approval update
        approvalService.updateApproval(vo);
        // document Status change
        documentService.updateStatusOfficialDocument(vo.getDocId(), DocumentStatus.REJECTED.getCodeId());

        // history
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType(ActionType.REJECT_APPROVAL.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.REJECT_APPROVAL.getCodeId(), loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void reception(WorkRequestAndResponseVo vo){
        if(vo.getDocTypeCd() == null){}
        else if(vo.getDocTypeCd().equals(DocumentType.REPLY_PURPOSE.getCodeId())){
            //Answer
            // Request for implementation addition
            vo.setRegDt(LocalDateTime.now());
            vo.setRegId(new SecurityInfoUtil().getAccountId());
            vo.setWorkStatus(WorkStatus.DISPATCHED.getCodeId());

            WorkRequestVo requestVo = vo.toRequestVo();

            if (requestVo.getWorkReqId() == 0) {
                workRequestService.insertWorkRequest(requestVo);
            } else {
                workRequestService.updateWorkRequest(requestVo);
            }

            log.info("workReqId : " + requestVo.toString());

            workResponseService.delete(requestVo.getWorkReqId());
            //// 이행자 추가
            vo.getWorkResponseVos().forEach(workResponseVo -> {
                UserMemberVo loginUser = userService.getUserMemberDetail(workResponseVo.getUserId());
                workResponseVo.setWorkReqId(requestVo.getWorkReqId());
                workResponseVo.setDeptCd(loginUser.getDeptCd());
                workResponseVo.setJobCd(loginUser.getJobCd());
                workResponseVo.setUserNm(loginUser.getUserNm());
                workResponseVo.setRcvDtm(LocalDateTime.now());

                workResponseService.insertWorkResponse(workResponseVo);

            });
            // receivedInfo situation change, main performer addition
            UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                    .rcvId(vo.getRcvId())
                    .workerId(vo.getWorkerId())
                    .rcpDtm(LocalDateTime.now())
                    .rcvStatus(ReceiveStatus.REGISTERED.getCodeId())
                    .build();
            receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            // document status change
            documentService.updateStatusOfficialDocument(vo.getDocId() , DocumentStatus.REGISTERED.getCodeId());
            // history
            UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
            HistoryVo historyVo = HistoryVo.builder()
                    .userId(loginUser.getUserId())
                    .docId(vo.getDocId())
                    .actType(ActionType.REGISTER_DOCUMENT.getCodeId())
                    .actDtm(LocalDateTime.now())
                    .actDetail(historyService.getActionDetail(ActionType.REGISTER_DOCUMENT.getCodeId(),loginUser.getUserNm()))
                    .userNm(loginUser.getUserNm())
                    .build();
            historyService.insertHistory(historyVo);


        }else if(vo.getDocTypeCd().equals(DocumentType.CONFIRMATION_PURPOSE.getCodeId())){
            // Confirmation - end
            // receivedInfo Update
            UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                    .rcvId(vo.getRcvId())
                    .rcpDtm(LocalDateTime.now())
                    .rcvStatus(ReceiveStatus.COMPLETED_RESPONSE.getCodeId())
                    .build();
            receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            endDocument(vo.getDocId());
        }else if(vo.getDocTypeCd().equals(DocumentType.SIGNATURE_PURPOSE.getCodeId())){
            //Signature - signature
            UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                    .rcvId(vo.getRcvId())
                    .rcpDtm(LocalDateTime.now())
                    .hashFileId(vo.getHashFileId())
                    .rcvStatus(ReceiveStatus.COMPLETED_RESPONSE.getCodeId())
                    .build();
            receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);

            UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
            HistoryVo historyVo = HistoryVo.builder()
                    .userId(loginUser.getUserId())
                    .docId(vo.getDocId())
                    .actType(ActionType.SIGN_DOCUMENT.getCodeId())
                    .actDtm(LocalDateTime.now())
                    .actDetail(historyService.getActionDetail(ActionType.SIGN_DOCUMENT.getCodeId(),loginUser.getUserNm()))
                    .userNm(loginUser.getUserNm())
                    .build();
            historyService.insertHistory(historyVo);

            if(isEnd(vo.getDocId())){
                endDocument(vo.getDocId());
            }else{
                documentService.updateStatusOfficialDocument(vo.getDocId(), DocumentStatus.REGISTERED.getCodeId());
            }
        }
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void rejectReception(UpdateReceivedInfoVo vo){
        //receivedInfo update
        vo.setRjctDtm(LocalDateTime.now());
        vo.setRcvStatus(ReceiveStatus.REJECTED.getCodeId());
        receivedInfoService.updateReceivedInfo(vo);
        // documentStatus update
        documentService.updateStatusOfficialDocument(vo.getDocId(), DocumentStatus.REJECTED.getCodeId());
        //insert history
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType(ActionType.REJECT_DOCUMENT.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.REJECT_DOCUMENT.getCodeId(), loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void endDocument(String docId){
       UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
       HistoryVo historyVo = HistoryVo.builder()
               .userId(loginUser.getUserId())
               .docId(docId)
               .actType(ActionType.COMPLETE_DOCUMENT.getCodeId())
               .actDtm(LocalDateTime.now())
               .actDetail(historyService.getActionDetail(ActionType.COMPLETE_DOCUMENT.getCodeId(), loginUser.getUserNm()))
               .userNm(loginUser.getUserNm())
               .build();
       historyService.insertHistory(historyVo);
       documentService.updateStatusOfficialDocument(docId, DocumentStatus.COMPLETED.getCodeId());
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    @Override
    public void saveReplyDocument(InsertDocumentVo vo) {
        String loginId = new SecurityInfoUtil().getAccountId();
        UserMemberVo loginUser = userService.getUserMemberDetail(loginId);
        LocalDateTime now = LocalDateTime.now();
        //document write
        OfficialDocumentVo documentVo = OfficialDocumentVo
                .builder()
                .aarsDocId(vo.getAarsDocId())
                .billId(vo.getBillId())
                .docId(vo.getDocId())
                .senderDeptCd(loginUser.getDeptCd())
                .docTypeCd(DocumentType.CONFIRMATION_PURPOSE.getCodeId())
                .docSubtle(vo.getDocSubtle())
                .senderId(loginId)
                .docStatusCd(DocumentStatus.APPROVING.getCodeId())
                .tmlmtYn(vo.getTmlmtYn())
                .digitalYn('Y')
                .userId(loginId)
                .docLng(arrayToString(vo.getDocLng()))
                .docAttrbCd(arrayToString(vo.getDocAttrbCd()))
                .senderNm(loginUser.getUserNm())
                .regDtm(now)
                .build();

        documentService.saveOfficialDocument(documentVo);
        // draft status change
        draftDocumentService.updateDraftStatus(vo.getAarsDocId(), DraftStatus.DISPATCHED.getCodeId());


        int addApprovalCount = 1;

        //approval Type Change addition
        String[] approvalIds = vo.getApprovalIds();
        for (String approvalId : approvalIds) {
            if (approvalId != null) {
                UserMemberVo user = userService.getUserMemberDetail(approvalId);
                ApprovalVo approvalVo = ApprovalVo.builder()
                        .docId(vo.getDocId())
                        .apvlStatusCd(ApprovalStatus.PENDING.getCodeId())
                        .apvlOrd(addApprovalCount)
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .deptCd(user.getDeptCd())
                        .jobCd(user.getJobCd())
                        .apvlType(ApprovalType.REQUEST_REPLY_APPROVAL .getCodeId())
                        .build();
                if (addApprovalCount == 1) {
                    approvalVo.setRcvDtm(now);
                    approvalVo.setApvlStatusCd(ApprovalStatus.SENT.getCodeId());
                }
                addApprovalCount++;
                approvalService.insertApproval(approvalVo);
            }
        }

        for(String attrbCd : vo.getDocAttrbCd() ){
            if(attrbCd.equals("DMA02")){
                //todo userId, userNm, deptCd, jobCd ;
                ApprovalVo approvalVo = ApprovalVo.builder()
                        .docId(vo.getDocId())
                        .apvlOrd(addApprovalCount)
                        .apvlType(ApprovalType.REQUEST_REVIEW_AND_APPROVAL.getCodeId())
                        .userId("gduser1")
                        .build();

                if(addApprovalCount == 1){
                    approvalVo.setRcvDtm(now);
                    approvalVo.setApvlStatusCd(ApprovalStatus.SENT.getCodeId());
                }else{
                    approvalVo.setApvlStatusCd(ApprovalStatus.PENDING.getCodeId());
                }
                addApprovalCount++;
                approvalService.insertApproval(approvalVo);
            }
        }

        //document connection
        LinkDocumentVo linkDocumentVo = LinkDocumentVo
                .builder()
                .fromDocId(vo.getFromDocId())
                .toDocId(vo.getDocId())
                .linkType(LinkType.LINK_REPLY.getCodeId())
                .regId(loginId)
                .regDtm(now)
                .build();
        linkDocumentService.insertLinkDocument(linkDocumentVo);
        //ReceivedStatus change
        UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                .rcvStatus(ReceiveStatus.APPROVING_RESPONSE.getCodeId())
                .rcpDocId(vo.getFromDocId())
                .rcvId(vo.getRcvId())
                .build();
        receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
        // implementation file Brought Adding
        String[] alreadyUploadFiles = vo.getAlreadyUploadFiles();
        for(String fileId : alreadyUploadFiles){
            EasFileVo easFileVo =  easFileService.getFileById(fileId);
            easFileVo.setFileId(UUID.randomUUID().toString());
            easFileVo.setDocId(vo.getDocId());
            easFileVo.setFileType(EasFileType.ATTACHMENT_FILE.getCodeId());
            easFileService.saveEasFile(easFileVo);
        }

        //ReceivedInfo addition
        List<Map<String, String>> receivedIds = vo.getReceiverIds();
        for(int i=0 ; i < receivedIds.size() ; i++){
            if(receivedIds.get(i).get("isExternal").equals("N")){
                UserMemberVo user = userService.getUserMemberDetail(receivedIds.get(i).get("userId"));
                ReceivedInfoVo receivedInfoVo = ReceivedInfoVo.builder()
                        .docId(vo.getDocId())
                        .rcvStatus(ReceiveStatus.BEFORE_SEND.getCodeId())
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .deptCd(user.getDeptCd())
                        .rcvOrd(i+1)
                        .build();
                if(addApprovalCount ==1){
                    receivedInfoVo.setRcvStatus(ReceiveStatus.SENT.getCodeId());
                }else{
                    receivedInfoVo.setRcvStatus(ReceiveStatus.BEFORE_SEND.getCodeId());
                }
                receivedInfoService.insertReceivedInfo(receivedInfoVo);
            }else{
                /* todo External agency */
            }
        }

        //history
        HistoryVo historyVo = HistoryVo.builder()
                .docId(vo.getFromDocId())
                .userId(loginId)
                .actType(ActionType.WRITE_REPLY.getCodeId())
                .actDtm(now)
                .actDetail(historyService.getActionDetail(ActionType.WRITE_REPLY.getCodeId(), loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);

        if(addApprovalCount ==1){
            documentService.updateStatusOfficialDocument(documentVo.getDocId(), DocumentStatus.TRANSMITTED.getCodeId());
        }
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    @Override
    public void rewriteDocument(InsertDocumentVo vo) {
        saveAllDocument(vo);

        String[] alreadyUploadFiles = vo.getAlreadyUploadFiles();
        for(String fileId : alreadyUploadFiles){
            EasFileVo easFileVo =  easFileService.getFileById(fileId);
            easFileVo.setFileId(UUID.randomUUID().toString());
            easFileVo.setDocId(vo.getDocId());
            easFileVo.setFileType(EasFileType.ATTACHMENT_FILE.getCodeId());
            easFileService.saveEasFile(easFileVo);
        }

        deleteDocument(vo.getFromDocId());
    }

    @Override
    public void endRejectedDocument(String docId) {
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        documentService.updateStatusOfficialDocument(docId, DocumentStatus.COMPLETED.getCodeId());
        HistoryVo historyVo = HistoryVo.builder()
                .docId(docId)
                .userId(loginUser.getUserId())
                .actType(ActionType.COMPLETE_DOCUMENT.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.COMPLETE_DOCUMENT.getCodeId(), loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }

    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void RequestApprove(UpdateApprovalVo vo){
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        List<ApprovalVo> approvalVoList = approvalService.getApprovals(vo.getDocId());
        ApprovalVo approvalVo = approvalService.getApproval(vo.getApvlId());
        if(approvalVo.getApvlOrd() == approvalVoList.size()) {
            List<ReceivedInfoVo> receivedInfoVo =  receivedInfoService.getReceivedInfo(vo.getDocId());
            receivedInfoVo.forEach(rcvInfoVo -> {
                UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                        .rcvId(rcvInfoVo.getRcvId())
                        .rcvDtm(LocalDateTime.now())
                        .rcvStatus(ReceiveStatus.SENT.getCodeId())
                        .build();
                // Recipient situation change
                receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            });
            // forwarding In a state change
            documentService.updateStatusOfficialDocument(vo.getDocId(), DocumentStatus.TRANSMITTED.getCodeId());
            // todo number grant
        }else{
            Optional<ApprovalVo> nextApprovalVo = approvalVoList.stream().filter(s-> s.getApvlOrd() == approvalVo.getApvlOrd()+1).findFirst();

            if(nextApprovalVo.get().getApvlStatusCd().equals(ApprovalStatus.PENDING.getCodeId())){

                UpdateApprovalVo  nextUpdateApprovalVo= UpdateApprovalVo
                        .builder()
                        .apvlId(nextApprovalVo.get().getApvlId())
                        .rcvDtm(LocalDateTime.now())
                        .apvlStatusCd(ApprovalStatus.SENT.getCodeId())
                        .build();

                UpdateApprovalVo  updateApprovalVo= UpdateApprovalVo.builder()
                        .apvlId(vo.getApvlId())
                        .resDtm(LocalDateTime.now())
                        .apvlStatusCd(ApprovalStatus.APPROVED.getCodeId())
                        .build();

                approvalService.updateApproval(nextUpdateApprovalVo);
                approvalService.updateApproval(updateApprovalVo);
            }
        }
        // existing approval change
        vo.setResDtm(LocalDateTime.now());
        vo.setApvlStatusCd(ApprovalStatus.APPROVED.getCodeId());
        approvalService.updateApproval(vo);

        // history save
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType(ActionType.APPROVE_SIGN.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.APPROVE_SIGN.getCodeId(),loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void ReplyApprove(UpdateApprovalVo vo) {
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        List<ApprovalVo> approvalVoList = approvalService.getApprovals(vo.getDocId());
        ApprovalVo approvalVo = approvalService.getApproval(vo.getApvlId());
        if(approvalVo.getApvlOrd() == approvalVoList.size()) {
            List<ReceivedInfoVo> receivedInfoVo =  receivedInfoService.getReceivedInfo(vo.getDocId());
            receivedInfoVo.forEach(rcvInfoVo -> {
                UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                        .rcvId(rcvInfoVo.getRcvId())
                        .rcvDtm(LocalDateTime.now())
                        .rcvStatus(ReceiveStatus.SENT.getCodeId())
                        .build();
                // Recipient situation change
                receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            });
            documentService.updateStatusOfficialDocument(vo.getDocId(), DocumentStatus.TRANSMITTED.getCodeId());
            // todo number grant

            // link document Answered thing Come -up
            LinkDocumentVo linkDocumentVo = linkDocumentService.getLinkDocumentByDocIdAndType(vo.getDocId(), LinkType.LINK_REPLY.getCodeId());
            // ReceiveStatus.COMPLETED_RESPONSE change
            ReceivedInfoVo replyReceivedInfo = receivedInfoService.getReceivedInfoByRcpDocId(linkDocumentVo.getToDocId());
            UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                    .rcvId(replyReceivedInfo.getRcvId())
                    .rcvStatus(ReceiveStatus.COMPLETED_RESPONSE.getCodeId())
                    .build();
            receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            // existing history change
            HistoryVo historyVo = HistoryVo.builder()
                    .userId(loginUser.getUserId())
                    .docId(linkDocumentVo.getFromDocId())
                    .actType(ActionType.APPROVE_REPLY.getCodeId())
                    .actDtm(LocalDateTime.now())
                    .actDetail(historyService.getActionDetail(ActionType.APPROVE_REPLY.getCodeId(), loginUser.getUserNm()))
                    .userNm(loginUser.getUserNm())
                    .build();
            historyService.insertHistory(historyVo);
            // isEnd check after When confirmed document end

            if(isEnd(linkDocumentVo.getFromDocId())){
                endDocument(linkDocumentVo.getFromDocId());
            }
        }else{
            Optional<ApprovalVo> nextApprovalVo = approvalVoList.stream().filter(s-> s.getApvlOrd() == approvalVo.getApvlOrd()+1).findFirst();

            if(nextApprovalVo.get().getApvlStatusCd().equals(ApprovalStatus.PENDING.getCodeId())){

                UpdateApprovalVo  nextUpdateApprovalVo= UpdateApprovalVo
                        .builder()
                        .apvlId(nextApprovalVo.get().getApvlId())
                        .resDtm(LocalDateTime.now())
                        .apvlStatusCd(ApprovalStatus.SENT.getCodeId())
                        .build();

                UpdateApprovalVo  updateApprovalVo= UpdateApprovalVo.builder()
                        .apvlId(vo.getApvlId())
                        .rcvDtm(LocalDateTime.now())
                        .apvlStatusCd(ApprovalStatus.APPROVED.getCodeId())
                        .build();

                approvalService.updateApproval(nextUpdateApprovalVo);
                approvalService.updateApproval(updateApprovalVo);
            }
        }
        // existing approval change
        vo.setResDtm(LocalDateTime.now());
        vo.setApvlStatusCd(ApprovalStatus.APPROVED.getCodeId());
        approvalService.updateApproval(vo);


        // history save
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType(ActionType.APPROVE_SIGN.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.APPROVE_SIGN.getCodeId(),loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void RequestReviewAndApprove(UpdateApprovalVo vo){
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        List<ApprovalVo> approvalVoList = approvalService.getApprovals(vo.getDocId());
        ApprovalVo approvalVo = approvalService.getApproval(vo.getApvlId());
        DocumentDetailDto documentVo = documentService.getDocumentDetail(vo.getDocId());

        if(approvalVo.getApvlOrd() == approvalVoList.size()) {
            List<ReceivedInfoVo> receivedInfoVo =  receivedInfoService.getReceivedInfo(vo.getDocId());
            receivedInfoVo.forEach(rcvInfoVo -> {
                UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                        .rcvId(rcvInfoVo.getRcvId())
                        .rcvDtm(LocalDateTime.now())
                        .rcvStatus(ReceiveStatus.SENT.getCodeId())
                        .build();
                // Recipient situation change
                receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            });


            // todo number grant

            // Documentary External transmission Whether it is Check In the state of transmission change
            if(documentVo.getExternalYn().equals("Y")){
                // todo To external organizations forwarding
            }else{
                documentService.updateStatusOfficialDocument(vo.getDocId(), DocumentStatus.TRANSMITTED.getCodeId());
            }
        }else{
            Optional<ApprovalVo> nextApprovalVo = approvalVoList.stream().filter(s-> s.getApvlOrd() == approvalVo.getApvlOrd()+1).findFirst();

            if(nextApprovalVo.get().getApvlStatusCd().equals(ApprovalStatus.PENDING.getCodeId())){

                UpdateApprovalVo  nextUpdateApprovalVo= UpdateApprovalVo
                        .builder()
                        .apvlId(nextApprovalVo.get().getApvlId())
                        .resDtm(LocalDateTime.now())
                        .apvlStatusCd(ApprovalStatus.SENT.getCodeId())
                        .build();

                UpdateApprovalVo  updateApprovalVo= UpdateApprovalVo.builder()
                        .apvlId(vo.getApvlId())
                        .rcvDtm(LocalDateTime.now())
                        .apvlStatusCd(ApprovalStatus.APPROVED.getCodeId())
                        .build();

                approvalService.updateApproval(nextUpdateApprovalVo);
                approvalService.updateApproval(updateApprovalVo);
            }
        }
        // existing approval change
        vo.setResDtm(LocalDateTime.now());
        vo.setApvlStatusCd(ApprovalStatus.APPROVED.getCodeId());
        approvalService.updateApproval(vo);

        // history save
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType(ActionType.APPROVE_SIGN.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.APPROVE_SIGN.getCodeId(),loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);

    }
    public boolean isEnd(String docId){
        List<ReceivedInfoVo> receivedInfo = receivedInfoService.getReceivedInfo(docId);
        // The recipient no case treatment (Business Logic according to decision)
        if (receivedInfo.isEmpty()) {
            return false; // or true (Business To the requirements according to decision)
        }
        // every The recipient answer complete State awareness check
        for (ReceivedInfoVo rcvInfoVo : receivedInfo) {
            if (!rcvInfoVo.getRcvStatus().equals(ReceiveStatus.COMPLETED_RESPONSE.getCodeId())) {
                return false; // One answer Complete or not immediately false return
            }
        }
        return true; // every The recipient answer complete State true return
    }

    @Override
    public int updateReadDateTime(int rcvId) {
        UpdateReceivedInfoVo vo = UpdateReceivedInfoVo.builder()
                .rcvId(rcvId)
                .checkDtm(LocalDateTime.now())
                .rcvStatus(ReceiveStatus.VIEWED.getCodeId())
                .build();
        return receivedInfoService.updateReceivedInfo(vo);
    }
    
    public void updateReadApproval(int apvlId){
        UpdateApprovalVo vo = UpdateApprovalVo.builder()
                .apvlId(apvlId)
                .checkDtm(LocalDateTime.now())
                .apvlStatusCd(ApprovalStatus.VIEWED.getCodeId())
                .build();
         approvalService.updateApproval(vo);
    }
    public String arrayToString(String[] arrayS){
        return String.join(",", arrayS);
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void deleteDocument(String docId){
        documentService.deleteDocument(docId);
        approvalService.deleteDocument(docId);
        receivedInfoService.deleteDocument(docId);
        historyService.deleteDocument(docId);
        workRequestService.deleteDocument(docId);
        workResponseService.deleteDocument(docId);
        easFileService.deleteDocument(docId);
    }
}