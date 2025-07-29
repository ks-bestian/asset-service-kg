package kr.co.bestiansoft.ebillservicekg.asset.faq.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.asset.faq.service.FaqService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "faq API")
@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {

    private final FaqService faqService;

    @Operation(summary = "get faq detail by faq id")
    @GetMapping
    public ResponseEntity<CommonResponse> getInstallList(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", faqService.getFaqList(eqpmntId)), HttpStatus.OK);
    }

    @Operation(summary = "설치 정보 삭제", description = "설치 정보를 삭제한다.")
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteFaqById(@RequestBody List<String> ids) {
    	faqService.deleteFaqById(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Faq deleted"), HttpStatus.OK);
    }

}
