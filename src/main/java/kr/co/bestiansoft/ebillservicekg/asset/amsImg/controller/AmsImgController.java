package kr.co.bestiansoft.ebillservicekg.asset.amsImg.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.bestiansoft.ebillservicekg.asset.amsImg.service.AmsImgService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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


    @Operation(summary = "장비 이미지 상세 목록 조회", description = "장비 이미지 상세 목록 조회한다.")
    @GetMapping("equip/img/list")
    public ResponseEntity<CommonResponse> getImgList(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", amsImgService.getDetailListByEqpmntId(eqpmntId)), HttpStatus.OK);
    }

    @Operation(summary = "My information image", description="My information Image Inquiry.")
    @GetMapping("asset/img/view")
    public ResponseEntity<?> getMyInfoImg(@RequestParam String pdfFileNm) {
        Resource resource = FileUtil.loadFile(pdfFileNm);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}
