package kr.co.bestiansoft.ebillservicekg.common.file.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class EbsFileVo extends ComDefaultVO {

	private Long seq;
	private String billId;
	private String fileKindCd;
	private String orgFileId;
	private String orgFileNm;
	private String pdfFileId;
	private String pdfFileNm;
	private String rmk;
	private String opbYn;
	private String deleteYn;
	private Long fileSize;
	private Long mtngId;
	
	private String clsCd;
	private String detailSeq;
	
}
