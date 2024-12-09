package kr.co.bestiansoft.ebillservicekg.admin.comCode.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.service.ComCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "코드 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ComCodeController {

    private final ComCodeService comCodeService;

    @ApiOperation(value = "그룹코드 리스트 조회", notes = "그룹코드 리스트를 조회한다.")
    @GetMapping("/admin/grpCode")
    public ResponseEntity<CommonResponse> getGrpCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", comCodeService.getGrpCodeList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "코드 리스트 조회", notes = "코드 리스트를 조회한다.")
    @GetMapping("/admin/comCode")
    public ResponseEntity<CommonResponse> getCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", comCodeService.getCodeList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "그룹코드 상세 조회", notes = "그룹코드 상세를 조회한다.")
    @GetMapping("/admin/grpCode/{codeId}")
    public ResponseEntity<CommonResponse> getGrpCodeById(@PathVariable String grpCode) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", comCodeService.getGrpCodeById(grpCode)), HttpStatus.OK);
    }

    @ApiOperation(value = "코드 상세 조회", notes = "코드 상세를 조회한다.")
    @GetMapping("/admin/comCode/{codeId}")
    public ResponseEntity<CommonResponse> getComCodeById(@PathVariable String codeId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", comCodeService.getComCodeById(codeId)), HttpStatus.OK);
    }

    @ApiOperation(value = "그룹코드 생성", notes = "그룹코드를 생성한다.")
    @PostMapping(value = "/admin/grpCode")
    public ResponseEntity<CommonResponse> createGrpCode(@RequestBody ComCodeVo comCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", comCodeService.createGrpCode(comCodeVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "코드 생성", notes = "코드를 생성한다.")
    @PostMapping(value = "/admin/comCode")
    public ResponseEntity<CommonResponse> createComCode(@RequestBody ComCodeDetailVo comCodeDetailVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", comCodeService.createComCode(comCodeDetailVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "그룹코드 수정", notes = "그룹코드를 수정한다.")
    @PutMapping(value = "/admin/grpCode")
    public ResponseEntity<CommonResponse> updateGrpCode(@RequestBody ComCodeVo comCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "Board updated successfully", comCodeService.updateGrpCode(comCodeVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "코드 수정", notes = "코드를 수정한다.")
    @PutMapping(value = "/admin/comCode")
    public ResponseEntity<CommonResponse> updateComCode(@RequestBody ComCodeDetailVo comCodeDetailVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "Board updated successfully", comCodeService.updateComCode(comCodeDetailVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "그룹코드 삭제", notes = "그룹코드를 정보를 삭제한다.") //그룹코드의 경우 하위코드 존재하면 삭제불가
    @DeleteMapping("/admin/grpCode")
    public ResponseEntity<CommonResponse> deletegrpCode(@RequestBody Integer grpCode) {
        int code = HttpStatus.OK.value();
        String msg= "ok";
        Object data= "Board deleted successfully";
        HttpStatus status = HttpStatus.OK;

        try {
            comCodeService.deleteGrpCode(grpCode);
        } catch (UnsupportedOperationException e) {
            code = HttpStatus.BAD_REQUEST.value();
            msg = "bad";
            data = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new CommonResponse(code, msg, data), status);
    }

    @ApiOperation(value = "하위코드 삭제", notes = "하위코드를 정보를 삭제한다.") //그룹코드의 경우 하위코드 존재하면 삭제불가
    @DeleteMapping("/admin/comCode")
    public ResponseEntity<CommonResponse> deleteGrpCode(@RequestBody String codeId) {
        comCodeService.deleteComCode(codeId);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Board deleted successfully"), HttpStatus.OK);
    }
}
