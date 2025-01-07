package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo;

import java.time.LocalDateTime;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ProcessVo extends ComDefaultVO {

	private String billId;
	private String procId;

	private Long ord;
	private String reqUsrId;
	private Long reqAuthId;
	private String reqDeptId;
	private LocalDateTime reqDt;
	private Long rcpAuthId;
	private String rcpDeptId;
	private String procUsrId;
	private LocalDateTime procDt;
	private String procKindCd;
	private String procYn;
	private String rsltCd;
	private String rsltDt;
	private String rmk;
	private String prsntDt;
	private String billAprvNo;

}
