package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.service.MtngAllService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "meeting entire API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class MtngAllController {

    private final MtngAllService mtngAllService;

    @Operation(summary = "meeting entire List check", description = "List Inquiry.")
    @GetMapping("/bill/mtng/all")
    public ResponseEntity<CommonResponse> getMtngList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngAllService.getMtngList(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting entire particular check", description = "Details Inquiry.")
    @GetMapping("/bill/mtng/all/detail/{mtngId}")
    public ResponseEntity<CommonResponse> getMtngById(@PathVariable Long mtngId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngAllService.getMtngById(mtngId, param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting participant check", description = "participant Inquiry.")
    @GetMapping("/bill/mtng/all/participants")
    public ResponseEntity<CommonResponse> getMtngParticipants(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngAllService.getMtngParticipants(param)), HttpStatus.OK);
    }

    @Operation(summary = "Agenda meeting List check", description = "Agenda meeting List Inquiry.")
    @GetMapping("/bill/mtng/all/bybill")
    public ResponseEntity<CommonResponse> selectMtngByBillId(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngAllService.selectMtngByBillId(param)), HttpStatus.OK);
    }
}