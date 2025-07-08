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

    /**
     * Retrieves the list of forms from the database.
     *
     * @return a list of FormVo objects representing the forms available.
     */
    @Override
    public List<FormVo> getFormList() {
        return formMapper.selectFormList();
    }

    /**
     * Creates a form along with its associated fields and saves them to the database.
     *
     * @param formWithFieldsVo the form object containing the form information and associated fields
     * @return the form object with updated information, including the newly assigned form ID and field sequence numbers
     */
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

    /**
     * Retrieves form details, including associated fields, by form ID.
     *
     * @param formId the unique identifier of the form to retrieve
     * @return a FormWithFieldsVo object containing form details and a list of associated fields
     */
    @Override
    public FormWithFieldsVo getFormWithFieldsById(Integer formId) {
        FormWithFieldsVo formWithFieldsVo = formMapper.selectFormDetail(formId);
        List<FormFieldVo> fields = formFieldService.getFormFieldByFormId(formId);
        if (!fields.isEmpty()) {
            formWithFieldsVo.setFields(fields);
        }
        return formWithFieldsVo;
    }

    /**
     * Deletes a form along with all its associated fields and the corresponding file if it exists.
     *
     * This method performs the following steps:
     * 1. Deletes all fields associated with the form identified by its ID.
     * 2. Retrieves the form details to check for any associated file.
     * 3. Deletes the associated file if it is present.
     * 4. Deletes the form entry from the database.
     *
     * @param formId the unique identifier of the form to be deleted
     */
    @Override
    public void deleteFormWithFields(Integer formId) {
        formFieldService.deleteFormFields(formId);

        FormWithFieldsVo formWithFieldsVo = formMapper.selectFormById(formId);
        String fileId = formWithFieldsVo.getFileId();

        if (fileId != null) {
            comFileService.deleteFile(fileId);
        }
        formMapper.deleteForm(formId);
        }
}
