package kr.co.bestiansoft.ebillservicekg.eas.draftData.controller;


import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.service.DraftDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DraftDataController {
    private final DraftDataService draftDataService;

    @Operation(summary = "getDataByAarsDocId", description ="getDataByAarsDocId")
    @GetMapping("/eas/draftData/{aarsDocId}")
    public ResponseEntity<CommonResponse> getDataByAarsDocId(@PathVariable int aarsDocId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", draftDataService.getDraftData(aarsDocId)), HttpStatus.OK);
    }
}
