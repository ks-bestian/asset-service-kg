package kr.co.bestiansoft.ebillservicekg.admin.log.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Logs {

    private int histId;
    private String userId;
    private String userNm;
    private String accessIp;
    private String reqUrl;
    private String reqMethod;
    private LocalDateTime reqDt;

    @Builder
	public Logs(int histId, String userId, String userNm, String accessIp, String reqUrl, String reqMethod, LocalDateTime reqDt) {
		this.histId = histId;
		this.userId = userId;
		this.userNm = userNm;
		this.accessIp = accessIp;
		this.reqUrl = reqUrl;
		this.reqMethod = reqMethod;
		this.reqDt = reqDt;
	}
    
}
