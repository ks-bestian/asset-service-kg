package kr.co.bestiansoft.ebillservicekg.myPage.myInfo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.myPage.myInfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Api(tags = "나의정보 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MyInfoController {

    private final MyInfoService myInfoService;

    @ApiOperation(value = "내정보 조회", notes = "내정보를 조회한다.")
    @GetMapping("myPage/myInfo")
    public ResponseEntity<CommonResponse> getMyInfo(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok",  myInfoService.getMyInfo(param)), HttpStatus.OK);
    }

//    @ApiOperation(value = "내정보 이미지", notes="내정보 이미지를 조회한다.")
//    @GetMapping('myPage/myInfo/img')
//    public ResponseEntity<CommonResponse> getMyInfoImg() {
//        try {
//            byte[] content = myInfoService.getFileContentByPath(new SecurityInfoUtil().getAccountId());
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//            return new ResponseEntity<>(content, headers, HttpStatus.OK);
//        }catch(Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

}
