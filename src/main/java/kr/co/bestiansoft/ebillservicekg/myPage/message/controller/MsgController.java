package kr.co.bestiansoft.ebillservicekg.myPage.message.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.myPage.message.service.MsgService;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgRequest;
import kr.co.bestiansoft.ebillservicekg.myPage.message.vo.MsgVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Message management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MsgController {

    private final MsgService msgService;
    @Operation(summary = "message reception List check", description = "message reception List Inquiry.")
    @GetMapping("myPage/msg/rcv")
    public ResponseEntity<CommonResponse> getMsgRcvList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", msgService.getRcvList(param)), HttpStatus.OK);
    }

    @Operation(summary = "message Sender List check", description = "message Sender List Inquiry.")
    @GetMapping("/myPage/msg/send")
    public ResponseEntity<CommonResponse> getMsgSendList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", msgService.getSendList(param)), HttpStatus.OK);
    }

    @Operation(summary = "message particular check", description = "message Details Inquiry.")
    @GetMapping("/myPage/msg/{msgId}")
    public ResponseEntity<CommonResponse> getMsgDetail(@PathVariable Long msgId, @RequestParam String lang) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", msgService.getMsgDetail(msgId, lang)), HttpStatus.OK);
    }

    @Operation(summary = "message forwarding", description = "Message Transfer.")
    @PostMapping(value = "/myPage/msg", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> sendMsg(MsgRequest msgRequest) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Msg sent successfully.", msgService.sendMsg(msgRequest)), HttpStatus.OK);
    }

    @Operation(summary = "message Confirmation date and time save", description = "message Confirmation date and time Store.")
    @PutMapping(value = "/myPage/msg/rcvDt")
    public ResponseEntity<CommonResponse> msgRcvDt(@RequestBody HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Msg sent successfully.", msgService.msgRcvDt(param)), HttpStatus.OK);
    }

    @Operation(summary = "user entire check", description = "entire User Inquiry.")
    @GetMapping(value = "/myPage/msg/user")
    public ResponseEntity<CommonResponse> getUserMember(@RequestParam  HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", msgService.getUserMember(param)), HttpStatus.OK);
    }

    @Operation(summary = "message delete", description = "Message Delete.")
    @DeleteMapping("/myPage/msg")
    public ResponseEntity<CommonResponse> deleteMsg(@RequestBody List<Long> msgIds) {
        msgService.deleteMsg(msgIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Msg deleted successfully.", "deleted"), HttpStatus.OK);
    }


}
