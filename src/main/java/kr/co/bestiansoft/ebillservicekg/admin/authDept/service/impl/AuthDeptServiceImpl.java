package kr.co.bestiansoft.ebillservicekg.admin.authDept.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.authDept.repository.AuthDeptMapper;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.service.AuthDeptService;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptCreate;
import kr.co.bestiansoft.ebillservicekg.admin.authDept.vo.AuthDeptVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthDeptServiceImpl implements AuthDeptService {

    private final AuthDeptMapper authDeptMapper;

    @Override
    public List<AuthDeptVo> getAuthDeptList(String deptCd) {
        return authDeptMapper.selectListAuthDept(deptCd);
    }

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
