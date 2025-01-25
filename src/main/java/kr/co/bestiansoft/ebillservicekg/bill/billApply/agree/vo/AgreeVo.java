package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo;

import java.time.LocalDateTime;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class AgreeVo extends ComDefaultVO {

	//등록번호(사회토론번호?)
	private String sclDscRcpNmb;
	//안건아이디
	private String billId;
	//안건이름
	private String billName;
	//안건이름kg
    private String billNameKg;
    //안건이름ru
    private String billNameRu;
    //제안자
    private String ppsrNm;
    //제안자 아이디
    private String ppsrId;

    //상태
    private String statCd;
    private String statNm;
    //동의일시
    private LocalDateTime signDt;

    //제알일
    private String ppslDt;

    //동의여부
    private String agreeYn;

    private String wtYn;
    //비고
    private String etc;
    //비고kg
    private String etcKg;
    //비고ru
    private String etcRu;
    //제안자 대상
    private String proposerId;

    //정당이름
    private String polyNm;
    //정당코드
    private String polyCd;
    //의원이름
    private String memberNm;
    private String memberNmKg;
    private String memberNmRu;
}
