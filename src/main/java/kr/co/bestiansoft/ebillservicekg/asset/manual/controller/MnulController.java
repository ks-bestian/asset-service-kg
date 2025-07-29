package kr.co.bestiansoft.ebillservicekg.asset.manual.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.asset.manual.service.MnulService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "메뉴얼 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mnul")
public class MnulController {

    private final MnulService mnulService;

    @Operation(summary = "메뉴얼 삭제", description = "메뉴얼 삭제한다.")
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteMnulById(@RequestBody List<String> ids) {
        mnulService.deleteMnulById(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Manual deleted"), HttpStatus.OK);
    }


}
