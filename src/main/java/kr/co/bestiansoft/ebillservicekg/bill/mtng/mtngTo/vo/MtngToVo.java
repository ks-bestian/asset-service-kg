package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.AgendaVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class MtngToVo extends ComDefaultVO {
	
	private Long mtngId;
	private String mtngTypeCd;
	private String mtngTypeNm;
	private String cmtId;
	private String cmtNm;
	private Long dgrCd;
	private String openDtm;
	private String closeDtm;
	private String mtngPlc;
	private String ageCd;
	private String rmk;
	private String statCd;
	private String dueDtm;
	
    private List<MemberVo> attendantList;
    private List<AgendaVo> agendaList;
    private MultipartFile[] files;
    
}
