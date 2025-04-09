package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.member.service.MemberService;
import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
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

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class OfficialDocumentServiceImpl implements OfficialDocumentService {

    private final OfficialDocumentMapper mapper;
    private final ApprovalService approvalService;
    private final ReceivedInfoService receivedInfoService;
    private final HistoryService historyService;
    private final UserService userService;

    @Override
    public List<OfficialDocumentVo> getOfficialDocument(OfficialDocumentVo vo){
        return mapper.getOfficialDocument(vo);
    }
    public int saveOfficialDocument(OfficialDocumentVo vo){
        return mapper.saveOfficialDocument(vo);
    }

    @Transactional
    @Override
    public int saveAllDocument(InsertDocumentVo vo) {
        int result= 0 ;
        System.out.println(vo.toString());
        String loginId = new SecurityInfoUtil().getAccountId();
        //todo senderNm, userNm 확인하는 코드 가져오기
        UserMemberVo loginUser = userService.getUserMemberDetail(loginId);
        log.info(loginUser.toString());
        /* save eas_document */
        //todo docLng, docAttrbCd, senderNm
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
                .docLng(null)
                .docAttrbCd(null)
                .senderNm(loginUser.getMemberNmKg())
                .build();


//        result += saveOfficialDocument(documentVo);

        /* save eas_approval */
        //todo  apvlOrd for문 추가하면서 i로 변경, userId 받아서 정보가져와서 채우기
        log.info("approval");
        String[] approvalIds = vo.getApprovalIds();
        for(int i =0 ; i < approvalIds.length ; i ++){
            UserMemberVo user = userService.getUserMemberDetail(approvalIds[i]);
            log.info(user.toString());

            ApprovalVo approvalVo = ApprovalVo.builder()
                    .docId(vo.getDocId())
                    .apvlDtm(LocalDateTime.now())
                    .apvlStatusCd("AS01")
                    .apvlOrd(0)
                    .userId(user.getUserId())
                    .userNm(user.getUserNm())
                    .deptCd(user.getDeptCd())
                    .jobCd(user.getJobCd())
                    .build();

            log.info(approvalVo.toString());
        }
//        result += approvalService.insertApproval(approvalVo);

        /* eas_received_info */
        /* todo 외부기관 */
        String[] receivedIds = vo.getReceiverIds();
        for(int i=0 ; i < receivedIds.length ; i++){
            UserMemberVo user = userService.getUserMemberDetail(receivedIds[i]);
            log.info(user.toString());
            ReceivedInfoVo receivedInfoVo = ReceivedInfoVo.builder()
                    .docId(vo.getDocId())
                    .rcvStatus("RS001")
                    .rcvDtm(LocalDateTime.now())
                    .userId(user.getMemberId())
                    .userNm(user.getUserNm())
                    .deptCd(user.getDeptCd())
                    .extOrgnCd(null)
                    .rcvOrd(0)
                    .build();
            log.info(receivedInfoVo.toString());
        }

//        result += receivedInfoService.insertReceivedInfo(receivedInfoVo);

        /* eas_history */
        HistoryVo historyVo = HistoryVo.builder()
                .docId(vo.getDocId())
                .userId(loginId)
                // 문서작성
                .actType("AT01")
                .actDtm(LocalDateTime.now())
                .actDetail(null)
                .userNm(loginUser.getMemberNmKg())
                .build();
        log.info(historyVo.toString());
//        result += historyService.insertHistory(historyVo);

        return result;
    }

}
