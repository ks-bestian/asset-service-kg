package kr.co.bestiansoft.ebillservicekg.eas.file.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.annotations.ApiOperation;

@RequiredArgsConstructor
@Controller
public class EasFileController {

    final EasFileService easFileService;

    @ApiOperation(value="uploadEasFile", notes = "uploadEasFile")
    @PostMapping(value="/eas/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadEasFile (EasFileVo fileVo){
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(),"Ok",easFileService.uploadEasFile(fileVo)), HttpStatus.CREATED);
    }
}