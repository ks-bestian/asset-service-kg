package kr.co.bestiansoft.ebillservicekg.eas.file.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums.EasFileType;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class EasFileController {

    final EasFileService easFileService;

    @Operation(summary = "uploadEasFile", description = "uploadEasFile")
    @PostMapping(value="/eas/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadEasFile (EasFileVo fileVo){
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(),"Ok",easFileService.uploadEasFileAndConversionPdf(fileVo)), HttpStatus.CREATED);
    }
    @Operation(summary = "getWorkResponseFiles", description = "getWorkResponseFiles")
    @GetMapping(value = "/eas/file/workResponse/{docId}")
    public ResponseEntity<CommonResponse> getWorkResponseFiles (@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(),"Ok",easFileService.getAttachFiles(docId, EasFileType.EXECUTION_REPLY_FILE.getCodeId())), HttpStatus.OK);
    }
}