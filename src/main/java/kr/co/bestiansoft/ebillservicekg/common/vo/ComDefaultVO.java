package kr.co.bestiansoft.ebillservicekg.common.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ComDefaultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String billId;
	private String regId;
	private LocalDateTime regDt;

	private String modId;
	private LocalDateTime modDt;

	private String mdfrId;
	private LocalDateTime mdfcnDt;

	private String regNm;

	private String modNm;

    private String lang;


}
