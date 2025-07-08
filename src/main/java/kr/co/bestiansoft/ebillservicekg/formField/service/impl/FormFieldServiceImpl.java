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

    /**
     * Creates a new form field record in the database.
     *
     * @param formFieldVo the FormFieldVo object containing the details of the form field to be created
     * @return the number of records affected in the database
     */
    @Override
    public int createFormField(FormFieldVo formFieldVo) {
        return formFieldMapper.insertFormField(formFieldVo);

    }

    /**
     * Deletes all form field records associated with the specified form ID.
     *
     * @param formId the ID of the form whose fields are to be deleted
     */
    @Override
    public void deleteFormFields(Integer formId) {
        formFieldMapper.deleteFormFields(formId);
    }

    /**
     * Retrieves a list of form fields associated with the given form ID.
     *
     * @param formId the ID of the form whose fields are to be fetched
     * @return a list of FormFieldVo objects representing the form fields associated with the given form ID
     */
    @Override
    public List<FormFieldVo> getFormFieldByFormId(Integer formId) {
        return formFieldMapper.selectFormFieldListByFormId(formId);
    }


}
