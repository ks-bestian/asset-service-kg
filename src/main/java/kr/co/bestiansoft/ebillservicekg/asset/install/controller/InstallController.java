package kr.co.bestiansoft.ebillservicekg.asset.install.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.asset.install.service.InstallService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "install API")
@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/install")
public class InstallController {

    private final InstallService installService;

    @Operation(summary = "get install detail by eqpmnt id")
    @GetMapping
    public ResponseEntity<CommonResponse> getInstallList(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", installService.getInstallList(eqpmntId)), HttpStatus.OK);
    }

    @Operation(summary = "설치 정보 삭제", description = "설치 정보를 삭제한다.")
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteInstlById(@RequestBody List<String> ids) {
        installService.deleteInstlById(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Manual deleted"), HttpStatus.OK);
    }

}
