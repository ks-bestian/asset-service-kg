package kr.co.bestiansoft.ebillservicekg.form.service;

import kr.co.bestiansoft.ebillservicekg.form.vo.FormVo;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;

import java.util.HashMap;
import java.util.List;

public interface FormService {
    List<FormVo> getFormList(HashMap<String , Object> param);

    FormWithFieldsVo createFormWithFields(FormWithFieldsVo formWithFieldsVo);
    FormWithFieldsVo getFormWithFieldsById(Integer formId);
}
