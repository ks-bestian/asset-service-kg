package kr.co.bestiansoft.ebillservicekg.bill.billApply.revoke.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.Data;

@Data
public class RevokeResponse {

	RevokeVo revokeDetail;
	
	List<RevokeVo> proposerList;
	
	List<EbsFileVo> fileList;
}
