package kr.co.bestiansoft.ebillservicekg.asset.faq.vo;

import kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo.BzentyVo;
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
    
    private String faqSeNm1;
    private String faqSeNm2;
    private String faqSeNm3;

    public FaqVo(FaqVo vo) {
    	this.eqpmntId = vo.getEqpmntId();
    	this.faqSe = vo.getFaqSe();
    	this.qstn = vo.getQstn();
    	this.ans = vo.getAns();
    	this.seq = vo.getSeq();
    	this.faqSeNm1 = vo.getFaqSeNm1();
    	this.faqSeNm2 = vo.getFaqSeNm2();
    	this.faqSeNm3 = vo.getFaqSeNm3();
    	
    }
    

}
