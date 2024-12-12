package kr.co.bestiansoft.ebillservicekg.admin.ccof.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.service.impl.CcofService;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.vo.CcofVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = "겸직 API")
public class CcofController {
    private final CcofService ccofService;

    @ApiOperation(value = "사용자 겸직 리스트 조회", notes = "사용자의 겸직 리스트를 조회한다.")
    @GetMapping("admin/ccof/{userId}")
    public ResponseEntity<CommonResponse> getMenuList(@RequestParam HashMap<String, Object> param, @PathVariable String userId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", ccofService.getCcofList(param, userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "사용자 겸직 저장", notes = "사용자의 겸직을 저장한다.")
    @PostMapping("admin/ccof")
    public ResponseEntity<CommonResponse> createMenu(@RequestBody CcofVo ccofVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Ccof saved successfully.", ccofService.createCcofInUser(ccofVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "겸직 삭제", notes = "메뉴 삭제한다.")
    @DeleteMapping("admin/ccof")
    public ResponseEntity<CommonResponse> deleteCcof(@RequestBody HashMap<String, Object> param) {
        ccofService.deleteCcofInUser(param);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Ccof deleted successfully."), HttpStatus.OK);

    }
}
