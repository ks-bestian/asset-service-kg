package kr.co.bestiansoft.ebillservicekg.asset.faq.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FaqVo extends ComDefaultVO{
    private String faqId;
    private String eqpmntId;
    private String faqSe;
    private String qstn;
    private String ans;
    private int seq;
    

}
