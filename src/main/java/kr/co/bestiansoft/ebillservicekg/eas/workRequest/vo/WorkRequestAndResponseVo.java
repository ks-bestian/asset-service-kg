package kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo;


import kr.co.bestiansoft.ebillservicekg.eas.workResponse.vo.WorkResponseVo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class WorkRequestAndResponseVo {
    //eas_document
    private String docId;
    private String docTypeCd;
    // workRequest
    private int workReqId;
    private String workCn;
    private char tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private char infoYn;
    private String workCycleCd;
    private String regId;
    private LocalDateTime regDt;
    private String workStatus;
    private int rcvId;
    //workResponse
    private List<WorkResponseVo> workResponseVos;
    //receivedInfo Used when registering to add the main executor; stores `hashFileId` for signature.
    private String workerId;
    private String hashFileId;


    @Builder
    public WorkRequestAndResponseVo(String docTypeCd, int workReqId, String docId, String workCn, char tmlmtYn, LocalDateTime tmlmtDtm, char infoYn, String workCycleCd, String regId, LocalDateTime regDt, String workStatus, int rcvId, List<WorkResponseVo> workResponseVos, String workId) {
        this.docTypeCd = docTypeCd;
        this.workReqId = workReqId;
        this.docId = docId;
        this.workCn = workCn;
        this.tmlmtYn = tmlmtYn;
        this.tmlmtDtm = tmlmtDtm;
        this.infoYn = infoYn;
        this.workCycleCd = workCycleCd;
        this.regId = regId;
        this.regDt = regDt;
        this.workStatus = workStatus;
        this.rcvId = rcvId;
        this.workResponseVos = workResponseVos;
        this.workerId = workId;
    }

    public WorkRequestAndResponseVo from (WorkRequestVo vo, List<WorkResponseVo> workResponseVo){
        return WorkRequestAndResponseVo.builder()
                .workReqId(vo.getWorkReqId())
                .workCn(vo.getWorkCn())
                .tmlmtYn(vo.getTmlmtYn())
                .tmlmtDtm(vo.getTmlmtDtm())
                .infoYn(vo.getInfoYn())
                .workCycleCd(vo.getWorkCycleCd())
                .regId(vo.getRegId())
                .regDt(vo.getRegDt())
                .workStatus(vo.getWorkStatus())
                .workResponseVos(workResponseVo)
                .rcvId(vo.getRcvId())
                .docId(vo.getDocId())
                .build();
    }

    public WorkRequestVo toRequestVo (){
        return WorkRequestVo.builder()
                .workReqId(this.getWorkReqId())
                .docId(this.getDocId())
                .workCn(this.getWorkCn())
                .infoYn(this.getInfoYn())
                .rcvId(this.getRcvId())
                .regDt(this.getRegDt())
                .regId(this.getRegId())
                .tmlmtDtm(this.getTmlmtDtm())
                .tmlmtYn(this.getTmlmtYn())
                .workCycleCd(this.getWorkCycleCd())
                .workStatus(this.getWorkStatus())
                .build();
    }
}
