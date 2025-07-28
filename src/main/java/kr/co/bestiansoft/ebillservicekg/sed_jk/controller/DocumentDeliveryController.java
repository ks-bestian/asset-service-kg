package kr.co.bestiansoft.ebillservicekg.sed_jk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import kr.co.bestiansoft.ebillservicekg.sed_jk.enums.Paths;
import kr.co.bestiansoft.ebillservicekg.sed_jk.services.document.DocumentDeliveryService;
import kr.co.bestiansoft.ebillservicekg.sed_jk.services.document.dto.GatewaySendResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
//@RequestMapping(value = Paths.DOCUMENT)
public class DocumentDeliveryController {
    private final DocumentDeliveryService documentDeliveryService;

    @GetMapping("/send-test")
    public ResponseEntity<GatewaySendResponseDto> send(Long id){
        GatewaySendResponseDto response = documentDeliveryService.sendDocumentToRecipients(id);

        if (response.success()) {
            return ResponseEntity.ok(response);
        } else {
            log.warn("Document sending failed for file ID: {}", response.message());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
