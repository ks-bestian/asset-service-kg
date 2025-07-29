package kr.co.bestiansoft.ebillservicekg.asset.faq.controller;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.asset.faq.service.FaqService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "faq API")
@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {

    private final FaqService faqService;

    @Operation(summary = "get faq list by eqpmntId")
    @GetMapping
    public ResponseEntity<CommonResponse> getFaqList(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", faqService.getFaqList(eqpmntId)), HttpStatus.OK);
    }

    @Operation(summary = "설치 정보 삭제", description = "설치 정보를 삭제한다.")
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteFaqById(@RequestBody List<String> ids) {
    	faqService.deleteFaqById(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Faq deleted"), HttpStatus.OK);
    }

}
