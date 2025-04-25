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

    private int workReqId;
    private String docId;
    private String workCn;
    private char tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private char infoYn;
    private String workCycleCd;
    private String regId;
    private LocalDateTime regDt;
    private String workStatus;
    private List<WorkResponseVo> workResponseVos;

    @Builder
    public WorkRequestAndResponseVo(int workReqId, String docId, String workCn, char tmlmtYn, LocalDateTime tmlmtDtm, char infoYn, String workCycleCd, String regId, LocalDateTime regDt, String workStatus, List<WorkResponseVo> workResponseVos) {
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
        this.workResponseVos = workResponseVos;
    }

    public WorkRequestAndResponseVo from (WorkRequestVo vo, List<WorkResponseVo> workResponseVo){
        return WorkRequestAndResponseVo.builder()
                .workReqId(vo.getWorkReqId())
                .docId(vo.getDocId())
                .workCn(vo.getWorkCn())
                .tmlmtYn(vo.getTmlmtYn())
                .tmlmtDtm(vo.getTmlmtDtm())
                .infoYn(vo.getInfoYn())
                .workCycleCd(vo.getWorkCycleCd())
                .regId(vo.getRegId())
                .regDt(vo.getRegDt())
                .workStatus(vo.getWorkStatus())
                .workResponseVos(workResponseVo)
                .build();
    }
}
