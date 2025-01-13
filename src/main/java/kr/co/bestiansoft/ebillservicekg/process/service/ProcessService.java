package kr.co.bestiansoft.ebillservicekg.process.service;

import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;

public interface ProcessService {




	ProcessVo testProcess(ProcessVo argVo) throws Exception;
	ProcessVo createProcessAuto(ProcessVo argVo) throws Exception;



	ProcessVo handleProcess(ProcessVo argVo);

}
