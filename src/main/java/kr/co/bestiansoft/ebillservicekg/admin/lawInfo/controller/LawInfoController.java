package kr.co.bestiansoft.ebillservicekg.admin.lawInfo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.lawInfo.service.LawInfoService;
import kr.co.bestiansoft.ebillservicekg.admin.lawInfo.vo.LawInfoVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "법률관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LawInfoController {
    private final LawInfoService lawInfoService;

    @ApiOperation(value = "법률리스트 조회", notes = "법률리스트를 조회한다.")
    @GetMapping("admin/lawInfo")
    public ResponseEntity<CommonResponse> getLawInfoList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", lawInfoService.getLawInfoList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "법률 상세 조회", notes = "법률 상세를 조회한다.")
    @GetMapping("admin/lawInfo/{lawId}")
    public ResponseEntity<CommonResponse> getLawInfoDetail(@PathVariable Long lawId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", lawInfoService.getLawInfoDetail(lawId)), HttpStatus.OK);
    }

    @ApiOperation(value = "법률 생성", notes = "법률을 생성한다.")
    @PostMapping("admin/lawInfo")
    public ResponseEntity<CommonResponse> createLawInfo(@RequestBody LawInfoVo lawInfoVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "LawInfo created successfully.", lawInfoService.createLawInfo(lawInfoVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "법률 수정", notes = "법률을 수정한다.")
    @PutMapping("admin/lawInfo")
    public ResponseEntity<CommonResponse> updateLawInfo(@RequestBody LawInfoVo lawInfoVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "LawInfo updated successfully", lawInfoService.updateLawInfo(lawInfoVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "법률 삭제", notes = "법률을 삭제한다.")
    @DeleteMapping("admin/lawInfo")
    public ResponseEntity<CommonResponse> deleteLawInfo(@RequestBody List<Long> ids) {
        lawInfoService.deleteLawInfo(ids);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "LawInfo code deleted successfully."), HttpStatus.OK);

    }
}
