package kr.co.bestiansoft.ebillservicekg.admin.ccof.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.admin.ccof.service.impl.CcofService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

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
