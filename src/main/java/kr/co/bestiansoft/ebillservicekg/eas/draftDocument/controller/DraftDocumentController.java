package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.DraftDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiOperation;

@RequiredArgsConstructor
@Controller
public class DraftDocumentController {

    final DraftDocumentService documentService;

    @ApiOperation(value="insertDraftDocument", notes = "insertDraftDocument")
    @PostMapping("/eas/draftDocument")
    public ResponseEntity<CommonResponse> insertDraftDocument(@RequestBody DraftDocumentVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.insertDraftDocument(vo)), HttpStatus.OK);
    }
}