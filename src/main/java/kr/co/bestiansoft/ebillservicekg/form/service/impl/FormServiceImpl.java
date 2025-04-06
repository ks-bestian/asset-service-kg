package kr.co.bestiansoft.ebillservicekg.form.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.form.repository.FormMapper;
import kr.co.bestiansoft.ebillservicekg.form.service.FormService;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormVo;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;
import kr.co.bestiansoft.ebillservicekg.formField.repository.FormFieldMapper;
import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final FormMapper formMapper;
    private final FormFieldMapper formFieldMapper;

    public FormServiceImpl(FormMapper formMapper, FormFieldMapper formFieldMapper) {
        this.formMapper = formMapper;
        this.formFieldMapper = formFieldMapper;
    }

    @Override
    public List<FormVo> getFormList(HashMap<String, Object> param) {
        List<FormVo> result = formMapper.selectFormList(param);
        return result;
    }
    @Override
    public FormWithFieldsVo createFormWithFields(FormWithFieldsVo formWithFieldsVo) {

        formWithFieldsVo.setRegId(new SecurityInfoUtil().getAccountId());
        formMapper.insertForm(formWithFieldsVo);

        //insert fields
        if(formWithFieldsVo.getFields() != null) {
            for(FormFieldVo field : formWithFieldsVo.getFields()) {
                field.setFormId(formWithFieldsVo.getFormId());
                formFieldMapper.insertFormField(field);
            }
        }
        return formWithFieldsVo;
    }

    @Override
    public FormWithFieldsVo getFormWithFieldsById(Integer formId) {
        FormWithFieldsVo formWithFieldsVo = formMapper.selectFormDetail(formId);

        List<FormFieldVo> fields = formFieldMapper.selectFormFieldListByFormId(formId);
        if (!fields.isEmpty()) {
            formWithFieldsVo.setFields(fields);
        }
        return formWithFieldsVo;
    }

}
