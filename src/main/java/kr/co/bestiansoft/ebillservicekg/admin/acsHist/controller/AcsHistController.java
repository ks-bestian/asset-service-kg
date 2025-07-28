package kr.co.bestiansoft.ebillservicekg.admin.acsHist.controller;


import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.AcsHistService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Access history API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AcsHistController {

    private final AcsHistService acsHistService;

    @Operation(summary = "Access history list inquiry", description = "Search for a list of access history.")
    @GetMapping("/api/admin/acsHist")
    public ResponseEntity<CommonResponse> getAcsHistList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", acsHistService.getAcsHistList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Inquiry of the agenda access history", description = "Inquire a list of agenda access.")
    @GetMapping("/api/admin/billHist")
    public ResponseEntity<CommonResponse> getBillHistList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", acsHistService.getBillHistList(param)), HttpStatus.OK);
    }

}
