package kr.co.bestiansoft.ebillservicekg.admin.log.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "Access Log History")
@Data
public class LogCreate {

	@NotBlank(message = "Accessor User ID Required.")
	@ApiModelProperty(value = "Access ID", required = true)
    private String userId;

	@NotBlank(message = "Accessor User IP Required.")
	@ApiModelProperty(value = "Access IP", required = true)
    private String accessIp;

	@ApiModelProperty(value = "Access URL", required = true)
    private String reqUrl;

	@ApiModelProperty(value = "Access Method", required = true)
    private String reqMethod;

	@Builder
	public LogCreate(String userId, String accessIp, String reqUrl, String reqMethod) {
		this.userId = userId;
		this.accessIp = accessIp;
		this.reqUrl = reqUrl;
		this.reqMethod = reqMethod;
	} 

}
