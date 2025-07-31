package kr.co.bestiansoft.ebillservicekg.asset.amsImg.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.AmsImgService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


@Tag(name = "이미지 API")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping
public class AmsImgController {

    private final AmsImgService amsImgService;

    @Operation(summary = "이미지 생성", description = "이미지를 생성한다.")
    @GetMapping("/com/img/down")
    public ResponseEntity<?> imgDownload(@RequestParam String fileId, HttpServletResponse response, HttpServletRequest request) throws Exception {

//        InputStream ins = edv.download(fileId);
        InputStream ins = amsImgService.imgDownload(fileId);
        Resource resource = new InputStreamResource(ins);

        if (resource == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "장비 이미지 상세 목록 조회", description = "장비 이미지 상세 목록 조회한다.")
    @GetMapping("equip/img/list")
    public ResponseEntity<CommonResponse> getImgList(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", amsImgService.getDetailListByEqpmntId(eqpmntId)), HttpStatus.OK);
    }



}
