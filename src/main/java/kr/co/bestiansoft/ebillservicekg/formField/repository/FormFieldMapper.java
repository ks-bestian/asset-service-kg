package kr.co.bestiansoft.ebillservicekg.formField.repository;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;

@Mapper
public interface FormFieldMapper {
    int insertFormField(FormFieldVo formFieldVo);
    List<FormFieldVo> selectFormFieldListByFormId(Integer formId);
    void deleteFormFields(Integer formId);
}
