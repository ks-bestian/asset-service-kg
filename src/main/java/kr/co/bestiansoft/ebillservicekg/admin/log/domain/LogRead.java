package kr.co.bestiansoft.ebillservicekg.admin.log.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ApiModel(description = "접속이력 조회")
@Data
public class LogRead {

	@ApiModelProperty(value = "접속자 아이디")
    private String userId;

	@ApiModelProperty(value = "접속 url")
    private String reqUrl;

    @ApiModelProperty(value = "접속일자 조회 시작일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @ApiModelProperty(value = "접속일자 조회 종료일")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Builder
	public LogRead(String userId, String accessIp, String reqUrl, LocalDate startDate, LocalDate endDate) {
		this.userId = userId;
		this.reqUrl = reqUrl;
		this.startDate = startDate;
		this.endDate = endDate;
	}

}
