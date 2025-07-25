package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngFileVo;
import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class MtngAllVo extends ComDefaultVO {

	private Long mtngId;
	private String mtngTypeCd;
	private String mtngTypeNm;
	private String cmtId;
	private String cmtNm;
	private String deptCd;
	private String deptNm;
	private Long dgrCd;
	private String openDtm;
	private String closeDtm;
	private String mtngPlc;
	private String ageCd;
	private String rmk;
	private String statCd;
	private String statNm;
	private String dueDtm;
	private String cmtSeNm;
	private String sessionNo;
	private Long sessionOrd;


	/* 참석자 관련 */
    private List<MemberVo> attendantList;

    /* 안건 관련 */
    private String billId;
    private String rsltCd;
    private String rsltNm;
    private List<AgendaVo> agendaList;

    /* 파일 관련*/
    private List<MtngFileVo> reportList;
    private MultipartFile[] files;
    private String[] fileKindCds;

}
