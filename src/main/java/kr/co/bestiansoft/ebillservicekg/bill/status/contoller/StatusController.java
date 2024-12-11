package kr.co.bestiansoft.ebillservicekg.bill.status.contoller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.bill.status.service.StatusService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "안건 현황 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class StatusController {

    private final StatusService statusService;

    @ApiOperation(value = "의사일정", notes = "의사일정을 조회한다")
    @GetMapping("/status/calendar")
    public ResponseEntity<CommonResponse> getMtngList(@RequestParam HashMap<String, Object> param) {
    	//TODO :: 쿼리 kg,ru 칼럼 추가해서 받아야함...
    	return new ResponseEntity<>(new CommonResponse(200, "OK", statusService.getMtngList(param)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "의안모니터링", notes = "안건의 현황을 조회한다")
    @GetMapping("/status/monitoring")
    public ResponseEntity<CommonResponse> getMonitoringList(@RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", statusService.getMonitorList(param)), HttpStatus.OK);
    }
    
    
}