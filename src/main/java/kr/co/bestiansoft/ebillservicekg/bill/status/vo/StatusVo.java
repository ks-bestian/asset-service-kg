package kr.co.bestiansoft.ebillservicekg.bill.status.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class StatusVo extends ComDefaultVO {

	//calendar
	//회의종류
	private String mtngTypeCd;
	//회의이름
	private String mtngSj;
	//회의예정일
	private String openDtm;
	//회의장소
	private String mtngPlc;


	//monitoring

}
