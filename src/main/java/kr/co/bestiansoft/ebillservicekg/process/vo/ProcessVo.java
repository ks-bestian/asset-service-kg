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
	private String trgtUserId;//Work Allocation Status ID
	private String dueDt;
	private String completedDt;
	private String billAprvNo;
	private String taskStatus;//Work status P: Progress and Allocation C: Completion
	private String deptCd;// dept code

	private String srvcCd;
	private String jobCd;
	private String atrzMngNo;//Payment management number

	private String cmtCd;
	private String cmtSeCd;


}
