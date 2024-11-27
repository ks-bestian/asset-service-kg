package kr.co.bestiansoft.ebillservicekg.admin.bbs.vo;

import java.time.LocalDateTime;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class BoardVo extends ComDefaultVO {

	private Long brdId;
    private String brdSj;
    private String brdType;
    private String brdCn;
    private LocalDateTime regDt;
    private String regDtNm;
    private String regId;
    private Long notiInqCnt;
    private String pupYn;
    private String pupBgDt;
    private String pupEdDt;
    private Long ord;
    private String fileGroupId;
    private Long num;
    private List<BoardVo> delList;
    
}
