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
@Api(tags = "의원관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "의원 리스트 조회", notes = "의원 리스트를 조회한다.")
    @GetMapping("admin/member")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "의원상세 조회", notes = "의원 상세를 조회한다.")
    @GetMapping("admin/member/{memberId}")
    public ResponseEntity<CommonResponse> getMemberDetail(@PathVariable String memberId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberDetail(memberId)), HttpStatus.OK);
    }

    @ApiOperation(value = "의원 생성", notes = "의원를 생성한다.")
    @PostMapping("admin/member")
    public ResponseEntity<CommonResponse> createMember(@RequestBody MemberVo memberVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Member created successfully.", memberService.createMember(memberVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "의원 수정", notes = "의원을 수정한다.")
    @PutMapping("admin/member")
    public ResponseEntity<CommonResponse> updateMember(@RequestBody MemberVo memberVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Member updated successfully", memberService.updateMember(memberVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "의원 삭제", notes = "의원을 삭제한다.")
    @DeleteMapping("admin/member")
    public ResponseEntity<CommonResponse> deleteMember(@RequestBody List<String> memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Member code deleted successfully."), HttpStatus.OK);

    }
    
    @ApiOperation(value = "정당 의원 조회", notes = "정당별 의원을 조회한다.")
    @GetMapping("admin/poly/member")
    public ResponseEntity<CommonResponse> getMemberByPoly(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", memberService.getMemberByPoly(param)), HttpStatus.OK);
    }
}
