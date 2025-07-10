package kr.co.bestiansoft.ebillservicekg.sed_jk.controller;

import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.request.SignRequestDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.response.CheckResponseDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.response.SignResponseDto;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.enums.Paths;
import kr.co.bestiansoft.ebillservicekg.sed_jk.services.common.DocumentSignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping(value = Paths.DOCUMENT)
public class DocumentSignController {

    private final DocumentSignService documentSignService;

    @GetMapping("/sign-test")
    public ResponseEntity<SignResponseDto> sign(
            @Valid SignRequestDto dto
            ) {

        SignResponseDto response = documentSignService.signDocument(dto);

        if (response.getSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            log.warn("Document signing failed for file ID {}: {}", dto.apvlId(), response.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
//    @PostMapping("/sign")
//    public ResponseEntity<SignResponseDto> sign(
//            @Valid @RequestBody SignRequestDto dto
//            ) {
//
//        SignResponseDto response = documentSignService.signDocument(dto);
//
//        if (response.getSuccess()) {
//            return ResponseEntity.ok(response);
//        } else {
////            log.warn("Document signing failed for file ID {}: {}", dto.agreementId(), response.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }

//    @GetMapping("/check-agreement/{id}")
//    public ResponseEntity<CheckResponseDto> chekAgreement(@PathVariable Long id){
//        CheckResponseDto response = documentSignService.checkAgreement(id);
//
//        if (response.success()) {
//            return ResponseEntity.ok(response);
//        } else {
//            log.warn("Document check failed for agreement ID {}: {}", id, response.message());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//    @GetMapping("/check-document/{id}")
//    public ResponseEntity<CheckResponseDto> chekDocument(@PathVariable Long id){
//        CheckResponseDto response = documentSignService.checkDocument(id);
//
//        if (response.success()) {
//            return ResponseEntity.ok(response);
//        } else {
//            log.warn("Document check failed for agreement ID {}: {}", id, response.message());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
}
