package kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class RevokeAgreeVo extends ComDefaultVO {
	
	//안건아이디
	private String billId;
    //등록번호(사회토론번호?)
	private String sclDscRcpNmb;
	//안건이름kg
	private String billNameKg;
	//안건이름ru
	private String billNameRu;
	//제안자
	private String ppsrNm;
	//등록일
	private String regDate;
	//상태
	private String statCd;
	//안건종류
	private String billKind;
	//비고kg
	private String etcKg;
	//비고ru
	private String etcRu;
	//철회사유kg
	private String wtCnKg;
	//철회사유ru
	private String wtCnRu;
	//철회대상자
	private String proposerId;
	//동의여부
	private String agreeYn;
}
