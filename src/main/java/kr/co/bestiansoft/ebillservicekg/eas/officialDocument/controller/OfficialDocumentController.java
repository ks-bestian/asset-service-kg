package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.InsertDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "전자문서 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Controller
public class OfficialDocumentController {
    private final OfficialDocumentService documentService;

    @ApiOperation(value = "getDocument", notes = "getDocument")
    @GetMapping("/eas/documents")
    public ResponseEntity<CommonResponse> getOfficialDocument (OfficialDocumentVo vo){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getOfficialDocument(vo)), HttpStatus.OK);
    }
    @ApiOperation(value="saveDocument", notes = "saveDocument")
    @PostMapping("/eas/document")
    public ResponseEntity<CommonResponse> saveOfficialDocument (@RequestBody InsertDocumentVo vo) {

        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.saveAllDocument(vo)), HttpStatus.OK);
    }

}
