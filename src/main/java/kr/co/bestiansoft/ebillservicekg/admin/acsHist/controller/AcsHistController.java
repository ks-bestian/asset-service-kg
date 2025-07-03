package kr.co.bestiansoft.ebillservicekg.admin.acsHist.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.AcsHistService;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Api(tags = "Access history API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AcsHistController {

    private final AcsHistService acsHistService;

    @ApiOperation(value = "Access history list inquiry", notes = "Search for a list of access history.")
    @GetMapping("/api/admin/acsHist")
    public ResponseEntity<CommonResponse> getAcsHistList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", acsHistService.getAcsHistList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Inquiry of the agenda access history", notes = "Inquire a list of agenda access.")
    @GetMapping("/api/admin/billHist")
    public ResponseEntity<CommonResponse> getBillHistList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", acsHistService.getBillHistList(param)), HttpStatus.OK);
    }

}
