package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.controller;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.DraftDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class DraftDocumentController {

    final DraftDocumentService documentService;

    @Operation(summary = "insertDraftDocument", description = "insertDraftDocument")
    @PostMapping("/eas/draftDocument/{formId}")
    public ResponseEntity<CommonResponse> insertDraftDocument(@PathVariable int formId, @RequestBody Map<String, String> map) {
        System.out.println(map.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.insertDraftDocument(formId,map)), HttpStatus.OK);
    }
    @Operation(summary = "getDraftDocument", description = "getDraftDocument")
    @GetMapping("/eas/draftDocument/{aarsDocId}")
    public ResponseEntity<CommonResponse> getDraftDocument(@PathVariable int aarsDocId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getDraftDocument(aarsDocId)), HttpStatus.OK);
    }
}