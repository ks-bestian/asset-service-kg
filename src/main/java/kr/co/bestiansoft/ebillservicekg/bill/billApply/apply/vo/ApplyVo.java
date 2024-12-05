package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ApplyVo extends ComDefaultVO {

	//안건아이디
	private String billId;
	//안건번호
	private String billNo;
	//안건종류
	private String billKind;
	//대수
	private String naTermCd;
	//안건이름-kg
	private String billNameKg;
	//안건이름-ru
	private String billNameRu;
	//비고-kg
	private String etcKg;
	//비고-ru
	private String etcRu;
	//제안자 종류
	private String ppslKndCd;
	//제안자 이름
	private String ppsrNm;
	//제안자 아이디
	private String ppsrId;
	//안건 상태
	private String statCd;
	//제개정타입(제정/개정/폐지)
	private String billTy;
	//법률아이디
	private Integer lawId;
	//철회사유
	private String wtCn;
	
	//제안자 목록
	private List<String> proposerList;
	
	//순서
	private int ord;
	//정당 코드
	private String polyCd;
	//정당 이름
	private String polyNm;
	//서명
	private String signDt;
	//날짜변환
	private String regDate;
	
	
	
}
