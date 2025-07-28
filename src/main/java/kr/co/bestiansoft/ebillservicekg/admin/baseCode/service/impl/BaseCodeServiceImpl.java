package kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.baseCode.repository.BaseCodeMapper;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.BaseCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class BaseCodeServiceImpl implements BaseCodeService {

    private final BaseCodeMapper baseCodeMapper;

    /**
     * Retrieves a list of BaseCodeVo objects based on the provided parameters.
     *
     * @param param a HashMap containing the criteria and values for filtering
     *              the base codes.
     * @return a List of BaseCodeVo objects that meet the criteria specified
     *         in the param.
     */
    @Override
    public List<BaseCodeVo> getBaseCodeList(HashMap<String, Object> param) {
        return baseCodeMapper.selectListBaseCode(param);
    }

    /**
     * Creates a new base code entry by setting the registration ID and inserting the
     * base code data into the database.
     *
     * @param baseCodeVo the BaseCodeVo object containing the base code information to be created
     * @return the BaseCodeVo object after it has been created and inserted into the database
     */
    @Override
    public BaseCodeVo createBaseCode(BaseCodeVo baseCodeVo) {
        baseCodeVo.setRegId(new SecurityInfoUtil().getAccountId());
    	baseCodeMapper.insertBaseCode(baseCodeVo);
        return baseCodeVo;
    }

    /**
     * Updates an existing base code entry by setting the modifier ID and passing the updated data
     * to the appropriate data mapper for persistence.
     *
     * @param baseCodeVo the BaseCodeVo object containing the updated base code information
     *                   to be persisted in the database
     * @return an integer representing the number of rows affected by the update operation
     */
    @Override
    public int updateBaseCode(BaseCodeVo baseCodeVo) {
        baseCodeVo.setModId(new SecurityInfoUtil().getAccountId());
        return baseCodeMapper.updateBaseCode(baseCodeVo);
    }

    /**
     * Deletes base codes corresponding to the provided list of IDs.
     *
     * @param ids a list of base code IDs to be deleted from the database
     */
    @Override
    public void deleteBaseCode(List<String> ids) {
        for (String id : ids) {
            baseCodeMapper.deleteBaseCode(id);
        }
    }

    /**
     * Retrieves a BaseCodeVo object based on the provided base code identifier.
     *
     * @param baseCode the unique identifier of the base code to be retrieved
     * @return the BaseCodeVo object corresponding to the given base code identifier,
     *         or null if no matching record is found
     */
    @Override
    public BaseCodeVo getBaseCodeById(Long baseCode) {
        return baseCodeMapper.selectBaseCode(baseCode);
    }
}
