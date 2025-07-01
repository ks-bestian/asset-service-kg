package kr.co.bestiansoft.ebillservicekg.eas.approval.controller;

import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.Impl.DocumentWorkFlowServiceImpl;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.SearchDocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class ApprovalController {

    final DocumentWorkFlowService documentWorkFlowService;
    final ApprovalService approvalService ;

    @ApiOperation(value="insertApproval", notes = "insertApproval")
    @PostMapping("/eas/approval")
    public ResponseEntity<CommonResponse> insertApproval(@RequestBody ApprovalVo vo) {
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", approvalService.insertApproval(vo)), HttpStatus.OK);
    }

    @GetMapping("/eas/approval/{docId}")
    public ResponseEntity<CommonResponse> getApproval(@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", approvalService.getApprovals(docId)), HttpStatus.OK);
    }
    @ApiOperation(value = "Read Approval", notes = "Read Approval")
    @PutMapping("/eas/approval/read/{apvlId}")
    public ResponseEntity<CommonResponse> readApproval( @PathVariable int apvlId) {
        documentWorkFlowService.updateReadApproval(apvlId);
        return new ResponseEntity<>(new CommonResponse(200, "OK", apvlId ),HttpStatus.OK);
    }
    @ApiOperation(value = "Approve", notes = "Approve")
    @PutMapping("/eas/approval/approve")
    public ResponseEntity<CommonResponse> approval(@RequestBody UpdateApprovalVo vo) {
        documentWorkFlowService.approve(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", vo.getApvlId() ),HttpStatus.OK);
    }
    @ApiOperation(value="Reject", notes = "reject")
    @PutMapping("/eas/approval/reject")
    public ResponseEntity<CommonResponse> approveReject(@RequestBody UpdateApprovalVo vo) {
        documentWorkFlowService.approveReject(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", vo.getApvlId() ),HttpStatus.OK);
    }
    @ApiOperation(value = "get approvals by docId", notes = "get approvals by docId")
    @GetMapping("/eas/approval/user/{docId}")
    public ResponseEntity<CommonResponse> getApprovalsByUserId(@PathVariable String docId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", approvalService.getApprovalsByUserId(docId)),HttpStatus.OK);
    }
}
