package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.Data;

@Data
public class AgreeResponse {
	
    List<AgreeVo> proposerList;
    List<EbsFileVo> fileList;
    AgreeVo agreeDetail;
}
