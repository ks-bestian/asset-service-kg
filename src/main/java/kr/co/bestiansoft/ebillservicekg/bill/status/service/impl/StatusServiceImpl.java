package kr.co.bestiansoft.ebillservicekg.bill.status.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.bill.status.repository.StatusMapper;
import kr.co.bestiansoft.ebillservicekg.bill.status.service.StatusService;
import kr.co.bestiansoft.ebillservicekg.bill.status.vo.StatusVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class StatusServiceImpl implements StatusService {

	private final StatusMapper statusMapper;

	/**
	 * Retrieves a list of meetings based on the provided parameters.
	 *
	 * @param param a HashMap containing the parameters to filter the meeting list.
	 *              The specific keys and values required depend on the implementation.
	 * @return a List of StatusVo objects representing the meeting details.
	 */
	@Override
	public List<StatusVo> getMtngList(HashMap<String, Object> param) {
		return statusMapper.getMtngList(param);
	}

	/**
	 * Retrieves a list of monitoring data based on the provided parameters.
	 *
	 * @param param a HashMap containing the parameters to filter the monitoring data.
	 *              The specific keys and values required depend on the implementation.
	 * @return a List of StatusVo objects representing the monitoring data details.
	 */
	@Override
	public List<StatusVo> getMonitorList(HashMap<String, Object> param) {
		return statusMapper.getMonitorList(param);
	}

}