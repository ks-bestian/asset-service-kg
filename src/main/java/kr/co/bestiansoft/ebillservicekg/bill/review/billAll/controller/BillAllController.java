package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.service.BoardService;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.bill.review.billAll.service.BillAllService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "안건 전체 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class BillAllController {

    private final BillAllService billAllService;

    @ApiOperation(value = "안건 전체 리스트 조회", notes = "리스트를 조회한다.")
    @GetMapping("/bill/review/all")
    public ResponseEntity<CommonResponse> getBillList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billAllService.getBillList(param)), HttpStatus.OK);
    }


    @ApiOperation(value = "안건 전체 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/bill/review/all/detail/{billId}")
    public ResponseEntity<CommonResponse> getBillById(@PathVariable String billId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", billAllService.getBillById(billId, param)), HttpStatus.OK);
    }

}