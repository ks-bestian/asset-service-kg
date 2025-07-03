package kr.co.bestiansoft.ebillservicekg.admin.member.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.member.service.MemberService;
import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "Parliament member management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "member List check", notes = "member List Inquiry.")
    @GetMapping("admin/member")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieve member details check", notes = "member Details Inquiry.")
    @GetMapping("admin/member/{memberId}")
    public ResponseEntity<CommonResponse> getMemberDetail(@PathVariable String memberId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberDetail(memberId)), HttpStatus.OK);
    }

    @ApiOperation(value = "member generation", notes = "Member Create.")
    @PostMapping("admin/member")
    public ResponseEntity<CommonResponse> createMember(@RequestBody MemberVo memberVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Member created successfully.", memberService.createMember(memberVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "member correction", notes = "Clinic Modify.")
    @PutMapping("admin/member")
    public ResponseEntity<CommonResponse> updateMember(@RequestBody MemberVo memberVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Member updated successfully", memberService.updateMember(memberVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "member delete", notes = "Member Delete.")
    @DeleteMapping("admin/member")
    public ResponseEntity<CommonResponse> deleteMember(@RequestBody List<String> memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Member code deleted successfully."), HttpStatus.OK);

    }
    
    @ApiOperation(value = "political party member check", notes = "Political party Member Inquiry.")
    @GetMapping("admin/poly/member")
    public ResponseEntity<CommonResponse> getMemberByPoly(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberByPoly(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "password reset", notes = "password Initialize.")
    @PutMapping("admin/member/reset/pswd")
    public ResponseEntity<CommonResponse> resetPswd(@RequestBody HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Member updated successfully", memberService.resetPswd(param)), HttpStatus.OK);
    }
}
