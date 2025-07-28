package kr.co.bestiansoft.ebillservicekg.admin.authDept.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.authDept.repository.AuthDeptMapper;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.service.AuthDeptService;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthDeptServiceImpl implements AuthDeptService {

    private final AuthDeptMapper authDeptMapper;

    /**
     * Retrieves a list of authorized departments based on the given department code.
     *
     * @param deptCd the code of the department for which the authorized departments need to be retrieved
     * @return a list of AuthDeptVo objects representing the authorized departments
     */
    @Override
    public List<AuthDeptVo> getAuthDeptList(String deptCd) {
        return authDeptMapper.selectListAuthDept(deptCd);
    }

    /**
     * Creates or updates the authorization information for a department.
     * Deletes all existing authorization information for the specified department
     * and inserts the provided authorization details.
     *
     * @param authDeptCreate the object containing the department code and a list of
     *                       authorization details to be created or updated
     * @return the same AuthDeptCreate object passed as input, representing the updated
     *         authorization details
     */
    @Override
    public AuthDeptCreate createAuthDept(AuthDeptCreate authDeptCreate) {
        List<AuthDeptVo> authDeptVos = authDeptCreate.getAuthDeptVos();
        String deptCd = authDeptCreate.getDeptCd();

        authDeptMapper.deleteAuthDept(deptCd);

        for(AuthDeptVo vo : authDeptVos) {
            vo.setRegId(new SecurityInfoUtil().getAccountId());
            authDeptMapper.insertAuthDept(vo);
        }

        return authDeptCreate;
    }
}
