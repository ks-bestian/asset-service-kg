package kr.co.bestiansoft.ebillservicekg.form.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.form.service.FormService;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(tags = "코드 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class FormController {

    private final FormService formService;

    @ApiOperation(value = "서식 리스트 조회", notes = "서식 리스트를 조회한다.")
    @GetMapping("/form")
    public ResponseEntity<CommonResponse> getFormList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", formService.getFormList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "서식과 필드 생성", notes = "서식과 필드를 생성한다.")
    @PostMapping(value = "/formWithFields")
    public ResponseEntity<CommonResponse> createFormWithField(@RequestBody FormWithFieldsVo formWithFieldsVo){
        return  new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "form and fields created successfully", formService.createFormWithFields(formWithFieldsVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "서식과 필드 상세 조회", notes = "서시과 필드를 상세 조회한다.")
    @GetMapping("/formWithFields/detail/{formId}")
    public ResponseEntity<CommonResponse> getFormById(@PathVariable String formId) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(),  "OK", formService.getFormWithFieldsById(Integer.valueOf(formId))), HttpStatus.OK);
    }


}
