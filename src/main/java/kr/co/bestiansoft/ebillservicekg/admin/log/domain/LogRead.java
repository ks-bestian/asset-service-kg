package kr.co.bestiansoft.ebillservicekg.admin.log.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.request.ListRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "접속이력 조회")
@Data
public class LogRead extends ListRequest {


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

}
