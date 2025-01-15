package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class MtngAllVo extends ComDefaultVO {
	
	private Long mtngId;
	private String mtngTypeCd;
	private String cmtId;
	private String cmtNm;
	private Long dgrCd;
	private String openDtm;
	private String closeDtm;
	private String mtngPlc;
	private String ageCd;
	private String rmk;
	private String statCd;
	private String dueDtm;
    
}
