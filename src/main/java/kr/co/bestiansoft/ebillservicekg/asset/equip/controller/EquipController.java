package kr.co.bestiansoft.ebillservicekg.asset.equip.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.asset.equip.service.EquipService;
import kr.co.bestiansoft.ebillservicekg.asset.equip.vo.EquipRequest;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "장비 API")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/equip")
public class EquipController {
    private final EquipService equipService;

    @Operation(summary = "장비 생성", description = "장비를 생성한다.")
    @PostMapping
    public ResponseEntity<CommonResponse> createEuip(@RequestBody EquipRequest equipRequest) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "group code created successfully", equipService.createEquip(equipRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "장비유지관리 목록 조회", description = "장비유지관리 목록 조회한다.")
    @GetMapping
    public ResponseEntity<CommonResponse> getEquipList(@RequestParam HashMap<String, Object> params) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", equipService.getEquipItemLists(params)), HttpStatus.OK);
    }

    @Operation(summary = "equipment detail", description = "search equipment detail by eqpmntId")
    @GetMapping("/detail")
    public ResponseEntity<CommonResponse> getEquipDetail(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", equipService.getEquipDetail(eqpmntId)), HttpStatus.OK);
    }

    @Operation(summary = "장비 수정", description = "장비 수정한다.")
    @PutMapping
    public ResponseEntity<CommonResponse> updateEquip(@RequestBody EquipRequest equipRequest) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "Board updated successfully", equipService.updateEquip(equipRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "장비 삭제", description = "장비삭제한다.")
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteEquip(@RequestBody List<String> ids) {
        equipService.deleteEquip(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "LngCode deleted c"), HttpStatus.OK);
    }

}
