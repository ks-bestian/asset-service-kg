package kr.co.bestiansoft.ebillservicekg.form.service.impl;

import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.form.repository.FormMapper;
import kr.co.bestiansoft.ebillservicekg.form.service.FormService;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormVo;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;
import kr.co.bestiansoft.ebillservicekg.formField.service.FormFieldService;
import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final FormMapper formMapper;

    private final FormFieldService formFieldService;
    private final ComFileService comFileService;

    @Autowired
    private final EDVHelper edv;

    @Override
    public List<FormVo> getFormList() {
        return formMapper.selectFormList();
    }

    @Override
    public FormWithFieldsVo createFormWithFields(FormWithFieldsVo formWithFieldsVo) {

        formWithFieldsVo.setRegId(new SecurityInfoUtil().getAccountId());
        formMapper.insertForm(formWithFieldsVo);
        int formId = formWithFieldsVo.getFormId();

        //insert fields
        List<FormFieldVo> fields = formWithFieldsVo.getFields();
        if(fields != null && !fields.isEmpty()) {
            for(int i = 0; i < fields.size(); i++) {
                FormFieldVo field = fields.get(i);
                field.setFormId(formId);
                field.setFieldSeq(i+1);
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
    public void deleteFormWithFields(Integer formId) {
        formFieldService.deleteFormFields(formId);

        FormWithFieldsVo formWithFieldsVo = formMapper.selectFormById(formId);
        String fileId = formWithFieldsVo.getFileId();

        if (!fileId.isEmpty()) {
            comFileService.deleteFile(fileId);
        }
        formMapper.deleteForm(formId);
        }
}
