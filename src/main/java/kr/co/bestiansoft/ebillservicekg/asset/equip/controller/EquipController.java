package kr.co.bestiansoft.ebillservicekg.asset.equip.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.asset.equip.service.EquipService;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "장비 API")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/equip")
public class EquipController {
    private final EquipService equipService;

    @Operation(summary = "장비 생성", description = "장비를 생성한다.")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createEuip(EquipRequest equipRequest, @RequestParam("mnulVoList") String mnlVoJson, @RequestParam("installVoList") String installVoJson, @RequestParam("faqVoList") String faqVoJson, @RequestParam Map<String, MultipartFile> fileMap) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Equipment created successfully", equipService.createEquip(equipRequest, mnlVoJson, installVoJson, faqVoJson, fileMap)), HttpStatus.CREATED);
    }
    
    //목록만 가져옵시다!! 목록안에 목록이 또있다!!
    /*
    @Operation(summary = "장비유지관리 목록 조회", description = "장비유지관리 목록 조회한다.")
    @GetMapping
    public ResponseEntity<CommonResponse> getEquipList(@RequestParam HashMap<String, Object> params) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", equipService.getEquipList(params)), HttpStatus.OK);
    }
    */
    @Operation(summary = "장비유지관리 목록 조회", description = "장비유지관리 목록 조회한다.")
    @GetMapping
    public ResponseEntity<CommonResponse> getEquipListAll(@RequestParam HashMap<String, Object> params) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", equipService.getEquipListAll(params)), HttpStatus.OK);
    }

    @Operation(summary = "equipment detail", description = "search equipment detail by eqpmntId")
    @GetMapping("/detail")
    public ResponseEntity<CommonResponse> getEquipDetail(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", equipService.getEquipDetail(eqpmntId)), HttpStatus.OK);
    }

    @Operation(summary = "장비 수정", description = "장비 수정한다.")
    @PutMapping
    public ResponseEntity<CommonResponse> updateEquip(@RequestBody EquipRequest equipRequest, @RequestParam("faqVoList") String faqVoJson) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "Equipment updated successfully", equipService.updateEquip(equipRequest, faqVoJson)), HttpStatus.CREATED);
    }

    @Operation(summary = "장비 삭제", description = "장비삭제한다.")
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteEquip(@RequestBody List<String> ids) {
        equipService.deleteEquip(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "equipment deleted"), HttpStatus.OK);
    }

    @GetMapping(value = "/thumbnail/{eqpmntId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> streamThumbnail(@PathVariable String eqpmntId) throws IOException {
        Resource thumbnail = equipService.loadThumbnail(eqpmntId);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(thumbnail);
    }



}
