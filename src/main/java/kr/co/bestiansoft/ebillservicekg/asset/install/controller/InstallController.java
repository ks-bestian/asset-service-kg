package kr.co.bestiansoft.ebillservicekg.asset.install.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.asset.install.service.InstallService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @Operation(summary = "get install place")
    @GetMapping("/place/list")
    public ResponseEntity<CommonResponse> getInstlPlace() {
        return new ResponseEntity<>(new CommonResponse(200, "OK", installService.getInstlPlace()), HttpStatus.OK);
    }

    @GetMapping(value="/imgs/{instlId}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> imgByInstlId(@PathVariable String instlId) throws IOException {
        Resource video = installService.instlImgAsResource(instlId);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(video);
    }


}
