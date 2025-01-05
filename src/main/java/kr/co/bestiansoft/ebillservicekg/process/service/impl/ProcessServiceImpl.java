package kr.co.bestiansoft.ebillservicekg.process.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.process.controller.ProcessHandler;
import kr.co.bestiansoft.ebillservicekg.process.repository.ProcessMapper;
import kr.co.bestiansoft.ebillservicekg.process.service.ProcessService;
import kr.co.bestiansoft.ebillservicekg.process.vo.AuthConstants;
import kr.co.bestiansoft.ebillservicekg.process.vo.CmttVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ProcessServiceImpl implements ProcessService {


	private final ProcessMapper processMapper;
	private final ProcessHandler processHandler;

	@Transactional
	@Override
	public void testProcess() throws Exception {

		String billId = "EB_cc4aeeb5-bad9-4b81-aa4a-effd2916ffb5";
		ProcessVo processVo = new ProcessVo();
		processVo.setBillId(billId);
		processVo.setStepId("0");

		processHandler.createProcess(processVo);

	}

	@Override
	public ProcessVo makeProcessEbs(ProcessVo argVo) {





		ProcessVo pStepVo = null;

		if("0".equals(argVo.getStepId())) {

			pStepVo = new ProcessVo();
			pStepVo.setBillId(argVo.getBillId());
			pStepVo.setBpDfId("1");//일단은 하나로 설정 여러개라면 안건유형에 따라서
			pStepVo.setStatus("P");//진행중
			pStepVo.setCurrentStepId("0");
			processMapper.insertBpInstance(pStepVo);

			List<ProcessVo> steps = processMapper.selectListBpStep(pStepVo);//전체 스텝가져오기
			for(ProcessVo vo: steps) {

				if(vo.getStepId().equals(argVo.getStepId())) {
					pStepVo = vo;
				}
			}

			//executeService(pStepVo);

		} else {

			ProcessVo pInstanceVo = processMapper.selectBpInstance(argVo);//billId 에 해당하는 인스턴스
			pInstanceVo.setStepId(argVo.getStepId());
			List<ProcessVo> steps = processMapper.selectListBpStep(pInstanceVo);//전체 스텝가져오기
			for(ProcessVo vo: steps) {
				if(vo.getStepId().equals(pInstanceVo.getStepId())) {
					pStepVo = vo;
					break;
				}
			}

			pStepVo.setCurrentStepId(pInstanceVo.getStepId());
			processMapper.updateBpInstance(pStepVo);
			//executeService(pStepVo);

		}



		return pStepVo;
	}



}