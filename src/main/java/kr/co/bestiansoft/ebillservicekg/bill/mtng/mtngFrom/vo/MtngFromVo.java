package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class MtngFromVo extends ComDefaultVO {

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
	private String dueDtm;
	private String statNm;

    private List<MemberVo> attendantList;
    private List<AgendaVo> agendaList;



}
