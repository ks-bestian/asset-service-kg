package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.Impl;

import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.DraftDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                .tmlmtDtm(vo.getTmlmtDtm() != null ?vo.getTmlmtDtm().atStartOfDay() : null)
                .docSubtle(vo.getDocSubtle())
                .senderId(loginId)
                .docStatusCd("DMS01")
                .digitalYn('Y')
                .userId(loginId)
                .docLng(arrayToString(vo.getDocLng()))
                .docAttrbCd(arrayToString(vo.getDocAttrbCd()))
                .senderNm(loginUser.getUserNm())
                .regDtm(now)
                .deptCd(loginUser.getDeptCd())
                .build();



        result += documentService.saveOfficialDocument(documentVo);

        draftDocumentService.updateDraftStatus(vo.getAarsDocId(), "DS02");


        /* save eas_approval */
        String[] approvalIds = vo.getApprovalIds();
        for(int i =0 ; i < approvalIds.length ; i ++){
            UserMemberVo user = userService.getUserMemberDetail(approvalIds[i]);
            ApprovalVo approvalVo = ApprovalVo.builder()
                    .docId(vo.getDocId())
                    .apvlStatusCd("AS01")
                    .apvlOrd(i+1)
                    .userId(user.getUserId())
                    .userNm(user.getUserNm())
                    .deptCd(user.getDeptCd())
                    .jobCd(user.getJobCd())
                    .build();
            if(i ==0) {
                approvalVo.setRcvDtm(now);
                approvalVo.setApvlStatusCd("AS02");
            }

            result += approvalService.insertApproval(approvalVo);
        }


        /* eas_received_info */
        List<Map<String, String>> receivedIds = vo.getReceiverIds();
        for(int i=0 ; i < receivedIds.size() ; i++){
            log.info("receivedIds.get({}).get(\"isExternal\") = {}",i,receivedIds.get(i).get("isExternal"));
            if(receivedIds.get(i).get("isExternal").equals("false")){
                UserMemberVo user = userService.getUserMemberDetail(receivedIds.get(i).get("userId"));
                ReceivedInfoVo receivedInfoVo = ReceivedInfoVo.builder()
                        .docId(vo.getDocId())
                        .rcvStatus("RS00")
                        .userId(user.getUserId())
                        .userNm(user.getUserNm())
                        .deptCd(user.getDeptCd())
                        .rcvOrd(i+1)
                        .build();

                result += receivedInfoService.insertReceivedInfo(receivedInfoVo);
            }else{
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
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    @Override
    public void approve(UpdateApprovalVo vo) {
        List<ApprovalVo> approvalVoList = approvalService.getApprovals(vo.getDocId());
        ApprovalVo approvalVo = approvalService.getApproval(vo.getApvlId());
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        log.info(approvalVoList.toString());
        /** AS01 대기, AS02 발송, AS03 열람, AS04 승인, AS05 반려*/
        log.info("approvalOrd : {} , approvalSize : {}",approvalVo.getApvlOrd(), approvalVoList.size());
        if(approvalVo.getApvlOrd() == approvalVoList.size()) {
            List<ReceivedInfoVo> receivedInfoVo =  receivedInfoService.getReceivedInfo(vo.getDocId());
            receivedInfoVo.forEach(rcvInfoVo -> {
                UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                        .rcvId(rcvInfoVo.getRcvId())
                        .rcvDtm(LocalDateTime.now())
                        .rcvStatus("RS01")
                        .build();
                // 받는사람들 상태 변경
                receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            });
            // 전송 상태로 변경
            documentService.updateStatusOfficialDocument(vo.getDocId(), "DMS02");
            // todo 번호 부여
        }else{
            Optional<ApprovalVo> nextApprovalVo = approvalVoList.stream().filter(s-> s.getApvlOrd() == approvalVo.getApvlOrd()+1).findFirst();

            if(nextApprovalVo.get().getApvlStatusCd().equals("AS01")){

                UpdateApprovalVo  nextUpdateApprovalVo= UpdateApprovalVo
                        .builder()
                        .apvlId(nextApprovalVo.get().getApvlId())
                        .resDtm(LocalDateTime.now())
                        .apvlStatusCd("AS02")
                        .build();

                UpdateApprovalVo  updateApprovalVo= UpdateApprovalVo.builder()
                        .apvlId(vo.getApvlId())
                        .resDtm(LocalDateTime.now())
                        .apvlStatusCd("AS04")
                        .build();

                approvalService.updateApproval(nextUpdateApprovalVo);
                approvalService.updateApproval(updateApprovalVo);
            }
        }

        // 기존 approval 변경
        vo.setResDtm(LocalDateTime.now());
        vo.setApvlStatusCd("AS04");
        approvalService.updateApproval(vo);

        // history 저장
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType(vo.getApvlStatusCd())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(vo.getApvlStatusCd(),loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }
    // DMS04
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void approveReject(UpdateApprovalVo vo){
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());

        vo.setResDtm(LocalDateTime.now());
        vo.setApvlStatusCd("AS05");
        // approval update
        approvalService.updateApproval(vo);
        // document Status 변경
        documentService.updateStatusOfficialDocument(vo.getDocId(), "DMS04");

        // history
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType(vo.getApvlStatusCd())
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(vo.getApvlStatusCd(),loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void reception(WorkRequestAndResponseVo vo){
        if(vo.getDocTypeCd() == null){}
        else if(vo.getDocTypeCd().equals("DMT01")){
            //답변용

            // 이행요청 추가
            vo.setRegDt(LocalDateTime.now());
            vo.setRegId(new SecurityInfoUtil().getAccountId());
            vo.setWorkStatus("WS01");
            workRequestService.insertWorkRequest(vo.toRequestVo());
            // 이행자 추가
            vo.getWorkResponseVos().forEach(workResponseVo -> {
                UserMemberVo loginUser = userService.getUserMemberDetail(workResponseVo.getUserId());
                workResponseVo.setDeptCd(loginUser.getDeptCd());
                workResponseVo.setJobCd(loginUser.getJobCd());
                workResponseVo.setUserNm(loginUser.getUserNm());

                workResponseService.insertWorkResponse(workResponseVo);
            });
            // receivedInfo 상태 변경, 주 이행자 추가
            UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                    .rcvId(vo.getRcvId())
                    .workerId(vo.getWorkerId())
                    .rcpDtm(LocalDateTime.now())
                    .rcvStatus("RS03")
                    .build();
            receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            // document status 변경
            documentService.updateStatusOfficialDocument(vo.getDocId() , "DMS03");
            // history
            UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
            HistoryVo historyVo = HistoryVo.builder()
                    .userId(loginUser.getUserId())
                    .docId(vo.getDocId())
                    .actType("RS03")
                    .actDtm(LocalDateTime.now())
                    .actDetail(historyService.getActionDetail("RS03",loginUser.getUserNm()))
                    .userNm(loginUser.getUserNm())
                    .build();
            historyService.insertHistory(historyVo);


        }else if(vo.getDocTypeCd().equals("DMT02")){
            // 확인용 - 종료
            // receivedInfo Update
            UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                    .rcvId(vo.getRcvId())
                    .rcpDtm(LocalDateTime.now())
                    .rcvStatus("RS05")
                    .build();
            receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            endDocument(vo.getDocId());
        }else if(vo.getDocTypeCd().equals("DMT03")){
            //서명용 - 서명
            UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                    .rcvId(vo.getRcvId())
                    .rcpDtm(LocalDateTime.now())
                    .hashFileId(vo.getHashFileId())
                    .rcvStatus("RS05")
                    .build();
            receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);

            UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
            HistoryVo historyVo = HistoryVo.builder()
                    .userId(loginUser.getUserId())
                    .docId(vo.getDocId())
                    .actType("RS05")
                    .actDtm(LocalDateTime.now())
                    .actDetail(historyService.getActionDetail("RS05",loginUser.getUserNm()))
                    .userNm(loginUser.getUserNm())
                    .build();
            historyService.insertHistory(historyVo);

            endDocument(vo.getDocId());
            // 서명하는 거 하나 endDocument;
        }
    }
    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    public void rejectReception(UpdateReceivedInfoVo vo){
        //receivedInfo update
        vo.setRjctDtm(LocalDateTime.now());
        vo.setRcvStatus("RS04");
        receivedInfoService.updateReceivedInfo(vo);
        // documentStatus update
        documentService.updateStatusOfficialDocument(vo.getDocId(), "DMS04");
        //insert history
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType("RS04")
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail("RS04",loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);
    }
    public void endDocument(String docId){
       if(isEnd(docId)){
           UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
           HistoryVo historyVo = HistoryVo.builder()
                   .userId(loginUser.getUserId())
                   .docId(docId)
                   .actType("DMS05")
                   .actDtm(LocalDateTime.now())
                   .actDetail(historyService.getActionDetail("DMS05",loginUser.getUserNm()))
                   .userNm(loginUser.getUserNm())
                   .build();
           historyService.insertHistory(historyVo);
            documentService.updateStatusOfficialDocument(docId, "DMS05");
       }else{
           UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());
           HistoryVo historyVo = HistoryVo.builder()
                   .userId(loginUser.getUserId())
                   .docId(docId)
                   .actType("DMS03")
                   .actDtm(LocalDateTime.now())
                   .actDetail(historyService.getActionDetail("DMS03",loginUser.getUserNm()))
                   .userNm(loginUser.getUserNm())
                   .build();
           historyService.insertHistory(historyVo);
           documentService.updateStatusOfficialDocument(docId, "DMS03");
       }
    }
    public boolean isEnd(String docId){
        boolean result = true;
        List<ReceivedInfoVo>receivedInfo = receivedInfoService.getReceivedInfo(docId);
        for(ReceivedInfoVo rcvInfoVo : receivedInfo){
            if(!rcvInfoVo.getRcvStatus().equals("RS05")) result = false;
        }
        return result;
    }
    @Override
    public int updateReadDateTime(int rcvId) {
        UpdateReceivedInfoVo vo = UpdateReceivedInfoVo.builder()
                .rcvId(rcvId)
                .checkDtm(LocalDateTime.now())
                .rcvStatus("RS02")
                .build();
        return receivedInfoService.updateReceivedInfo(vo);
    }
    public void updateReadApproval(int apvlId){
        UpdateApprovalVo vo = UpdateApprovalVo.builder()
                .apvlId(apvlId)
                .checkDtm(LocalDateTime.now())
                .apvlStatusCd("AS03")
                .build();
         approvalService.updateApproval(vo);
    }
    public String arrayToString(String[] arrayS){
        return String.join(",", arrayS);
    }

}
