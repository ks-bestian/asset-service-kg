package kr.co.bestiansoft.ebillservicekg.eas.approval.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class ApprovalController {

    final ApprovalService approvalService ;

    @ApiOperation(value="insertApproval", notes = "insertApproval")
    @PostMapping("/eas/approval")
    public ResponseEntity<CommonResponse> insertApproval(@RequestBody ApprovalVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", approvalService.insertApproval(vo)), HttpStatus.OK);
    }

}
