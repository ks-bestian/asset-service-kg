package kr.co.bestiansoft.ebillservicekg.form.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.form.vo.FormVo;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;

@Mapper
public interface FormMapper {
    //List<FormVo> selectFormList(HashMap<String, Object> param);
    int insertForm(FormWithFieldsVo formWithFieldsVo);
    FormWithFieldsVo selectFormDetail(Integer formId);
    List<FormVo> selectFormList();

    void deleteForm(Integer formId);

    FormWithFieldsVo selectFormById(Integer formId);
}

