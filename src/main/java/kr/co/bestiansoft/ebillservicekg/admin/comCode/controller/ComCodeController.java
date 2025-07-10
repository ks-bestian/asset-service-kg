package kr.co.bestiansoft.ebillservicekg.admin.comCode.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.service.ComCodeService;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeDetailVo;
import kr.co.bestiansoft.ebillservicekg.admin.comCode.vo.ComCodeVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Tag(name = "code API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ComCodeController {

    private final ComCodeService comCodeService;

    @Operation(summary = "Group code List check", description = "Group code List Inquiry.")
    @GetMapping("/admin/grpCode")
    public ResponseEntity<CommonResponse> getGrpCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", comCodeService.getGrpCodeList(param)), HttpStatus.OK);
    }

    @Operation(summary = "code List check", description = "code List Inquiry.")
    @GetMapping("/admin/comCode")
    public ResponseEntity<CommonResponse> getCodeList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", comCodeService.getCodeList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Group code particular check", description = "Group code Details Inquiry.")
    @GetMapping("/admin/grpCode/{codeId}")
    public ResponseEntity<CommonResponse> getGrpCodeById(@PathVariable Integer grpCode) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", comCodeService.getGrpCodeById(grpCode)), HttpStatus.OK);
    }

    @Operation(summary = "code particular check", description = "code Details Inquiry.")
    @GetMapping("/admin/comCode/{codeId}")
    public ResponseEntity<CommonResponse> getComCodeById(@PathVariable String codeId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", comCodeService.getComCodeById(codeId)), HttpStatus.OK);
    }

    @Operation(summary = "Group code generation", description = "Group code Create.")
    @PostMapping(value = "/admin/grpCode")
    public ResponseEntity<CommonResponse> createGrpCode(@RequestBody ComCodeVo comCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "group code created successfully", comCodeService.createGrpCode(comCodeVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "cord generation", description = "Code Create.")
    @PostMapping(value = "/admin/comCode")
    public ResponseEntity<CommonResponse> createComCode(@RequestBody ComCodeDetailVo comCodeDetailVo) {
            return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), " code created successfully", comCodeService.createComCode(comCodeDetailVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Group code correction", description = "Group code Modify.")
    @PutMapping(value = "/admin/grpCode")
    public ResponseEntity<CommonResponse> updateGrpCode(@RequestBody ComCodeVo comCodeVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "group code updated successfully", comCodeService.updateGrpCode(comCodeVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "cord correction", description = "Code Modify.")
    @PutMapping(value = "/admin/comCode")
    public ResponseEntity<CommonResponse> updateComCode(@RequestBody ComCodeDetailVo comCodeDetailVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "code updated successfully", comCodeService.updateComCode(comCodeDetailVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Group code delete", description = "Group code Information Delete.")
    @DeleteMapping("/admin/grpCode")
    public ResponseEntity<CommonResponse> deleteGrpCode(@RequestBody List<Long> grpCodes) {
        comCodeService.deleteGrpCode(grpCodes);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "ok", "group code deleted successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Subcode delete", description = "Sub -code Information Delete.")
    @DeleteMapping("/admin/comCode/{grpCode}")
    public ResponseEntity<CommonResponse> deleteComCode(@RequestBody List<String> codeIds, @PathVariable int grpCode) {
        comCodeService.deleteComCode(codeIds, grpCode);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "code deleted successfully"), HttpStatus.OK);
    }
}
