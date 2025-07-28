package kr.co.bestiansoft.ebillservicekg.admin.member.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.admin.member.service.MemberService;
import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Parliament member management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "member List check", description = "member List Inquiry.")
    @GetMapping("admin/member")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Retrieve member details check", description = "member Details Inquiry.")
    @GetMapping("admin/member/{memberId}")
    public ResponseEntity<CommonResponse> getMemberDetail(@PathVariable String memberId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberDetail(memberId)), HttpStatus.OK);
    }

    @Operation(summary = "member generation", description = "Member Create.")
    @PostMapping("admin/member")
    public ResponseEntity<CommonResponse> createMember(@RequestBody MemberVo memberVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Member created successfully.", memberService.createMember(memberVo)), HttpStatus.OK);
    }

    @Operation(summary = "member correction", description = "Clinic Modify.")
    @PutMapping("admin/member")
    public ResponseEntity<CommonResponse> updateMember(@RequestBody MemberVo memberVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Member updated successfully", memberService.updateMember(memberVo)), HttpStatus.OK);
    }

    @Operation(summary = "member delete", description = "Member Delete.")
    @DeleteMapping("admin/member")
    public ResponseEntity<CommonResponse> deleteMember(@RequestBody List<String> memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Member code deleted successfully."), HttpStatus.OK);

    }

    @Operation(summary = "political party member check", description = "Political party Member Inquiry.")
    @GetMapping("admin/poly/member")
    public ResponseEntity<CommonResponse> getMemberByPoly(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberByPoly(param)), HttpStatus.OK);
    }

    @Operation(summary = "password reset", description = "password Initialize.")
    @PutMapping("admin/member/reset/pswd")
    public ResponseEntity<CommonResponse> resetPswd(@RequestBody HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Member updated successfully", memberService.resetPswd(param)), HttpStatus.OK);
    }
}
