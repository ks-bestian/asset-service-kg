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


	private final ProcessHandler processHandler;

	@Transactional
	@Override
	public ProcessVo testProcess(ProcessVo argVo) throws Exception {

//		String billId = "EB_cc4aeeb5-bad9-4b81-aa4a-effd2916ffb5";
//		ProcessVo processVo = new ProcessVo();
//		processVo.setBillId(billId);
//		processVo.setStepId("0");

		ProcessVo returnVo = processHandler.createProcess(argVo);
		return returnVo;
	}



}