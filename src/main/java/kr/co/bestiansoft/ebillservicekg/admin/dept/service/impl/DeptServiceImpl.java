package kr.co.bestiansoft.ebillservicekg.admin.dept.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.dept.repository.DeptMapper;
import kr.co.bestiansoft.ebillservicekg.admin.dept.service.DeptService;
import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
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
        return deptMapper.selectListDept(param);
    }

    @Override
    public DeptVo getComDeptById(String deptCd) {
        return deptMapper.selectDept(deptCd);
    }


    @Override
    public DeptVo createDept(DeptVo deptVo) {
        deptMapper.insertDept(deptVo);
        return deptVo;
    }

    @Override
    public int updateDept(DeptVo deptVo) {
        return deptMapper.updateDept(deptVo);
    }

    @Override
    public void deleteDept(String deptCd) {
        deptMapper.deleteDept(deptCd);
    }
}
