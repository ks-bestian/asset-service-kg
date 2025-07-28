package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.controller;

import java.io.InputStream;
import java.util.HashMap;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "My information API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MyInfoController {

    private final MyInfoService myInfoService;

    @Operation(summary = "My information check", description = "My information Inquiry.")
    @GetMapping("myPage/myInfo")
    public ResponseEntity<CommonResponse> getMyInfo(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok",  myInfoService.getMyInfo(param)), HttpStatus.OK);
    }

    @Operation(summary = "My information image", description="My information Image Inquiry.")
    @GetMapping("myPage/myInfo/img")
    public ResponseEntity<?> getMyInfoImg(@RequestParam HashMap<String, Object> param) {
        InputStream content = myInfoService.getFileContentByPath(param);
        Resource resource = content != null ? new InputStreamResource(content) : null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @Operation(summary = "My information correction", description = "My information Modify.")
    @PostMapping(value = "myPage/myInfo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> updateMyInfo(UserMemberVo userMemberVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", myInfoService.updateMyInfo(userMemberVo)), HttpStatus.CREATED);
    }



}
