package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.controller;

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
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.BillAllService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Agenda entire API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class BillAllController {

    private final BillAllService billAllService;

    @Operation(summary = "Agenda entire List check", description = "List Inquiry.")
    @GetMapping("/bill/review/all")
    public ResponseEntity<CommonResponse> getBillList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billAllService.getBillList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Agenda entire particular check", description = "Details Inquiry.")
    @GetMapping("/bill/review/all/detail/{billId}")
    public ResponseEntity<CommonResponse> getBillById(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billAllService.getBillById(billId, param)), HttpStatus.OK);
    }

    @Operation(summary = "Agenda Monitoring check", description = "List Inquiry.")
    @GetMapping("/bill/search/monitor")
    public ResponseEntity<CommonResponse> selectListBillMonitor(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billAllService.selectListBillMonitor(param)), HttpStatus.OK);
    }

    @Operation(summary = "bill statistics check", description = "bill Statistics Inquiry.")
    @GetMapping("/bill/search/statistics")
    public ResponseEntity<CommonResponse> selectListBillStatistics(@RequestParam HashMap<String, Object> param) {
    	Object statisticsKind = param.get("statisticsKind");

    	if("ppslKnd".equals(statisticsKind)) {
    		return new ResponseEntity<>(new CommonResponse(200, "OK", billAllService.countBillByPpslKnd(param)), HttpStatus.OK);
    	}
    	else if("cmt".equals(statisticsKind)) {
    		return new ResponseEntity<>(new CommonResponse(200, "OK", billAllService.countBillByCmt(param)), HttpStatus.OK);
    	}
    	else if("poly".equals(statisticsKind)) {
    		return new ResponseEntity<>(new CommonResponse(200, "OK", billAllService.countBillByPoly(param)), HttpStatus.OK);
    	}
    	else {
    		throw new IllegalArgumentException();
    	}

    }

}