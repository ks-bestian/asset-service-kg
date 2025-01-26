package kr.co.bestiansoft.ebillservicekg.process.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ProcessVo extends ComDefaultVO {

	private String bpDfId;
	private String bpDfNm;
	private String rmk;
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
	private Long taskId;
	private String taskNm;
	private String assignedTo;
	private String trgtUserId;//작업할당상요자아이디
	private String dueDt;
	private String completedDt;
	private String billAprvNo;
	private String taskStatus;//작업상태 P:진행 및 할당 C:완료

	private String srvcCd;
	private String jobCd;

}
