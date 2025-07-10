package kr.co.bestiansoft.ebillservicekg.admin.ccof.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Concurrent position API")
public class CcofController {
    private final CcofService ccofService;

    @Operation(summary = "Retrieves the user's list of concurrent positions.", description = "Retrieves the user's list of concurrent positions.")
    @GetMapping("admin/ccof")
    public ResponseEntity<CommonResponse> getMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", ccofService.getCcofList(param)), HttpStatus.OK);
    }
}
