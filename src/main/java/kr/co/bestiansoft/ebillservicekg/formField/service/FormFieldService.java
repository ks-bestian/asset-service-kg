package kr.co.bestiansoft.ebillservicekg.formField.service;

import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;


import java.util.List;


public interface FormFieldService {
    List<FormFieldVo> getFormFieldByFormId(Integer formId);
    int createFormField(FormFieldVo formFieldVo);

    void deleteFormFields(Integer formId);
}

