package kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseCodeVo extends ComDefaultVO {
    private Long seq;
    private String codeId;
    private String codeKind;
    private String codeNm;
    private String bgDt;
    private String edDt;
    private Integer ord;
    private String currYn;
    private String rmk;
    private String useYn;
    private String regNm;
    private String modNm;
}
