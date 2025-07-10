package kr.co.bestiansoft.ebillservicekg.admin.dept.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.repository.CcofMapper;
import kr.co.bestiansoft.ebillservicekg.admin.dept.domain.DeptHierarchy;
import kr.co.bestiansoft.ebillservicekg.admin.dept.repository.DeptMapper;
import kr.co.bestiansoft.ebillservicekg.admin.dept.service.DeptService;
import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.repository.UserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class DeptServiceImpl implements DeptService {
    private final DeptMapper deptMapper;
    private final CcofMapper ccofMapper;

    /**
     * Retrieves a list of departments with the provided parameters.
     * The 'useYn' parameter is automatically set to "Y" within the method
     * to only include active departments.
     *
     * @param param a HashMap containing the query parameters for the department list.
     *              The method modifies this parameter by adding "useYn" set to "Y".
     * @return a list of {@code DeptVo} objects representing the departments that match the query parameters.
     */
    @Override
    public List<DeptVo> getComDeptList(HashMap<String, Object> param) {
        param.put("useYn", "Y");
        return deptMapper.selectListDept(param);
    }

    /**
     * Constructs a hierarchical tree structure of departments based on the provided parameters.
     * Retrieves a list of department data from the database, builds a hierarchical structure
     * using the {@link DeptHierarchy} class, and converts it into a JSON-compatible format.
     *
     * @param param a {@code HashMap} containing query parameters for retrieving the department list.
     *              The key "searchYn*/
    @Override
    public ArrayNode getDeptTree(HashMap<String, Object> param) {
        boolean search = Boolean.parseBoolean((String)param.get("searchYn"));

        List<DeptVo> deptVos = deptMapper.selectListDept(param);
        DeptHierarchy dh = new DeptHierarchy();

        dh.buildHierarchy(deptVos, search);

        ArrayNode arrayNode = dh.getJson();
        return arrayNode;
    }

    /**
     * Retrieves a list of committees based on the provided parameters.
     *
     * @param param a HashMap containing query parameters for retrieving the committee list.
     *              The keys and values in the map are used to filter the results.
     * @return a list of {@code DeptVo} objects representing the committees that match the specified criteria.
     */
    @Override
    public List<DeptVo> getCmitList(HashMap<String, Object> param) {
        return deptMapper.getCmitList(param);
    }

    /**
     * Retrieves a department's information based on the specified department code and language.
     *
     * @param deptCd the unique code identifying the department to retrieve.
     * @param lang the language code used to determine the language-specific details of the department.
     * @return a {@code DeptVo} object containing the details of the specified department.
     */
    @Override
    public DeptVo getComDeptById(String deptCd, String lang) {
        return deptMapper.selectDept(deptCd, lang);
    }

    /**
     * Creates a new department by setting the registration ID and inserting the department
     * data into the database.
     *
     * @param deptVo the {@code DeptVo} object containing department details to be created.
     *               The method sets the registration ID before inserting the department.
     * @return the updated {@code DeptVo} object after creation with the registration ID set.
     */
    @Override
    public DeptVo createDept(DeptVo deptVo) {
        deptVo.setRegId(new SecurityInfoUtil().getAccountId());
        deptMapper.insertDept(deptVo);
        return deptVo;
    }

    /**
     * Persists user and department associations in the database based on the provided parameters.
     * The method iteratively combines user IDs and department codes to create multiple records and assigns
     * an ordinal value for each association. Each record is then inserted into the database.
     *
     * @param params a HashMap containing the input parameters:
     *               - "userList": a list of user IDs to associate with departments.
     *               - "deptList": a list of department codes to associate with users.
     * @return a {@code DeptVo} object. Currently, this method returns null as part of its execution.
     */
    @Override
    public DeptVo saveUsersCcofs(HashMap<String, Object> params) {
        List<String> userIds = (List<String>) params.get("userList");
        List<String> deptCds = (List<String>) params.get("deptList");


        int ord = 1;
        for(String userId : userIds) {
            for (String deptCd : deptCds) {
                UserVo userVo = new UserVo();
                userVo.setDeptCd(deptCd);
                userVo.setUserId(userId);
                userVo.setOrd(ord);

                ccofMapper.insertCcofInUser(userVo);
                ord++;
            }
        }

        return null;
    }

    /**
     * Updates the details of a department in the database.
     * The method modifies the {@code modId} property of the {@code DeptVo} object
     * to the current user's account ID before performing the update operation.
     *
     * @param deptVo the {@code DeptVo} object containing the department details to be updated.
     *               The {@code modId} field will be automatically set by this method.
     * @return an integer representing the result of the update operation, typically the number of rows affected.
     */
    @Override
    public int updateDept(DeptVo deptVo) {
        deptVo.setModId(new SecurityInfoUtil().getAccountId());
        return deptMapper.updateDept(deptVo);
    }

    /**
     * Deletes departments based on the provided list of department codes.
     * Iterates through the list of department codes and deletes each department
     * by invoking the corresponding method in the data mapper.
     *
     * @param deptCd a list of department codes to be deleted. Each code corresponds
     *               to a department that will be removed from the database.
     */
    @Override
    public void deleteDept(List<String> deptCd) {
        for (String cd : deptCd)
            deptMapper.deleteDept(cd);
    }

    /**
     * Deletes committee associations for a given department code and a list of user IDs.
     * Iterates through the list of user IDs and deletes the respective committee associations
     * for each user in the specified department by invoking the associated data handler method.
     *
     * @param deptCd the unique code identifying the department to remove committee associations from.
     * @param userIds a list of user IDs whose committee associations in the specified department
     *                are to be deleted.
     */
    @Override
    public void deleteCmit(String deptCd, List<String> userIds) {
        for(String userId : userIds) {
            ccofMapper.deleteCmit(deptCd, userId);
//                userMapper.updateCmit(userId);
        }
    }
}
