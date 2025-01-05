package kr.co.bestiansoft.ebillservicekg.process.controller;

import org.springframework.stereotype.Component;

import kr.co.bestiansoft.ebillservicekg.process.repository.ProcessMapper;
import kr.co.bestiansoft.ebillservicekg.process.vo.AuthConstants;
import kr.co.bestiansoft.ebillservicekg.process.vo.CmttVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProcessHandler {

	private final ProcessMapper processMapper;


	public ProcessVo createProcess(ProcessVo processVo) throws Exception {

		processVo.setBpDfId("1");//일단은 하나로 설정 여러개라면 안건유형에 따라서
		processVo.setStatus("P");//진행중
		processVo.setCurrentStepId("0");
		processMapper.insertBpInstance(processVo);

		//executeServiceTasks(processVo);

		return processVo;
    }

	public void executeServiceTasks(ProcessVo argVo) throws Exception {

		String stepId = argVo.getStepId();

		switch (stepId) {
	        case "0":
	        	executeService_0(argVo);
	            break;
	        case "1000":
	        	executeService_1000(argVo);
	            break;

	        case "1100":
	        	executeService_1100(argVo);
	            break;

	        case "1200":
	        	executeService_1200(argVo);
	            break;

	        case "1300":
	        	executeService_1300(argVo);
	            break;

	        case "1400":
	        	executeService_1400(argVo);
	            break;




	        default:
	            System.out.println("Unknown stepId.");
	            break;

		}

    }


		/*안건등록 시작점*/
		void executeService_0(ProcessVo argVo) throws Exception {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setTaskNm(argVo.getStepNm());
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setStatus("C");
			processMapper.insertBpTask(taskVo);
		}


		/*안건접수관리*/
		void executeService_1000(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setTaskNm(argVo.getStepNm());
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_21);//GD부서에 할당
			processMapper.insertBpTask(taskVo);

		}

		/*안건철회관리*/
		void executeService_1100(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setTaskNm(argVo.getStepNm());
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setStatus("P");
			taskVo.setAssignedTo("");//해당국회의원 할당
			processMapper.insertBpTask(taskVo);

		}

		/*법률부서검토관리*/
		void executeService_1200(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("법률부서검토관리");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_22);//법률부서에 할당

			processMapper.insertBpTask(taskVo);

		}

		/*의장접수(결재)*/
		void executeService_1300(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("의장접수");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_23);//의장 할당
			processMapper.insertBpTask(taskVo);

		}

		/*1차위원회 회의예정*/
		void executeService_1400(ProcessVo argVo) {

//			언어전문파트 의견서등록
//			심사부서 의견서등록
//			회의예정화면에서 회의안건으로 선택하여	회의를 저장한다.


			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("언어전문파트의견서등록");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_24);//언어전문파트 할당
			processMapper.insertBpTask(taskVo);

			taskVo.setTaskNm("심사부서의견서등록");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_25);//심사부서 할당
			processMapper.insertBpTask(taskVo);

		}

		/*1차위원회 회의결과등록*/
		void executeService_1500(ProcessVo argVo) {
	        //회의결과화면에서 결과정보를 등록한다.
			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("1차위원회 회의결과등록");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(cmttVo.getCmttId());//위원회 할당
			processMapper.insertBpTask(taskVo);

		}

		/*1차위원회 회의심사보고*/
		void executeService_1600(ProcessVo argVo) {

//			위원회권한의 사람이 의안상세화면에서
//			심사보고서를 등록한다.
			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("1차위원회 회의심사보고");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(cmttVo.getCmttId());//위원회 할당
			processMapper.insertBpTask(taskVo);

		}

		/*1차본회의 심사요청*/
		void executeService_1700(ProcessVo argVo) {

			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("1차본회의 심사요청");
			taskVo.setStatus("C");
			taskVo.setAssignedTo(cmttVo.getCmttId());//위원회 할당
			processMapper.insertBpTask(taskVo);

		}

		/*1차본회의 심사*/
		void executeService_1800(ProcessVo argVo) {

			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

//			본회의결과정보(가결/부결등) 등록
//			심사보고서등록
			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("본회의 심사요청");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(cmttVo.getCmttId());//위원회 할당
			processMapper.insertBpTask(taskVo);

		}

		/*1차 법적행위관리*/
		void executeService_1900(ProcessVo argVo) {

//			"1차 법적행위 검토 보고서등록
//			번역언어심사
//			법률검토
//			소관위
//			의장결재"

			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("법적행위 검토 보고서");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_26);//법적행위 할당
			processMapper.insertBpTask(taskVo);

			taskVo.setTaskNm("번역언어심사 의견서");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_27);//번역언어심사 할당
			processMapper.insertBpTask(taskVo);

			taskVo.setTaskNm("법률검토 의견서");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_28);//법률검토 할당
			processMapper.insertBpTask(taskVo);

			taskVo.setTaskNm("소관위 심사보고서");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(cmttVo.getCmttId());//위원회 할당
			processMapper.insertBpTask(taskVo);

			taskVo.setTaskNm("의장검토");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_23);//의장 할당
			processMapper.insertBpTask(taskVo);

		}

		/*2차위원회 회의예정*/
		void executeService_2000(ProcessVo argVo) {
			executeService_1400(argVo);
		}

		/*2차위원회 회의결과등록*/
		void executeService_2100(ProcessVo argVo) {
			executeService_1500(argVo);
		}

		/*2차위원회 회의심사보고*/
		void executeService_2200(ProcessVo argVo) {
			executeService_1600(argVo);
		}

		/*2차본회의 심사요청*/
		void executeService_2300(ProcessVo argVo) {
			executeService_1700(argVo);
		}

		/*2차본회의 심사*/
		void executeService_2400(ProcessVo argVo) {
			executeService_1800(argVo);
		}

		/*2차 법적행위관리*/
		void executeService_2500(ProcessVo argVo) {
			executeService_1900(argVo);
		}

		/*3차위원회 회의예정*/
		void executeService_2600(ProcessVo argVo) {
			executeService_1400(argVo);
		}

		/*3차위원회 회의결과등록*/
		void executeService_2700(ProcessVo argVo) {
			executeService_1500(argVo);
		}

		/*3차위원회 회의심사보고*/
		void executeService_2800(ProcessVo argVo) {
			executeService_1600(argVo);
		}

		/*3차본회의 심사요청*/
		void executeService_2900(ProcessVo argVo) {
			executeService_1700(argVo);
		}

		/*3차본회의 심사*/
		void executeService_3000(ProcessVo argVo) {
			executeService_1800(argVo);
		}

		/*3차 법적행위관리*/
		void executeService_3100(ProcessVo argVo) {
			executeService_1900(argVo);
		}

		/*정부이송 요청*/
		void executeService_3200(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("정부이송 요청");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_21);//일반부서(GD)
			processMapper.insertBpTask(taskVo);

		}

		/*공포*/
		void executeService_3300(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("공포");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_21);//일반부서(GD)
			processMapper.insertBpTask(taskVo);

		}

		/*종단점*/
		void executeService_9999(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setTaskNm(argVo.getStepNm());
			taskVo.setBpInstanceId(argVo.getBpInstanceId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setStatus("C");
			processMapper.insertBpTask(taskVo);
		}






}
