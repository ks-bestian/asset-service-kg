package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class RevokeVo extends ComDefaultVO {

	//안건아이디
	private String billId;
	//안건아이디
	private String billNo;
    //등록번호(사회토론번호?)
	private String sclDscRcpNmb;
	//안건이름
	private String billName;
	//안건이름kg
	private String billNameKg;
	//안건이름ru
	private String billNameRu;
	//제안자
	private String ppsrNm;

	//제안일자
	private String ppslDt;

	//안건종류
	private String billKind;
	//비고
	private String etc;
	//비고kg
	private String etcKg;
	//비고ru
	private String etcRu;
	//철회사유
	private String wtCn;
	//철회사유kg
	private String wtCnKg;
	//철회사유ru
	private String wtCnRu;
	//철회대상자
	private String proposerId;
	//동의여부
	private String agreeYn;
	//철회동의여부
	private String revokeYn;
	//의원이름
	private String memberNm;
	//의원이름kg
	private String memberNmKg;
	//의원이름ru
	private String memberNmRu;

	private String polyCd;
	private String polyNm;

	private String statNm;
	private String statCd;
	private String naTermCd;
	private LocalDateTime wtDt;
	private LocalDateTime wtCncDt;

    /** process stepId */
    private String stepId;

    /** process taskId  */
    private Long taskId;

    /** process taskStatus */
    private String taskStatus;

    private MultipartFile[] files;
    private String[] myFileIds;

    private LocalDateTime revokeRegDt;
}
