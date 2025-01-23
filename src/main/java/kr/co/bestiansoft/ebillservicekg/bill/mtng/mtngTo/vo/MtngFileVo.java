package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo;

import java.time.LocalDateTime;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class MtngFileVo extends ComDefaultVO {
	
	private Long seq;
	private Long mtngId;
	private String orgFileId;
	private String orgFileNm;
	private String pdfFileId;
	private String pdfFileNm;
	private String fileKindCd;
	private Long fileSize;
	private String deleteYn;
    
}
