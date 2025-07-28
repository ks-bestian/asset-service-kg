package kr.co.bestiansoft.ebillservicekg.eas.linkDocument.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.service.LinkDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.linkDocument.vo.LinkDocumentVo;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LinkDocumentController {

    final LinkDocumentService linkDocumentService;

    @Operation(summary = "insertLinkDocument", description = "insertLinkDocument")
    @PostMapping("/eas/linkDocument")
    public ResponseEntity<CommonResponse> insertLinkDocument(@RequestBody LinkDocumentVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", linkDocumentService.insertLinkDocument(vo)), HttpStatus.OK);
    }
    @Operation(summary = "getLInkDocument", description ="getLinkDocument")
    @GetMapping("/eas/linkDocument/{docId}")
    public ResponseEntity<CommonResponse> getLinkDocument(@PathVariable String docId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", linkDocumentService.getLinkDocumentByDocId(docId)), HttpStatus.OK);
    }
}