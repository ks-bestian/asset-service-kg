package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class AgendaVo extends ComDefaultVO {

	private Long mtngId;
	private String billId;
	private String billNo;
	private String billName;
	private String ppsrNm;
	private String ppsrId;
	private String ppslDt;
	private int ord;
	private String rsltCd;
	private String rsltNm;
	private String rmk;


	private String deptCd;
	private String cmtCd;
	private String cmtSeCd;

	private String currentStepId;
	private String statCd;
	private String statNm;
	private String sessionNo;
	private String sessionOrd;


}
