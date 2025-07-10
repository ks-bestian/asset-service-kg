package kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.impl;


import kr.co.bestiansoft.ebillservicekg.admin.acsHist.repository.AcsHistMapper;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.AcsHistService;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AcsHistServiceImpl implements AcsHistService {
    private final AcsHistMapper acsHistMapper;

    /**
     * Retrieves a list of access history records based on the provided parameters.
     *
     * @param param a HashMap containing query parameters to filter the access history records.
     *              The keys in the map should correspond to the fields or filters used in the query.
     * @return a list of AcsHistVo objects representing the access history records that match the query parameters.
     */
    @Override
    public List<AcsHistVo> getAcsHistList(HashMap<String, Object> param) {
        return acsHistMapper.getAcsHistList(param);
    }

    /**
     * Retrieves a list of billing history records based on the provided parameters.
     *
     * @param param a HashMap containing query parameters to filter the billing history records.
     *              The keys in the map should correspond to the fields or filters used in the query.
     * @return a*/
    @Override
    public List<AcsHistVo> getBillHistList(HashMap<String, Object> param) {
        return acsHistMapper.getBillHistList(param);
    }

    /**
     * Creates a new access history record.
     *
     * @param acsHistVo an instance of AcsHistVo containing the details of
     *                  the access history record to be created. This includes
     *                  information such as IP address, requested URL, request
     *                  method, and the name of the member making the request.
     */
    @Override
    public void createAcsHist(AcsHistVo acsHistVo) {
        acsHistMapper.createAcsHist(acsHistVo);
    }

    /**
     * Creates a new billing history record.
     *
     * @param acsHistVo an instance of AcsHistVo containing the details of
     *                  the billing history record to be created. This includes
     *                  information such as IP address, requested URL, request
     *                  method, and the name of the member initiating the billing-related action.
     */
    @Override
    public void createBillHist(AcsHistVo acsHistVo) {
        acsHistMapper.createBillHist(acsHistVo);
    }
}
