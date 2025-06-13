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
    private final UserMapper userMapper;

    @Override
    public List<DeptVo> getComDeptList(HashMap<String, Object> param) {
        param.put("useYn", "Y");
        return deptMapper.selectListDept(param);
    }

    @Override
    public ArrayNode getDeptTree(HashMap<String, Object> param) {
        boolean search = Boolean.parseBoolean((String)param.get("searchYn"));

        List<DeptVo> deptVos = deptMapper.selectListDept(param);
        DeptHierarchy dh = new DeptHierarchy();

        dh.buildHierarchy(deptVos, search);

        ArrayNode arrayNode = dh.getJson();
        return arrayNode;
    }

    @Override
    public List<DeptVo> getCmitList(HashMap<String, Object> param) {
        return deptMapper.getCmitList(param);
    }

    @Override
    public DeptVo getComDeptById(String deptCd, String lang) {
        return deptMapper.selectDept(deptCd, lang);
    }


    @Override
    public DeptVo createDept(DeptVo deptVo) {
        deptVo.setRegId(new SecurityInfoUtil().getAccountId());
        deptMapper.insertDept(deptVo);
        return deptVo;
    }

    @Override
    public DeptVo saveUsersCcofs(HashMap<String, Object> params) {
        List<String> userIds = (List<String>) params.get("userList");

        int ord = 1;
        for(String userId : userIds) {
            UserVo userVo = new UserVo();
            userVo.setDeptCd((String)params.get("deptCd"));
            userVo.setUserId(userId);
            userVo.setOrd(ord);

            ccofMapper.insertCcofInUser(userVo);
            ord++;
        }

        return null;
    }

    @Override
    public int updateDept(DeptVo deptVo) {
        deptVo.setModId(new SecurityInfoUtil().getAccountId());
        return deptMapper.updateDept(deptVo);
    }

    @Override
    public void deleteDept(List<String> deptCd) {
        for (String cd : deptCd)
            deptMapper.deleteDept(cd);
    }

    @Override
    public void deleteCmit(String deptCd, List<String> userIds) {
        for(String userId : userIds) {
            ccofMapper.deleteCmit(deptCd, userId);
//                userMapper.updateCmit(userId);
        }
    }
}
