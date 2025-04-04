package kr.co.bestiansoft.ebillservicekg.formField.repository;
import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FormFieldMapper {
    void insertFormField(FormFieldVo formFieldVo);
    List<FormFieldVo> selectFormFieldListByFormId(Integer formId);
}
