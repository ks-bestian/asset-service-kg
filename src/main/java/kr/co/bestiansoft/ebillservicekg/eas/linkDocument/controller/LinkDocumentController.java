package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.service.LinkDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
public class LinkDocumentController {

    final LinkDocumentService linkDocumentService;

    @ApiOperation(value="insertLinkDocument", notes = "insertLinkDocument")
    @PostMapping("/eas/linkDocument")
    public ResponseEntity<CommonResponse> insertLinkDocument(@RequestBody LinkDocumentVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", linkDocumentService.insertLinkDocument(vo)), HttpStatus.OK);
    }
    @ApiOperation(value="getLInkDocument", notes ="getLinkDocument")
    @GetMapping("/eas/linkDocument/{docId}")
    public ResponseEntity<CommonResponse> getLinkDocument(@PathVariable String docId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", linkDocumentService.getLinkDocumentByDocId(docId)), HttpStatus.OK);
    }
}