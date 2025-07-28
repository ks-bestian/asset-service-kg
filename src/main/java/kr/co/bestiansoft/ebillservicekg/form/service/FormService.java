package kr.co.bestiansoft.ebillservicekg.form.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.form.vo.FormVo;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;


public interface FormService {
    List<FormVo> getFormList();
    FormWithFieldsVo createFormWithFields(FormWithFieldsVo formWithFieldsVo);
    FormWithFieldsVo getFormWithFieldsById(Integer formId);

    void deleteFormWithFields(Integer formId);
}
