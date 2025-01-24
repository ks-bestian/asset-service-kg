package kr.co.bestiansoft.ebillservicekg.admin.billMng.vo;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class SystemBillVo extends ComDefaultVO {

	//순번
	private int seq;
	//안건아이디
	private String billId;
	//안건관련기타정보구분
	private String clsCd;
	//기타,비고,사유kg
	private String rmrkKg;
	//기타,비고,사유ru
	private String rmrkRu;
	//본회의장 회의일시
	private String mtnDt;
	//대통령거부일자
	private String prsdRjctDt;
	//법률검토결과코드
	private String lglRvwRsltCode;
	//법률행위결과코드
	private String lglActRsltCode;
	//결과처리일자
	private String rsltDt;
	
	//파일
    private MultipartFile[] files;
	private String[] fileKindCds;
	private String[] clsCds;
	
	//위원회 코드
	private String cmtCd;
	//회의 아이디
	private String mtngId;
	//회의 예정일시
	private String dueDtm;
	//회의결과코드
	private String rsltCd;
	//회의 차수
	private String dgrCd;
	
	private String[] rmks;
	private String[] orgFileIds;
	
}
