package kr.co.bestiansoft.ebillservicekg.admin.comCode.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.comCode.repository.ComCodeMapper;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.service.ComCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ComCodeServiceImpl implements ComCodeService {

    private final ComCodeMapper comCodeMapper;

    /**
     * Retrieves a list of group codes based on the provided parameters.
     *
     * @param param a HashMap containing the filter criteria and other parameters
     *              required for retrieving the list of group codes.
     * @return a list of ComCodeVo objects representing the group codes
     *         that match the specified criteria.
     */
    @Override
    public List<ComCodeVo> getGrpCodeList(HashMap<String, Object> param) {
        return comCodeMapper.selectListGrpCode(param);
    }

    /**
     * Retrieves a list of common codes based on the provided parameters.
     *
     * @param param a HashMap containing the criteria and parameters needed
     *              to filter and retrieve the list of common codes.
     * @return a List of ComCodeVo objects representing the common codes
     *         that match the provided parameters.
     */
    @Override
    public List<ComCodeVo> getCodeList(HashMap<String, Object> param) {
        return comCodeMapper.selectListComCode(param);
    }

    /**
     * Retrieves a group code based on the provided group code identifier.
     *
     * @param grpCode the unique identifier of the group code to be retrieved
     * @return a ComCodeVo object containing the details of the group code
     *         corresponding to the specified identifier, or null if no matching
     *         record is found
     */
    @Override
    public ComCodeVo getGrpCodeById(Integer grpCode) {
        return comCodeMapper.selectGrpCode(grpCode);
    }

    /**
     * Retrieves the details of a common code based on the provided code identifier.
     *
     * @param codeId the unique identifier of the common code to be retrieved
     * @return a ComCodeDetailVo object containing detailed information about
     *         the common code corresponding to the specified identifier,
     *         or null if no matching record is found
     */
    @Override
    public ComCodeDetailVo getComCodeById(String codeId) {
        return comCodeMapper.selectComCode(codeId);
    }

    /**
     * Creates a new group code by setting the registrant ID and inserting the
     * group code information into the database.
     *
     * @param comCodeVo a ComCodeVo object containing the details of the group code
     *                  to be created
     * @return the updated ComCodeVo object after insertion, containing the
     *         registrant ID and other group code details
     */
    @Override
    public ComCodeVo createGrpCode(ComCodeVo comCodeVo) {
        comCodeVo.setRegId(new SecurityInfoUtil().getAccountId());
        comCodeMapper.insertGrpCode(comCodeVo);
        return comCodeVo;
    }

    /**
     * Creates a new common code by setting the registrant ID and inserting
     * the common code details into the database.
     *
     * @param comCodeDetailVo a ComCodeDetailVo object containing the details
     *                        of the common code to be created
     * @return the updated ComCodeDetailVo object after insertion, including
     *         the registrant ID
     */
    @Override
    public ComCodeDetailVo createComCode(ComCodeDetailVo comCodeDetailVo) {
        comCodeDetailVo.setRegId(new SecurityInfoUtil().getAccountId());
        comCodeMapper.insertComCode(comCodeDetailVo);
        return comCodeDetailVo;
    }

    /**
     * Updates an existing group code in the system by setting the modifier ID
     * and delegating the update operation to the data mapper.
     *
     * @param comCodeVo a ComCodeVo object containing the updated details
     *                  of the group code, including its identifier and new values
     * @return an integer indicating the number of records affected by the update operation
     */
    @Override
    public int updateGrpCode(ComCodeVo comCodeVo) {
        comCodeVo.setModId(new SecurityInfoUtil().getAccountId());
        return comCodeMapper.updateGrpCode(comCodeVo);
    }

    /**
     * Updates an existing common code in the system by setting the modifier ID
     * and performing the update operation via the data mapper.
     *
     * @param comCodeDetailVo a ComCodeDetailVo object containing the updated details
     *                        of the common code, including its identifier and new values
     * @return an integer indicating the number of records affected by the update operation
     */
    @Override
    public int updateComCode(ComCodeDetailVo comCodeDetailVo) {
        comCodeDetailVo.setModId(new SecurityInfoUtil().getAccountId());
        return comCodeMapper.updateComCode(comCodeDetailVo);
    }

    /**
     * Deletes a list of group codes from the database.
     *
     * @param grpCodes a list of group code identifiers (Long) to be deleted
     */
    @Override
    public void deleteGrpCode(List<Long> grpCodes) {
        for(Long grpCode : grpCodes) {
            comCodeMapper.deleteGrpCode(grpCode);
        }
    }

    /**
     * Deletes a list of common codes associated with a specific group code.
     *
     * @param codeIds a list of common code identifiers (String) to be deleted
     * @param grpCode the unique identifier of the group code associated with the common codes to be deleted
     */
    @Override
    public void deleteComCode(List<String> codeIds, int grpCode) {
        for(String codeId : codeIds) {
            comCodeMapper.deleteComCode(codeId, grpCode);
        }
    }
}
