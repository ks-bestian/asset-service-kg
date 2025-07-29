package kr.co.bestiansoft.ebillservicekg.asset.bzenty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.asset.bzenty.service.BzentyService;
import kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo.BzentyVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "업체관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BzentyController {
    private final BzentyService bzentyService;

    @Operation(summary = "업체 리스트 조회", description = "업체 리스트를 조회한다.")
    @GetMapping("/asset/bzenty")
    public ResponseEntity<CommonResponse> getBzentyList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", bzentyService.getBzentyList(param)), HttpStatus.OK);
    }

    @Operation(summary = "업체상세 조회", description = "업체 상세를 조회한다.")
    @GetMapping("/asset/bzenty/detail")
    public ResponseEntity<CommonResponse> getBzentyDetail(@RequestParam String bzentyId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", bzentyService.getBzentyDetail(bzentyId)), HttpStatus.OK);
    }

    @Operation(summary = "업체 생성", description = "업체를 생성한다.")
    @PostMapping("/asset/bzenty")
    public ResponseEntity<CommonResponse> createBzenty(@RequestBody BzentyVo bzentyVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bzenty created successfully.", bzentyService.createBzenty(bzentyVo)), HttpStatus.OK);
    }

    @Operation(summary = "직원 수정", description = "직원을 수정한다.")
    @PutMapping("/asset/bzenty")
    public ResponseEntity<CommonResponse> updateBzenty(@RequestBody BzentyVo bzentyVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Bzenty updated successfully", bzentyService.updateBzenty(bzentyVo)), HttpStatus.OK);
    }

    @Operation(summary = "직원 삭제", description = "직원을 삭제한다.")
    @DeleteMapping("/asset/bzenty")
    public ResponseEntity<CommonResponse> deleteBzenty(@RequestBody List<String> ids) {
    	bzentyService.deleteBzenty(ids);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Bzenty code deleted successfully."), HttpStatus.OK);
    }

}
