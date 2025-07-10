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
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
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
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.repository.WorkResponseRepository;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.service.impl.WorkResponseServiceImpl;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.UpdateWorkResponseVo;
import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
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
    private final WorkResponseRepository workResponseRepository;


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
        // file add
        DraftDocumentVo draftVo = draftDocumentService.getDraftDocument(vo.getAarsDocId());
        String fileNm = "OfficialDocument.pdf";
        EasFileVo fileVo = EasFileVo.builder()
                .fileId(draftVo.getAarsFileId())
                .orgFileId(draftVo.getAarsFileId())
                .fileType(EasFileType.DRAFT_DOCUMENT_FILE.getCodeId())
                .orgFileNm(fileNm)
                .pdfFileNm(fileNm)
                .deleteYn('N')
                .pdfFileId(draftVo.getAarsPdfFileId())
                .docId(vo.getDocId())
                .regId(loginId)
                .regDt(LocalDateTime.now())
                .build();
        easFileService.saveEasFile(fileVo);

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
                UserMemberVo user = userService.getUserMemberDetail("gduser1");
                ApprovalVo approvalVo = ApprovalVo.builder()
                        .docId(vo.getDocId())
                        .apvlOrd(addApprovalCount)
                        .apvlType(ApprovalType.REQUEST_REVIEW_AND_APPROVAL.getCodeId())
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .deptCd(user.getDeptCd())
                        .jobCd(user.getJobCd())
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
                    receivedInfoVo.setRcvDtm(now);
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

    /**
     * Processes the approval request based on the specified type of approval.
     * Ensures the appropriate approval logic is executed based on the approval type defined in the input parameter.
     * This method is transactional and will roll back in case of a {@code SQLIntegrityConstraintViolationException}.
     *
     * @param vo the update approval value object containing the details of the approval request,
     *           including the type of approval to be processed.
     */
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

    /**
     * Processes the rejection of an approval request, which includes updating the
     * approval status, changing the document status, and recording the action in the history.
     *
     * @param vo the data transfer object containing the details of the approval to be rejected,
     *           such as the document ID and current approval status information
     */
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

    /**
     * Processes the reception of a work request based on its document type.
     * Handles various operations such as document registration, confirmation, and signature,
     * updating relevant entities and maintaining necessary historical records.
     *
     * @param vo an instance of WorkRequestAndResponseVo containing work request details,
     *           including document type, request ID, worker ID, and other necessary information.
     */
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void reception(WorkRequestAndResponseVo vo){
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        if(vo.getDocTypeCd() == null){}
        else if(vo.getDocTypeCd().equals(DocumentType.REPLY_PURPOSE.getCodeId())){
            // Request for implementation addition

            vo.setRegDt(LocalDateTime.now());
            vo.setRegId(loginUser.getUserId());
            vo.setWorkStatus(WorkStatus.DISPATCHED.getCodeId());
            vo.setRegUserNm(loginUser.getUserNm());
            WorkRequestVo requestVo = vo.toRequestVo();
            
            workRequestService.insertWorkRequest(requestVo);

            // 이행자 추가
            vo.getWorkResponseVos().forEach(workResponseVo -> {
                UserMemberVo user = userService.getUserMemberDetail(workResponseVo.getUserId());
                if(user == null) return;
                workResponseVo.setWorkReqId(requestVo.getWorkReqId());
                workResponseVo.setDeptCd(user.getDeptCd());
                workResponseVo.setJobCd(user.getJobCd());
                workResponseVo.setUserNm(user.getUserNm());
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

    /**
     * Rejects the reception process by updating the received information, updating the document status,
     * and inserting a rejection record in the history.
     *
     * @param vo an object of type {@code UpdateReceivedInfoVo} containing the information
     *           that needs to be updated for rejecting the reception. It includes details such as
     *           document ID and reception status.
     */
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

    /**
     * Marks the specified document as completed and logs the action in the history.
     *
     * @param docId the unique identifier of the document to be marked as completed
     */
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

    /**
     * Saves a reply document along with its associated metadata, approvals, and connections.
     * Handles the creation, update, and linking of various entities related to the reply document
     * in a transactional manner. This method ensures that the operations are rolled back in case
     * of an SQLIntegrityConstraintViolationException.
     *
     * @param vo the {@link InsertDocumentVo} object containing all the necessary data
     *           for creating and managing the reply document.
     */
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
        // file add
        DraftDocumentVo draftVo = draftDocumentService.getDraftDocument(vo.getAarsDocId());
        String fileNm = "OfficialDocument.pdf";
        EasFileVo fileVo = EasFileVo.builder()
                .fileId(draftVo.getAarsFileId())
                .orgFileId(draftVo.getAarsFileId())
                .fileType(EasFileType.DRAFT_DOCUMENT_FILE.getCodeId())
                .orgFileNm(fileNm)
                .pdfFileNm(fileNm)
                .deleteYn('N')
                .pdfFileId(draftVo.getAarsPdfFileId())
                .docId(vo.getDocId())
                .regId(loginId)
                .regDt(LocalDateTime.now())
                .build();
        easFileService.saveEasFile(fileVo);

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
                UserMemberVo user = userService.getUserMemberDetail("gduser1");
                ApprovalVo approvalVo = ApprovalVo.builder()
                        .docId(vo.getDocId())
                        .apvlOrd(addApprovalCount)
                        .apvlType(ApprovalType.REQUEST_REVIEW_AND_APPROVAL.getCodeId())
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .deptCd(user.getDeptCd())
                        .jobCd(user.getJobCd())
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
                .rcpDocId(vo.getDocId())
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
                    receivedInfoVo.setRcvDtm(now);
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

    /**
     * Rewrites an existing document by saving a new document and handling associated files.
     * Any files already uploaded are reassigned with new IDs and linked to the new document.
     * The old document is subsequently deleted.
     *
     * @param vo the data transfer object containing details of the document to be rewritten,
     *           including information about already uploaded files, document ID, and the
     *           source document ID to be replaced.
     * @throws SQLIntegrityConstraintViolationException if any database integrity constraint is violated during the transaction.
     */
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

    /**
     * Ends the processing of a rejected document by updating its status to completed
     * and recording the action in the document's history.
     *
     * @param docId the identifier of the document to be updated and logged
     */
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

    /**
     * Updates the work request and its associated responses, while maintaining history records.
     *
     * @param vo An instance of WorkRequestAndResponseVo that contains the work request data to be updated,
     *           along with the associated work responses.
     */
    @Override
    public void updateWorkRequest(WorkRequestAndResponseVo vo) {
        // update workRequest
        WorkRequestVo requestVo = vo.toRequestVo();
        workRequestService.updateWorkRequest(requestVo);
        // update workResponse
        workResponseService.deleteWorkRequestId(requestVo.getWorkReqId());
        vo.getWorkResponseVos().forEach(workResponseVo -> {
            UserMemberVo loginUser = userService.getUserMemberDetail(workResponseVo.getUserId());
            if(loginUser == null) return;
            workResponseVo.setWorkReqId(requestVo.getWorkReqId());
            workResponseVo.setDeptCd(loginUser.getDeptCd());
            workResponseVo.setJobCd(loginUser.getJobCd());
            workResponseVo.setUserNm(loginUser.getUserNm());
            workResponseVo.setRcvDtm(LocalDateTime.now());

            workResponseService.insertWorkResponse(workResponseVo);
        });
        // write history
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        HistoryVo historyVo = HistoryVo.builder()
                .docId(vo.getDocId())
                .userId(loginUser.getUserId())
                .actType(ActionType.UPDATE_REQUEST.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.UPDATE_REQUEST.getCodeId(), loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }

    /**
     * Updates the main responser for the provided document information.
     * This method updates the received information and logs the action in the history.
     *
     * @param vo the object containing the information required to update the main responser,
     *           including document details and other necessary attributes
     */
    @Override
    public void updateMainResponser(UpdateReceivedInfoVo vo) {
        // update main Responser
        receivedInfoService.updateReceivedInfo(vo);
        // history
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        HistoryVo historyVo = HistoryVo.builder()
                .docId(vo.getDocId())
                .userId(loginUser.getUserId())
                .actType(ActionType.UPDATE_MAIN_RESPONSER.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.UPDATE_MAIN_RESPONSER.getCodeId(), loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }

    /**
     * Inserts a new work request along with associated work responses and logs the action in the history.
     *
     * @param vo the {@code WorkRequestAndResponseVo} object containing the data needed to create the work request,
     *           associated work responses, and history record.
     */
    @Override
    public void insertWorkRequest(WorkRequestAndResponseVo vo) {
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        // request add
        vo.setRegDt(LocalDateTime.now());
        vo.setRegId(loginUser.getUserId());
        vo.setRegUserNm(loginUser.getUserNm());
        vo.setWorkStatus(WorkStatus.DISPATCHED.getCodeId());

        WorkRequestVo requestVo = vo.toRequestVo();

        workRequestService.insertWorkRequest(requestVo);
        //  response add
        vo.getWorkResponseVos().forEach(workResponseVo -> {
            UserMemberVo user = userService.getUserMemberDetail(workResponseVo.getUserId());
            if(user == null) return;
            workResponseVo.setWorkReqId(requestVo.getWorkReqId());
            workResponseVo.setDeptCd(user.getDeptCd());
            workResponseVo.setJobCd(user.getJobCd());
            workResponseVo.setUserNm(user.getUserNm());
            workResponseVo.setRcvDtm(LocalDateTime.now());

            workResponseService.insertWorkResponse(workResponseVo);

        });
        //  history add
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType(ActionType.INSERT_EXECUTOR.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.INSERT_EXECUTOR.getCodeId(),loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }

    /**
     * Registers a work response by updating the work response data, updating the work request status,
     * and recording the history of changes made based on the provided information in the UpdateWorkResponseVo object.
     *
     * @param vo the UpdateWorkResponseVo containing the information required to update the work response,
     *           determine the work request status, and log the changes in the history.
     */
    @Override
    public void registerWorkResponse(UpdateWorkResponseVo vo) {
        // update WorkResponse
        workResponseService.updateWorkResponse(vo);

        WorkResponseVo workResponseVo = workResponseRepository.getResponse(vo.getRspnsId());
        //update WorkRequestStatus
        if(isWorkEnd(vo.getWorkReqId())){
            WorkRequestVo workRequestVo = WorkRequestVo.builder()
                    .workReqId(workResponseVo.getWorkReqId())
                    .workStatus(WorkStatus.EXECUTION_COMPLETE.getCodeId())
                    .build();
            workRequestService.updateWorkRequest(workRequestVo);
        }else{
            WorkRequestVo workRequestVo = WorkRequestVo.builder()
                    .workReqId(workResponseVo.getWorkReqId())
                    .workStatus(WorkStatus.PENDING_EXECUTION.getCodeId())
                    .build();
            workRequestService.updateWorkRequest(workRequestVo);
        }
        //history
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(receivedInfoService.getDocIdByRcvId(vo.getRcvId()))
                .actType(ActionType.ADD_EXECUTOR_DETAILS.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.ADD_EXECUTOR_DETAILS.getCodeId(),loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }

    /**
     * Deletes a work request, including its associated work response,
     * and logs the deletion in the history.
     *
     * @param workReqId the unique identifier of the work request to delete
     */
    @Override
    public void deleteWorkRequest(int workReqId) {
        String docId = workRequestService.getDocIdByWorkReqId(workReqId);
        //delete workRequest
        workRequestService.deleteWorkRequest(workReqId);
        //delete workResponse
        workResponseService.deleteWorkRequestId(workReqId);
        //add history

        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(docId)
                .actType(ActionType.DELETE_EXECUTOR.getCodeId())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(ActionType.DELETE_EXECUTOR.getCodeId(),loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }

    /**
     * Processes the approval request for a document.
     * Updates the approval status and handles actions
     * required when the approval order is final or intermediate.
     * Logs the action in the history.
     *
     * @param vo The UpdateApprovalVo object containing approval information such as
     *           document ID, approval ID, approval status, and resolution information.
     */
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

    /**
     * Processes the approval of a reply document by updating its approval status and related information.
     * This method handles updating approval details, recipient statuses, document statuses, and maintaining history logs.
     * It also checks the approval order and handles the transition of approval responsibilities.
     *
     * @param vo an instance of {@link UpdateApprovalVo} containing details of the approval to be updated.
     *           This includes the document ID, approval ID, and relevant approval status.
     */
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

    /**
     * Processes a review and approval request for a specific document. This method handles updating
     * approval statuses, managing recipient information, and tracking the approval history.
     *
     * @param vo An {@link UpdateApprovalVo} object containing details of the approval and document to
     *           be processed, such as document ID, approval ID, and the current approval status to be updated.
     */
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

    /**
     * Checks if the document identified by the given docId has reached its end state.
     *
     * @param docId the identifier of the document to be checked
     * @return {@code true} if the document has either no received information or if all
     *         received information statuses are marked as completed response, otherwise {@code false}
     */
    public boolean isEnd(String docId){
        List<ReceivedInfoVo> receivedInfo = receivedInfoService.getReceivedInfo(docId);

        if (receivedInfo.isEmpty()) {
            return true;
        }

        return receivedInfo.stream()
                .allMatch(rcvInfoVo -> rcvInfoVo.getRcvStatus().equals(ReceiveStatus.COMPLETED_RESPONSE.getCodeId()));
    }

    /**
     * Checks if all work responses related to a specific work request are completed.
     *
     * @param workReqId the ID of the work request to check
     * @return true if there are no work responses or if all the work responses have non-null response dates,
     *         false otherwise
     */
    public boolean isWorkEnd(int workReqId){
        List<WorkResponseVo> responseVos = workResponseService.getWorkResponses(workReqId);

        if(responseVos.isEmpty()) {
            return true;
        }

        return responseVos.stream()
                .allMatch(responseVo -> responseVo.getRspnsDtm() != null);
    }


    /**
     * Updates the read date and time of a received message and sets its status to "viewed".
     *
     * @param rcvId the ID of the received message to be updated
     * @return the number of rows affected by the update operation
     */
    @Override
    public int updateReadDateTime(int rcvId) {
        UpdateReceivedInfoVo vo = UpdateReceivedInfoVo.builder()
                .rcvId(rcvId)
                .checkDtm(LocalDateTime.now())
                .rcvStatus(ReceiveStatus.VIEWED.getCodeId())
                .build();
        return receivedInfoService.updateReceivedInfo(vo);
    }

    /**
     * Updates the read approval status of an approval entry.
     *
     * @param apvlId the unique identifier of the approval to be updated
     */
    public void updateReadApproval(int apvlId){
        UpdateApprovalVo vo = UpdateApprovalVo.builder()
                .apvlId(apvlId)
                .checkDtm(LocalDateTime.now())
                .apvlStatusCd(ApprovalStatus.VIEWED.getCodeId())
                .build();
         approvalService.updateApproval(vo);
    }

    /**
     * Converts an array of strings into a single string with elements separated by commas.
     *
     * @param arrayS the array of strings to be converted into a single string
     * @return a string representation of the array with elements joined by commas
     */
    public String arrayToString(String[] arrayS){
        return String.join(",", arrayS);
    }

    /**
     * Deletes a document identified by the provided document ID from multiple related services.
     *
     * @param docId the unique identifier of the document to be deleted
     */
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