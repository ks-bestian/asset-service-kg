package kr.co.bestiansoft.ebillservicekg.admin.comCode.vo;


import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComCodeDetailVo extends ComDefaultVO {
    private String codeId;
    private String codeNm1;
    private String codeNm2;
    private String codeNm3;
    private String codeNm;
    private Integer grpCode;
    private String useYn;
    private Integer ord;
    private String rmk;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private String regId;
    private String modId;

}
