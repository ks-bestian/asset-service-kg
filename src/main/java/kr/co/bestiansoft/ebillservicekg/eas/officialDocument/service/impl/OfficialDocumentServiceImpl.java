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
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.InsertDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
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


    @Override
    public List<OfficialDocumentVo> getOfficialDocument(OfficialDocumentVo vo){
        return officialDocumentMapper.getOfficialDocument(vo);
    }
    public int saveOfficialDocument(OfficialDocumentVo vo){
        return officialDocumentMapper.saveOfficialDocument(vo);
    }

    @Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
    @Override
    public int saveAllDocument(InsertDocumentVo vo) {

        int result= 0 ;
        String loginId = new SecurityInfoUtil().getAccountId();
        UserMemberVo loginUser = userService.getUserMemberDetail(loginId);

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
                .build();



        result += saveOfficialDocument(documentVo);

        /* save eas_approval */
        String[] approvalIds = vo.getApprovalIds();
        for(int i =0 ; i < approvalIds.length ; i ++){
            UserMemberVo user = userService.getUserMemberDetail(approvalIds[i]);
            ApprovalVo approvalVo = ApprovalVo.builder()
                    .docId(vo.getDocId())
                    .apvlDtm(LocalDateTime.now())
                    .apvlStatusCd("AS01")
                    .apvlOrd(i)
                    .userId(user.getUserId())
                    .userNm(user.getUserNm())
                    .deptCd(user.getDeptCd())
                    .jobCd(user.getJobCd())
                    .build();


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
                        .rcvOrd(0)
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
                .actDtm(LocalDateTime.now())
                .actDetail(historyService.getActionDetail(actionType, loginUser.getUserNm()))
                .userNm(loginUser.getUserNm())
                .build();

        result += historyService.insertHistory(historyVo);

        return result;

    }

    @Override
    public void updateStatusOfficialDocument(String billId, String status) {
        officialDocumentMapper.updateStatus(billId,status);
    }

    public String arrayToString(String[] arrayS){
        return String.join(",", arrayS);
    }
}
