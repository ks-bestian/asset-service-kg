package kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.Impl;

import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.history.service.HistoryService;
import kr.co.bestiansoft.ebillservicekg.eas.history.vo.HistoryVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.InsertDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.service.ReceivedInfoService;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.ReceivedInfoVo;
import kr.co.bestiansoft.ebillservicekg.eas.receivedInfo.vo.UpdateReceivedInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class DocumentWorkFlowServiceImpl implements DocumentWorkFlowService {
    private final ApprovalService approvalService;
    private final ReceivedInfoService receivedInfoService;
    private final HistoryService historyService;
    private final UserService userService;
    private final OfficialDocumentService officialDocumentService;

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
                .digitalYn('N')
                .userId(loginId)
                .docLng(arrayToString(vo.getDocLng()))
                .docAttrbCd(arrayToString(vo.getDocAttrbCd()))
                .senderNm(loginUser.getUserNm())
                .regDtm(now)
                .deptCd(loginUser.getDeptCd())
                .build();



        result += officialDocumentService.saveOfficialDocument(documentVo);

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
        log.info("receivedIds.size() = {}",receivedIds.size());
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

    @Override
    public void approve(UpdateApprovalVo vo) {
        List<ApprovalVo> approvalVoList = approvalService.getApproval(vo.getDocId());
        ApprovalVo approvalVo = approvalService.getApproval(vo.getApvlId());
        UserMemberVo loginUser = userService.getUserMemberDetail(new SecurityInfoUtil().getAccountId());

        /** AS01 대기, AS02 발송, AS03 열람, AS04 승인, AS05 반려*/
        log.info("approvalOrd : {} , approvalSize : {}",approvalVo.getApvlOrd(), approvalVoList.size()-1);
        if(approvalVo.getApvlOrd() == approvalVoList.size()-1) {
            List<ReceivedInfoVo> receivedInfoVo =  receivedInfoService.getReceivedInfo(vo.getDocId());
            receivedInfoVo.forEach(rcvInfoVo -> {
                UpdateReceivedInfoVo updateReceivedInfoVo = UpdateReceivedInfoVo.builder()
                        .rcvId(rcvInfoVo.getRcvId())
                        .rcpDtm(LocalDateTime.now())
                        .rcvStatus("RS01")
                        .build();
                // 받는사람들 상태 변경
                receivedInfoService.updateReceivedInfo(updateReceivedInfoVo);
            });
            // 전송 상태로 변경
//            officialDocumentService.updateStatusOfficialDocument(vo.getDocId(), "DMS02");

        }else{
            ApprovalVo nextApprovalVo = approvalVoList.get(approvalVo.getApvlOrd()+1);
            if(nextApprovalVo.getApvlStatusCd().equals("AS01")){

                UpdateApprovalVo updateApprovalVo = UpdateApprovalVo
                        .builder()
                        .apvlId(nextApprovalVo.getApvlId())
                        .apvlStatusCd("AS02")
                        .build();
                nextApprovalVo.setApvlDtm(LocalDateTime.now());
                nextApprovalVo.setApvlStatusCd("AS02");
                approvalService.updateApproval(updateApprovalVo);
            }
        }

        // 기존 approval 변경
        vo.setApvlDtm(LocalDateTime.now());
        vo.setApvlStatusCd("AS04");
        approvalService.updateApproval(vo);

        // history 저장
        HistoryVo historyVo = HistoryVo.builder()
                .userId(loginUser.getUserId())
                .docId(vo.getDocId())
                .actType("AS04")
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail("AS04",loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();
        historyService.insertHistory(historyVo);

    }
    @Override
    public int updateReadDateTime(String docId) {
        UpdateReceivedInfoVo vo = new UpdateReceivedInfoVo();
        vo.setDocId(docId);
        vo.setCheckDtm(LocalDateTime.now());
        vo.setUserId(new SecurityInfoUtil().getAccountId());
        vo.setRcvStatus("RS02");
        return receivedInfoService.updateReceivedInfo(vo);
    }
    public String arrayToString(String[] arrayS){
        return String.join(",", arrayS);
    }
}
