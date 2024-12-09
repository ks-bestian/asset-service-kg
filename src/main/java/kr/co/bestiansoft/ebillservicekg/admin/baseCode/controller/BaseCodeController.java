package kr.co.bestiansoft.ebillservicekg.admin.baseCode.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.service.BaseCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.baseCode.vo.BaseCodeVo;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Api
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BaseCodeController {

    private final BaseCodeService basecodeService;

    @ApiOperation(value = "대별코드리스트 조회", notes = "대별 리스트를 조회한다.")
    @GetMapping("/admin/baseCode")
    public ResponseEntity<CommonResponse> getBoardList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", basecodeService.getBaseCodeList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "대별코드 생성", notes = "대별코드 생성한다.")
    @PostMapping(value = "/admin/baseCode")
    public ResponseEntity<CommonResponse> createBoard(@RequestBody BaseCodeVo baseCodeVo) {

        try {
            return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "BaseCode created successfully", basecodeService.createBaseCode(baseCodeVo)), HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(new CommonResponse(HttpStatus.BAD_REQUEST.value(), "bad", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "대별코드 수정", notes = "대별코드를 수정한다.")
    @PutMapping(value = "/admin/baseCode")
    public ResponseEntity<CommonResponse> updateBoard(@RequestBody BaseCodeVo baseCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "BaseCode updated successfully", basecodeService.updateBaseCode(baseCodeVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "대별코드 삭제", notes = "대별코드를 삭제한다.")
    @DeleteMapping("/admin/baseCode")
    public ResponseEntity<CommonResponse> deleteBoard(@RequestBody List<Long> ids) {
        basecodeService.deleteBaseCode(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "BaseCode deleted successfully"), HttpStatus.OK);
    }

    @ApiOperation(value = "대별코드 상세 조회", notes = "대별코드 상세를 조회한다.")
    @GetMapping("/admin/baseCode/{baseCode}")
    public ResponseEntity<CommonResponse> getBoardById(@PathVariable Long baseCode) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", basecodeService.getBaseCodeById(baseCode)), HttpStatus.OK);
    }
}
