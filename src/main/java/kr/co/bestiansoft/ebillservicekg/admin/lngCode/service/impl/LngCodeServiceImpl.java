package kr.co.bestiansoft.ebillservicekg.admin.lngCode.service.impl;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.lngCode.repository.LngCodeMapper;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.service.LngCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.lngCode.vo.LngCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class LngCodeServiceImpl implements LngCodeService {
    private final LngCodeMapper lngCodeMapper;

    /**
     * Retrieves a list of language codes based on the specified parameters.
     *
     * @param param a HashMap containing the parameters for filtering the language codes
     * @return a List of LngCodeVo objects that match the specified parameters
     */
    @Override
    public List<LngCodeVo> getLngCodeList(HashMap<String, Object> param) {
        return lngCodeMapper.selectListLngCode(param);
    }

    /**
     * Creates a new language code record and saves it to the database.
     *
     * @param lngCodeVo an instance of LngCodeVo containing the language code details to be created
     * @return the created LngCodeVo object with the registration ID set
     */
    @Override
    public LngCodeVo createLngCode(LngCodeVo lngCodeVo) {
        lngCodeVo.setRegId(new SecurityInfoUtil().getAccountId());
        lngCodeMapper.insertLngCode(lngCodeVo);

        return lngCodeVo;
    }

    /**
     * Updates an existing language code record with modified details.
     * The modification ID is set based on the current authenticated user.
     *
     * @param lngCodeVo an instance of LngCodeVo containing the updated details of the language code
     * @return the number of rows affected by the update operation
     */
    @Override
    public int updateLngCode(LngCodeVo lngCodeVo) {
        lngCodeVo.setModId(new SecurityInfoUtil().getAccountId());
        return lngCodeMapper.updateLngCode(lngCodeVo);
    }

    /**
     * Deletes language codes from the database for the given list of IDs.
     *
     * @param lngId a list of IDs representing the language codes to be deleted
     */
    @Override
    public void deleteLngCode(List<Long> lngId) {
        for (Long id : lngId) {
            lngCodeMapper.deleteLngCode(id);
        }
    }

    /**
     * Retrieves a specific language code record based on its unique identifier.
     *
     * @param lngId the unique identifier of the language code to be retrieved
     * @return an instance of LngCodeVo corresponding to the given identifier,
     *         or null if no matching record is found
     */
    @Override
    public LngCodeVo getLngCodeById(Long lngId) {
        return lngCodeMapper.selectLngCode(lngId);
    }
}
