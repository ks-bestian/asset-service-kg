package kr.co.bestiansoft.ebillservicekg.admin.comCode.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComCodeVo extends ComDefaultVO{
    private Integer grpCode;
    private String grpCodeNm1;
    private String grpCodeNm2;
    private String grpCodeNm3;
    private String useYn;
    private String rmk;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private String regId;
    private String modId;
}
