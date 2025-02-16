package kr.co.bestiansoft.ebillservicekg.process.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class CmttVo extends ComDefaultVO {

	private String billId;
	private String cmtId;
	private String cmtCd;
	private String cmtSeCd;
	private String cmtType;

}
