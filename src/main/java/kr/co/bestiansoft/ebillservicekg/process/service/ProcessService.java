package kr.co.bestiansoft.ebillservicekg.process.service;

import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;

public interface ProcessService {


	ProcessVo handleProcess(ProcessVo argVo);


	ProcessVo selectBpTask(ProcessVo argVo);


}

