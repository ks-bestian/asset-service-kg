package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MtngFromVo;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.service.MtngToService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngTo.vo.MtngToVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "meeting result API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class MtngToController {

    private final MtngToService mtngToService;

    @Operation(summary = "meeting result List check", description = "List Inquiry.")
    @GetMapping("/bill/mtng/to")
    public ResponseEntity<CommonResponse> getMtngToList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngToService.getMtngToList(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting result particular check", description = "Details Inquiry.")
    @GetMapping("/bill/mtng/to/detail/{mtngId}")
    public ResponseEntity<CommonResponse> getMtngToById(@PathVariable Long mtngId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngToService.getMtngToById(mtngId, param)), HttpStatus.OK);
    }


    @Operation(summary = "meeting result registration", description = "meeting result registration")
    @PostMapping(value = "/bill/mtng/result", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createMtngResult(@ModelAttribute MtngToVo mtngToVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngToService.createMtngResult(mtngToVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "meeting result registration Process progress", description = "meeting result registration Process progress")
    @PostMapping(value = "/bill/mtng/to/report", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> reportMtngTo(@ModelAttribute MtngToVo mtngToVo) throws Exception {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngToService.reportMtngTo(mtngToVo)), HttpStatus.CREATED);
    }


    @Operation(summary = "meeting result - member List check", description = "meeting result - member List Inquiry.")
    @GetMapping("/bill/mtng/to/member")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngToService.getMemberList(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting result - meeting cancellation", description = "meeting cancellation")
    @DeleteMapping("/bill/mtng/to")
    public ResponseEntity<CommonResponse> deleteMtngTo(@RequestBody List<Long> mtngIds) {
    	mtngToService.deleteMtng(mtngIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "meeting deleted successfully"), HttpStatus.OK);
    }

    @Operation(summary = "file delete", description = "meeting result Report Delete")
    @PutMapping(value = "/bill/mtng/to/report/delete")
    public ResponseEntity<CommonResponse> updateMtngFileDel(@RequestBody HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "mtng report delete successfully", mtngToService.updateMtngFileDel(param)), HttpStatus.OK);
    }
    
    @Operation(summary = "Send meeting agenda to the Legal Department.", description = "Send meeting agenda to the Legal Department.")
    @PutMapping("/bill/mtng/to/send")
    public ResponseEntity<CommonResponse> sendLegalActMtngAgenda(@RequestBody MtngFromVo mtngFromVo){
    	mtngToService.sendLegalActMtngAgenda(mtngFromVo.getAgendaList());
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "submission successful"), HttpStatus.OK);
    }
    
}