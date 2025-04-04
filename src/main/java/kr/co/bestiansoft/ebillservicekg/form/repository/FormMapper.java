package kr.co.bestiansoft.ebillservicekg.form.repository;

import kr.co.bestiansoft.ebillservicekg.form.vo.FormVo;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface FormMapper {
    List<FormVo> selectFormList(HashMap<String, Object> param);
    void insertForm(FormWithFieldsVo formWithFieldsVo);
    FormWithFieldsVo selectFormDetail(Integer formId);
}

