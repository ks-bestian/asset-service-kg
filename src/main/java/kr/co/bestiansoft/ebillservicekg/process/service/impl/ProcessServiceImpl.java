package kr.co.bestiansoft.ebillservicekg.process.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.process.repository.ProcessMapper;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.AuthConstants;
import kr.co.bestiansoft.ebillservicekg.process.vo.CmttVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ProcessServiceImpl implements ProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServiceImpl.class);
	private final ProcessMapper processMapper;


	@Transactional
	@Override
	public ProcessVo handleProcess(ProcessVo argVo) {

		String userId = new SecurityInfoUtil().getAccountId();
		ProcessVo stepVo = new ProcessVo();

		if("PC_START".equals(argVo.getStepId())) {// 시작점 bp instance 생성
			// BpDfId 를 접수시 할지? 등록시 할지?
			ProcessVo pVo = new ProcessVo();
			pVo.setBillId(argVo.getBillId());
			pVo.setBpDfId("1");//일단은 하나로 설정 여러개라면 안건유형에 따라서
			pVo.setStatus("P");//진행중
			pVo.setCurrentStepId("0");
			pVo.setRegId(argVo.getRegId());
			processMapper.insertBpInstance(pVo);

			stepVo.setStepId("0");
			stepVo.setBillId(argVo.getBillId());
			stepVo.setNextStepId("0");
		} else {
			stepVo = processMapper.selectBpStep(argVo);
		}


		if(argVo.getTaskId() != null && !"".equals(argVo.getTaskId())) {//taskId 가 존재시 타스크만 완료업뎃

			//task 완료처리
			ProcessVo cpltVo = new ProcessVo();
			cpltVo.setTaskId(argVo.getTaskId());
			cpltVo.setTaskStatus("C");
			cpltVo.setMdfrId(userId);
			processMapper.updateBpTask(cpltVo);
		}

		stepVo.setRegId(userId);
		stepVo.setMdfrId(userId);
		executeServiceTasks(stepVo);

		return argVo;

	}

	public void executeServiceTasks(ProcessVo argVo)  {

		String stepId = argVo.getStepId();
		String nextStepId = argVo.getNextStepId();

		if(stepId == null || "".equals(stepId)) return;

		switch (nextStepId) {
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

	        case "1500":
	        	executeService_1500(argVo);
	            break;

	        case "1600":
	        	executeService_1600(argVo);
	            break;

	        case "1700":
	        	executeService_1700(argVo);
	            break;

	        case "1800":
	        	executeService_1800(argVo);
	            break;

	        case "1900":
	        	executeService_1900(argVo);
	            break;

	        case "2000":
	        	executeService_2000(argVo);
	            break;

	        case "2100":
	        	executeService_2100(argVo);
	            break;

	        case "2200":
	        	executeService_2200(argVo);
	            break;

	        case "2300":
	        	executeService_2300(argVo);
	            break;

	        case "2400":
	        	executeService_2400(argVo);
	            break;

	        case "2500":
	        	executeService_2500(argVo);
	            break;

	        case "2600":
	        	executeService_2600(argVo);
	            break;

	        case "2700":
	        	executeService_2700(argVo);
	            break;

	        case "2800":
	        	executeService_2800(argVo);
	            break;

	        case "2900":
	        	executeService_2900(argVo);
	            break;

	        case "3000":
	        	executeService_3000(argVo);
	            break;

	        case "3100":
	        	executeService_3100(argVo);
	            break;

	        case "3200":
	        	executeService_3200(argVo);
	            break;

	        case "3300":
	        	executeService_3300(argVo);
	            break;

	        case "9999"://종단점
	        	executeService_9999(argVo);
	            break;

	        default:
	            System.out.println("Unknown stepId.");
	            break;

		}

		/*현재의 스텝아이디 변경*/
		processMapper.updateBpInstanceCurrentStep(argVo);
    }


		/*안건등록 시작점 프로세스시작*/
		void executeService_0(ProcessVo argVo)  {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setTaskNm("안건등록");
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setStatus("C");
			taskVo.setTrgtUserId(argVo.getRegId());
			taskVo.setRegId(argVo.getRegId());

			processMapper.insertBpTask(taskVo);
		}


		/*안건접수관리*/
		void executeService_1000(ProcessVo argVo) {

			// BpDfId 를 접수시 할지? 등록시 할지?
			//argVo.setBpDfId("1");//일단은 하나로 설정 여러개라면 안건유형에 따라서
			//processMapper.updateBpInstance(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setTaskNm("안건접수");
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_GD);//GD부서에 할당
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

		}

		/*안건철회관리*/
		void executeService_1100(ProcessVo argVo) {

			String userId = new SecurityInfoUtil().getAccountId();
			List<ProposerVo> properList = processMapper.selectListProposerId(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setTaskNm("안건철회");
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setRegId(argVo.getRegId());
			for(ProposerVo ppVo:properList) {

				if(userId.equals(ppVo.getProposerId())) {//로그인한자가 제안자이면 이미완료.
					taskVo.setStatus("C");
				} else {
					taskVo.setStatus("P");
				}
				taskVo.setTrgtUserId(ppVo.getProposerId());//해당국회의원 할당 공동발의자 들에게
				processMapper.insertBpTask(taskVo);
			}
		}

		/*법률부서검토관리
		 * GD가 안건접수시 법류부서검토로*/
		void executeService_1200(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setTaskNm("법률부서검토");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_LGRV);//법률부서에 할당
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

		}

		/*안건접수(위원회작성)*/
		void executeService_1300(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setTaskNm("위원회회부");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_GD);//GD 할당
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

		}

		/*1차위원회 회의예정*/
		void executeService_1400(ProcessVo argVo) {

//			언어전문파트 의견서등록
//			심사부서 의견서등록
//			회의예정화면에서 회의안건으로 선택하여	회의를 저장한다.

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setTaskNm("언어전문파트의견서등록");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_LGGSPLZ);//언어전문파트 할당
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

			taskVo.setTaskNm("심사부서의견서등록");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_LGEXNTN);//심사부서 할당
			processMapper.insertBpTask(taskVo);

		}

		/*1차위원회 회의결과등록*/
		void executeService_1500(ProcessVo argVo) {
	        //회의결과화면에서 결과정보를 등록한다.
			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setTaskNm("1차위원회 회의결과등록");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(cmttVo.getCmtId());//위원회 할당
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

		}

		/*1차위원회 회의심사보고*/
		void executeService_1600(ProcessVo argVo) {

//			위원회권한의 사람이 의안상세화면에서
//			심사보고서를 등록한다.
			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setTaskNm("1차위원회 회의심사보고");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(cmttVo.getCmtId());//위원회 할당
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

		}

		/*1차본회의 심사요청*/
		void executeService_1700(ProcessVo argVo) {

			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setTaskNm("1차본회의 심사요청");
			taskVo.setStatus("C");
			taskVo.setAssignedTo(cmttVo.getCmtId());//위원회 할당
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

		}

		/*1차본회의 심사*/
		void executeService_1800(ProcessVo argVo) {

			CmttVo cmttVo = processMapper.selectOneCmtt(argVo);

//			본회의결과정보(가결/부결등) 등록
//			심사보고서등록
			ProcessVo taskVo = new ProcessVo();
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setTaskNm("본회의 심사요청");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(cmttVo.getCmtId());//위원회 할당
			taskVo.setRegId(argVo.getRegId());
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
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getNextStepId());
			taskVo.setTaskNm("법적행위 검토 보고서");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_26);//법적행위 할당
			taskVo.setRegId(argVo.getRegId());
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
			taskVo.setAssignedTo(cmttVo.getCmtId());//위원회 할당
			processMapper.insertBpTask(taskVo);

			taskVo.setTaskNm("의장검토");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_CMOFFC);//의장 할당
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
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("정부이송 요청");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_GD);//일반부서(GD)
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

		}

		/*공포*/
		void executeService_3300(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setTaskNm("공포");
			taskVo.setStatus("P");
			taskVo.setAssignedTo(AuthConstants.AUTH_GD);//일반부서(GD)
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);

		}

		/*종단점*/
		void executeService_9999(ProcessVo argVo) {

			ProcessVo taskVo = new ProcessVo();
			taskVo.setTaskNm(argVo.getStepNm());
			taskVo.setBillId(argVo.getBillId());
			taskVo.setStepId(argVo.getStepId());
			taskVo.setStatus("C");
			taskVo.setRegId(argVo.getRegId());
			processMapper.insertBpTask(taskVo);
		}


		@Override
		public ProcessVo selectBpTask(ProcessVo argVo) {
			return processMapper.selectBpTask(argVo);
		}

		@Override
		public ProcessVo handleTask(ProcessVo argVo) {
			String userId = new SecurityInfoUtil().getAccountId();
			argVo.setMdfrId(userId);
			processMapper.updateBpTask(argVo);
			return argVo;
		}



}