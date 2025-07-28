package kr.co.bestiansoft.ebillservicekg.formField.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;


public interface FormFieldService {
    List<FormFieldVo> getFormFieldByFormId(Integer formId);
    int createFormField(FormFieldVo formFieldVo);

    void deleteFormFields(Integer formId);

}

