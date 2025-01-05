package kr.co.bestiansoft.ebillservicekg.process.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ProcessVo extends ComDefaultVO {

	private String bpDfId;
	private String bpDfNm;
	private String rmk;
	private String bpInstanceId;
	private String billId;
	private String startDt;
	private String endDt;
	private String status;
	private String currentStepId;
	private String stepId;
	private String stepNm;
	private String nextStepId;
	private String stepType;
	private String reqAuthId;
	private String stepOrd;
	private String taskId;
	private String taskNm;
	private String assignedTo;
	private String dueDt;
	private String completedDt;
	private String billAprvNo;

	private ProcessVo prevStepVo;
	private ProcessVo nextStepVo;


}
