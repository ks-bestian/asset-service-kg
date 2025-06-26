package kr.co.bestiansoft.ebillservicekg.formField.service.impl;

import kr.co.bestiansoft.ebillservicekg.formField.repository.FormFieldMapper;
import kr.co.bestiansoft.ebillservicekg.formField.service.FormFieldService;
import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormFieldServiceImpl implements FormFieldService {

    private final FormFieldMapper formFieldMapper;

    @Override
    public int createFormField(FormFieldVo formFieldVo) {
        return formFieldMapper.insertFormField(formFieldVo);

    }

    @Override
    public void deleteFormFields(Integer formId) {
        formFieldMapper.deleteFormFields(formId);
    }


    @Override
    public List<FormFieldVo> getFormFieldByFormId(Integer formId) {
        return formFieldMapper.selectFormFieldListByFormId(formId);
    }


}
