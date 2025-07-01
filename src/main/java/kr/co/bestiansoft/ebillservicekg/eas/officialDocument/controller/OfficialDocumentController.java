package kr.co.bestiansoft.ebillservicekg.eas.officialDocument.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums.ReceiveStatus;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.service.DocumentWorkFlowService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.service.OfficialDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.InsertDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.officialDocument.vo.SearchDocumentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Api(tags = "전자문서 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Controller
public class OfficialDocumentController {

    private final OfficialDocumentService documentService;
    private final DocumentWorkFlowService documentWorkFlowService;

    @ApiOperation(value="saveDocument", notes = "saveDocument")
    @PostMapping("/eas/document")
    public ResponseEntity<CommonResponse> saveOfficialDocument (@RequestBody InsertDocumentVo vo) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentWorkFlowService.saveAllDocument(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "getDocumentLists", notes = "getDocumentLists")
    @GetMapping("/eas/documents")
    public ResponseEntity<CommonResponse> getOfficialDocument (SearchDocumentVo vo){
        System.out.println(vo.toString());
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getDocumentList(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "getDocumentDetail", notes = "getDocumentDetail")
    @GetMapping("/eas/document/{docId}")
    public ResponseEntity<CommonResponse> getOfficialDocument (@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getDocumentDetail(docId)), HttpStatus.OK);
    }
    @ApiOperation(value = "getReceivedCount", notes = "getReceivedCount")
    @GetMapping("/eas/document/receive/count")
    public ResponseEntity<CommonResponse> getDocumentCount (){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.countDocumentList()), HttpStatus.OK);
    }
    @ApiOperation(value = "getCountWorkList", notes ="getCountWorkList")
    @GetMapping("/eas/document/work/count")
    public ResponseEntity<CommonResponse> getCountWorkList (){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.countWorkList()), HttpStatus.OK);
    }
    @ApiOperation(value = "getCountRejectList", notes ="getCountRejectList")
    @GetMapping("/eas/document/reject/count")
    public ResponseEntity<CommonResponse> getCountRejectList (){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.countRejectDocument()), HttpStatus.OK);
    }
    @ApiOperation(value = "updateDocumentStatus", notes = "updateDocumentStatus")
    @PutMapping("/eas/document/status")
    public ResponseEntity<CommonResponse> updateStatusOfficialDocument (String docId, String status){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.updateStatusOfficialDocument(docId, status)), HttpStatus.OK);
    }
    @ApiOperation(value = "getDocumentUser", notes = "getDocumentUser")
    @GetMapping("/eas/document/user/{docId}")
    public ResponseEntity<CommonResponse> getDocumentUser ( @PathVariable  String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getDocumentUser(docId)), HttpStatus.OK);
    }
    @ApiOperation(value = "updateReadDatetime", notes = "updateReadDatetime")
    @PutMapping("/eas/document/read/{rcvId}")
    public ResponseEntity<CommonResponse> updateStatusOfficialDocument (@PathVariable int rcvId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentWorkFlowService.updateReadDateTime(rcvId)), HttpStatus.OK);
    }
    @ApiOperation(value = "is Reject", notes="is Reject docId")
    @GetMapping("/eas/reject/{docId}")
    public ResponseEntity<CommonResponse> isReject(@PathVariable String docId){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.isReject(docId)), HttpStatus.OK);
    }
    @ApiOperation(value = "get reject list", notes="get reject list")
    @GetMapping("/eas/document/reject")
    public ResponseEntity<CommonResponse> getReject(SearchDocumentVo vo){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getRejectDocumentList(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "get my document list", notes="get my document list")
    @GetMapping("/eas/document/myDocument")
    public ResponseEntity<CommonResponse> getMyDocument(SearchDocumentVo vo){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getMyDocumentList(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "get work document list", notes="get work document list")
    @GetMapping("/eas/document/work")
    public ResponseEntity<CommonResponse> getWorkDocument(SearchDocumentVo vo){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getWorkList(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "get processed document list", notes="get processed document list")
    @GetMapping("/eas/document/processed")
    public ResponseEntity<CommonResponse> getProcessedDocument(SearchDocumentVo vo){
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getProcessedList(vo)), HttpStatus.OK);
    }
    @ApiOperation(value = "save Reply Document", notes="save Reply Document")
    @PostMapping("/eas/document/reply")
    public ResponseEntity<CommonResponse> saveReplyDocument(@RequestBody InsertDocumentVo vo){
        documentWorkFlowService.saveReplyDocument(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK"), HttpStatus.OK);
    }
    @ApiOperation(value=" rewrite reject document", notes="rewrite")
    @PostMapping("/eas/document/rewrite")
    public ResponseEntity<CommonResponse> rewriteDocument(@RequestBody InsertDocumentVo vo){
         documentWorkFlowService.rewriteDocument(vo);
        return new ResponseEntity<>(new CommonResponse(200, "OK"), HttpStatus.OK);
    }
    @ApiOperation(value ="end rejected document", notes="end rejected document")
    @PutMapping("/eas/document/reject/end/{docId}")
    public ResponseEntity<CommonResponse> endRejectedDocument (@PathVariable String docId){
        documentWorkFlowService.endRejectedDocument(docId);
        return new ResponseEntity<>(new CommonResponse(200, "OK"), HttpStatus.OK);
    }
    @ApiOperation(value="getApproval List", notes = "getApproval List")
    @GetMapping("/eas/approval")
    public ResponseEntity<CommonResponse> getApprovals(SearchDocumentVo vo) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.getApprovalList(vo)), HttpStatus.OK);
    }
    @ApiOperation(value="count Approval List", notes = "count Approval List")
    @GetMapping("/eas/approval/count")
    public ResponseEntity<CommonResponse> countApprovalList() {
        return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.countApprovalList()), HttpStatus.OK);
    }
}
