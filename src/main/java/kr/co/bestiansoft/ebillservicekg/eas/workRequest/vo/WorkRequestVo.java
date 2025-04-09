package kr.co.bestiansoft.ebillservicekg.eas.workRequest.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WorkRequestVo extends ComDefaultVO {

    private int workReqId;
    private String docId;
    private String workCn;
    private char tmlmtYn;
    private LocalDateTime tmlmtDtm;
    private char infoYn;
    private String workCycleCd;
    private String workStatus;
}