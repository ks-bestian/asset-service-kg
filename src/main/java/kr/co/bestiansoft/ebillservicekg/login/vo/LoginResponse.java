package kr.co.bestiansoft.ebillservicekg.login.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;
import lombok.Data;

@Data
public class LoginResponse{

	private Account loginInfo;
    private boolean result;
    private String msg;
    private String token;
    private List<ComCodeDetailVo> comCodes;
    private List<BaseCodeVo> baseCodes;
    private List<ProposerVo> srvcJobs;
    private List<CcofVo> ccofList;

}
