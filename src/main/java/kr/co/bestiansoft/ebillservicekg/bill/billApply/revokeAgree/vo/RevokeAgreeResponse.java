package kr.co.bestiansoft.ebillservicekg.bill.billApply.revokeAgree.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import lombok.Data;

@Data
public class RevokeAgreeResponse {

	RevokeAgreeVo revokeAgreeDetail;
	List<RevokeAgreeVo> proposerList;
	List<EbsFileVo> fileList;
}