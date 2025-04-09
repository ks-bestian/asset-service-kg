package kr.co.bestiansoft.ebillservicekg.form.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.form.repository.FormMapper;
import kr.co.bestiansoft.ebillservicekg.form.service.FormService;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormVo;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;
import kr.co.bestiansoft.ebillservicekg.formField.service.FormFieldService;
import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final FormMapper formMapper;
    private final FormFieldService formFieldService;

    @Override
    public List<FormVo> getFormList() {
        List<FormVo> result = formMapper.selectFormList();
        return result;
    }

    @Override
    public FormWithFieldsVo createFormWithFields(FormWithFieldsVo formWithFieldsVo) {

        formWithFieldsVo.setRegId(new SecurityInfoUtil().getAccountId());
        formMapper.insertForm(formWithFieldsVo);
        int formId = formWithFieldsVo.getFormId();

        //insert fields
        if(formWithFieldsVo.getFields() != null) {
            for(FormFieldVo field : formWithFieldsVo.getFields()) {
                field.setFormId(formId);
                formFieldService.createFormField(field);
            }
        }
        return formWithFieldsVo;
    }

    @Override
    public FormWithFieldsVo getFormWithFieldsById(Integer formId) {
        FormWithFieldsVo formWithFieldsVo = formMapper.selectFormDetail(formId);
        List<FormFieldVo> fields = formFieldService.getFormFieldByFormId(formId);
        if (!fields.isEmpty()) {
            formWithFieldsVo.setFields(fields);
        }
        return formWithFieldsVo;
    }

    @Override
    public void deleteFormWithFields(List<Integer> formIds) {
        for(Integer formId: formIds) {
            formFieldService.deleteFormFields(formId);
            formMapper.deleteForm(formId);
        }
    }
}

