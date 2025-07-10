package kr.co.bestiansoft.ebillservicekg.admin.auth.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.admin.auth.service.AuthService;
import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Tag(name = "Authority API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Rights list inquiry", description = "Look up at the authority list.")
    @GetMapping("admin/auth")
    public ResponseEntity<CommonResponse> getAuthList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", authService.getAuthList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Details of authority", description = "Inquiry for the details of the authority.")
    @GetMapping("admin/auth/{authId}")
    public ResponseEntity<CommonResponse> getAuthDetail(@PathVariable Long authId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", authService.getAuthDetail(authId)), HttpStatus.OK);
    }

    @Operation(summary = "Creation of authority", description = "Create permissions.")
    @PostMapping("admin/auth")
    public ResponseEntity<CommonResponse> createAuth(@RequestBody AuthVo authVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthCode created successfully.", authService.createAuth(authVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Modify Permissions", description = "Modify the authority.")
    @PutMapping("admin/auth")
    public ResponseEntity<CommonResponse> updateAuth(@RequestBody AuthVo authVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "AuthCode updated successfully", authService.updateAuth(authVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete permission.", description = "Delete authority.")
    @DeleteMapping("admin/auth")
    public ResponseEntity<CommonResponse> deleteAuth(@RequestBody List<Long> ids) {
        authService.deleteAuth(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", "AuthCode deleted successfully."), HttpStatus.OK);
    }

}
