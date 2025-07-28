package kr.co.bestiansoft.ebillservicekg.form.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.form.service.FormService;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;
import lombok.RequiredArgsConstructor;

@Tag(name = "Form API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class FormController {

    private final FormService formService;

    @Operation(summary = "Form List check", description = "Form List Inquiry.")
    @GetMapping("/form")
    public ResponseEntity<CommonResponse> getFormList() {
        return new ResponseEntity<>(new CommonResponse(200, "OK", formService.getFormList()), HttpStatus.OK);
    }

    @Operation(summary = "Form Field generation", description = "Form Field Create.")
    @PostMapping(value = "/formWithFields")
    public ResponseEntity<CommonResponse> createFormWithField(@RequestBody FormWithFieldsVo formWithFieldsVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "form and fields created successfully", formService.createFormWithFields(formWithFieldsVo)), HttpStatus.OK);
    }

    @Operation(summary = "Inquiry and field details", description = "Search for the form and field details.")
    @GetMapping("/formWithFields/detail/{formId}")
    public ResponseEntity<CommonResponse> getFormById(@PathVariable String formId) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", formService.getFormWithFieldsById(Integer.valueOf(formId))), HttpStatus.OK);
    }

    @Operation(summary = "Form delete", description = "Form Delete.")
    @DeleteMapping("/form")
    public ResponseEntity<CommonResponse> deleteFormWithFields(@RequestParam String formId) {
        formService.deleteFormWithFields(Integer.valueOf(formId));
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Form deleted successfully"), HttpStatus.OK);
    }

}


