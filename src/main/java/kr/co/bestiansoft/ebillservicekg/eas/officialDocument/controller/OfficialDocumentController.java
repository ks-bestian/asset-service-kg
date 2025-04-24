package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.InsertDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.OfficialDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.SearchDocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "전자문서 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Controller
public class OfficialDocumentController {
    private final OfficialDocumentService documentService;

    @ApiOperation(value="saveDocument", notes = "saveDocument")
    @PostMapping("/eas/document")
    public ResponseEntity<CommonResponse> saveOfficialDocument (@RequestBody InsertDocumentVo vo) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.saveAllDocument(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "getDocumentLists", notes = "getDocumentLists")
    @GetMapping("/eas/documents")
    public ResponseEntity<CommonResponse> getOfficialDocument (SearchDocumentVo vo){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getDocumentList(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "getDocumentDetail", notes = "getDocumentDetail")
    @GetMapping("/eas/document/{rcvId}")
    public ResponseEntity<CommonResponse> getOfficialDocument (@PathVariable int rcvId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getDocumentDetail(rcvId)), HttpStatus.OK);
    }
    @ApiOperation(value = "getCount", notes = "getCount")
    @GetMapping("/eas/document/count")
    public ResponseEntity<CommonResponse> getDocumentCount (){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.countDocumentList()), HttpStatus.OK);
    }

}
