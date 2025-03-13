package kr.co.bestiansoft.ebillservicekg.process.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.ProposerVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.CmttVo;
import kr.co.bestiansoft.ebillservicekg.process.vo.ProcessVo;

@Mapper
public interface ProcessMapper {

	ProcessVo selectBpTaskInfo(ProcessVo vo);


	List<ProcessVo> selectListBpStep(ProcessVo vo);
	ProcessVo selectBpStep(ProcessVo vo);
	List<ProcessVo> selectBpStepList(ProcessVo vo);

	void insertBpInstance(ProcessVo vo);
	void updateBpInstanceCurrentStep(ProcessVo vo);
	void updateBpInstance(ProcessVo vo);

	void insertBpTask(ProcessVo vo);
	void updateStepTasks(ProcessVo vo);


	CmttVo selectOneCmtt(ProcessVo vo);
	List<CmttVo> selectListBillCmtt(ProcessVo vo);



	void updateBpTask(ProcessVo vo);

	List<ProposerVo> selectListProposerId(ProcessVo vo);

	ProcessVo selectBpTask(ProcessVo vo);
	List<ProcessVo> selectBpStepTasks(ProcessVo vo);

	void deleteBpTasks(ProcessVo vo);
}
