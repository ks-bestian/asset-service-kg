package kr.co.bestiansoft.ebillservicekg.admin.acsHist.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.AcsHistService;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Api(tags = "접속이력 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class AcsHistController {

    private final AcsHistService acsHistService;

    @ApiOperation(value = "접속이력 리스트 조회", notes = "접속이력 리스트를 조회한다.")
    @GetMapping("/api/admin/acsHist")
    public ResponseEntity<CommonResponse> getAcsHistList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", acsHistService.getAcsHistList(param)), HttpStatus.OK);
    }

//    @ApiOperation(value = "접속이력 저장", notes = "접속이력 저장한다.")
//    @PostMapping("/api/admin/acsHist")
//    public void createAcsHist(@RequestBody AcsHistVo acsHistVo, HttpServletRequest request) {
//        acsHistService.createAcsHist(AcsHistVo.builder()
//                .regId(acsHistVo.getRegId())
//                .acsIp(request.getRemoteAddr())
//                .reqUrl(request.getRequestURI())
//                .reqMethod(request.getMethod())
//                .build());
//    }
}
