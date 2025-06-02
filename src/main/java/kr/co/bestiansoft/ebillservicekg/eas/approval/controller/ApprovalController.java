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
        return new ResponseEntity<>(new CommonResponse(200, "OK", approvalService.getApproval(docId)), HttpStatus.OK);
    }
    @ApiOperation(value="getApproval List", notes = "getApproval List")
    @GetMapping("/eas/approval")
    public ResponseEntity<CommonResponse> getApprovals(SearchDocumentVo vo) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", approvalService.getApprovalList(vo)), HttpStatus.OK);
    }
    @ApiOperation(value="count Approval List", notes = "count Approval List")
    @GetMapping("/eas/approval/count")
    public ResponseEntity<CommonResponse> countApprovalList() {
        return new ResponseEntity<>(new CommonResponse(200, "OK", approvalService.countApprovalList()), HttpStatus.OK);
    }
    @ApiOperation(value = "Read Approval", notes = "Read Approval")
    @PutMapping("/eas/approval/read/{apvlId}")
    public ResponseEntity<CommonResponse> readApproval( @PathVariable int apvlId) {
        UpdateApprovalVo vo = UpdateApprovalVo.builder()
                .apvlId(apvlId)
                .checkDtm(LocalDateTime.now())
                .apvlStatusCd("AS03")
                .build();
        approvalService.updateApproval(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", apvlId ),HttpStatus.OK);
    }
    @ApiOperation(value = "Approval", notes = "Approval")
    @PostMapping("/eas/approval/approve")
    public ResponseEntity<CommonResponse> approval(@RequestBody UpdateApprovalVo vo) {
        documentWorkFlowService.approve(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK", vo.getApvlId() ),HttpStatus.OK);
    }
}
