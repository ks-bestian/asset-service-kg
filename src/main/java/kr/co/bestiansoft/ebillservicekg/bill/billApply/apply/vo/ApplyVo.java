package kr.co.bestiansoft.ebillservicekg.bill.billApply.apply.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ApplyVo extends ComDefaultVO {

	//id
	private int id;
	//등록번호(사회토론번호?)
	private String sclDscRcpNmb;
	//안건아이디
	private String billId;
	//안건번호
	private String billNo;
	//안건종류
	private String billKind;
	//대수
	private String naTermCd;
	//안건이름
	private String billName;
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
	//철회사유kg
	private String wtCnKg;
	//철회사유ru
	private String wtCnRu;
	//제출날짜
	private String ppslDt;
	
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
	//아이디
	private String memberId;

	//파일
    private MultipartFile[] files;
	private String[] fileKindCds;
	
}
