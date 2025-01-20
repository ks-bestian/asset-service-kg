package kr.co.bestiansoft.ebillservicekg.admin.dept.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import kr.co.bestiansoft.ebillservicekg.admin.dept.domain.DeptHierarchy;
import kr.co.bestiansoft.ebillservicekg.admin.dept.repository.DeptMapper;
import kr.co.bestiansoft.ebillservicekg.admin.dept.service.DeptService;
import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class DeptServiceImpl implements DeptService {
    private final DeptMapper deptMapper;

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
    public DeptVo getComDeptById(String deptCd) {
        return deptMapper.selectDept(deptCd);
    }


    @Override
    public DeptVo createDept(DeptVo deptVo) {
        deptVo.setRegId(new SecurityInfoUtil().getAccountId());
        deptMapper.insertDept(deptVo);
        return deptVo;
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
}
