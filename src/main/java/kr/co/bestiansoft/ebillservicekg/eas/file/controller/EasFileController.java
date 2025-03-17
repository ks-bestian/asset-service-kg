package kr.co.bestiansoft.ebillservicekg.eas.file.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.annotations.ApiOperation;

@RequiredArgsConstructor
@Controller
public class EasFileController {

    final EasFileService easFileService;

    @ApiOperation(value="insertEasFile", notes = "insertEasFile")
    @PostMapping("/eas/file")
    public ResponseEntity<CommonResponse> insertEasFile(@RequestBody EasFileVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", easFileService.insertEasFile(vo)), HttpStatus.OK);
    }
}