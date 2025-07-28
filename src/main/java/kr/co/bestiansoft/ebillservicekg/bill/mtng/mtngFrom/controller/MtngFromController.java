package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.service.MtngFromService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MtngFromVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "meeting expected API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class MtngFromController {

    private final MtngFromService mtngFromService;

    @Operation(summary = "meeting expected List check", description = "List Inquiry.")
    @GetMapping("/bill/mtng/from")
    public ResponseEntity<CommonResponse> getMtngFromList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getMtngFromList(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting expected particular check", description = "Details Inquiry.")
    @GetMapping("/bill/mtng/from/detail/{mtngId}")
    public ResponseEntity<CommonResponse> getMtngFromById(@PathVariable Long mtngId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getMtngFromById(mtngId, param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting expected registration", description = "meeting expected registration")
    @PostMapping(value = "/bill/mtng/from")
    public ResponseEntity<CommonResponse> createMtngFrom(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.createMtngFrom(mtngFromVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "meeting expected - member List check", description = "meeting expected - member List Inquiry.")
    @GetMapping("/bill/mtng/from/member")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.getMemberList(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting expected - department(committee) List check", description = "meeting expected - department(committee) List Inquiry.")
    @GetMapping("/bill/mtng/from/dept")
    public ResponseEntity<CommonResponse> getDeptList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.getDeptList(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting expected - meeting cancellation", description = "meeting cancellation")
    @DeleteMapping("/bill/mtng/from")
    public ResponseEntity<CommonResponse> deleteMtngFrom(@RequestBody List<Long> mtngIds) {
    	mtngFromService.deleteMtng(mtngIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "meeting deleted successfully"), HttpStatus.OK);
    }

    @Operation(summary = "meeting expected - Committee The agenda check", description = "meeting expected - The agenda Inquiry.")
    @GetMapping("/bill/mtng/from/selectListMtngBill")
    public ResponseEntity<CommonResponse> selectListMtngBill(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.selectListMtngBill(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting expected - Plenary The agenda check", description = "meeting expected - Plenary The agenda Inquiry.")
    @GetMapping("/bill/mtng/from/selectListMainMtngBill")
    public ResponseEntity<CommonResponse> selectListMainMtngBill(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.selectListMainMtngBill(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting expected correction", description = "intended Meeting Modify")
    @PutMapping("/bill/mtng/from/update")
    public ResponseEntity<CommonResponse> updateMtngBill(@RequestBody MtngFromVo mtngFromVo){
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "mtng update successfully", mtngFromService.updateMtngBill(mtngFromVo)), HttpStatus.OK);
    }



    /*Hall meeting*/

    @Operation(summary = "Hall Meeting", description = "meeting expected registration")
    @PostMapping(value = "/bill/mtng/hallMtng")
    public ResponseEntity<CommonResponse> createHallMtng(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.createHallMtng(mtngFromVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Hall meeting  correction", description = "Hall Meeting Modify")
    @PutMapping("/bill/mtng/hallMtng")
    public ResponseEntity<CommonResponse> updateHallMtng(@RequestBody MtngFromVo mtngFromVo){

    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "mtng update successfully", mtngFromService.updateHallMtng(mtngFromVo)), HttpStatus.OK);
    }

    @Operation(summary = "Hall meeting Added agenda", description = "Hall meeting Agenda Add")
    @PutMapping("/bill/mtng/hallMtng/agenda")
    public ResponseEntity<CommonResponse> addHallMtngAgenda(@RequestBody MtngFromVo mtngFromVo){
    	mtngFromService.addHallMtngAgenda(mtngFromVo);
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "agenda added successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Hall meeting List check", description = "List Inquiry.")
    @GetMapping("/bill/mtng/hallMtng")
    public ResponseEntity<CommonResponse> getHallMtngList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getHallMtngList(param)), HttpStatus.OK);
    }

    @Operation(summary = "meeting Agenda submit", description = "meeting Agenda submit")
    @PutMapping("/bill/mtng/from/submit")
    public ResponseEntity<CommonResponse> submitMtngAgenda(@RequestBody MtngFromVo mtngFromVo){
    	mtngFromService.submitMtngAgenda(mtngFromVo.getMtngId());
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "submission successful"), HttpStatus.OK);
    }


    @Operation(summary = "Hall meeting Agenda List check", description = "List Inquiry.")
    @GetMapping("/bill/mtng/hallMtng/billList")
    public ResponseEntity<CommonResponse> getHallMtngBillList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getHallMtngBillList(param)), HttpStatus.OK);
    }


    @Operation(summary = "Hall Meeting results registration", description = "meeting result registration")
    @PostMapping(value = "/bill/mtng/hallMtng/result")
    public ResponseEntity<CommonResponse> createHallMtngResult(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.updateHallMtngResult(mtngFromVo)), HttpStatus.CREATED);
    }

    @Operation(summary = "Hall Meeting results order change", description = "Meeting results order change")
    @PutMapping(value = "/bill/mtng/hallMtng/ord")
    public ResponseEntity<CommonResponse> updateHallMtngOrd(@RequestBody MtngFromVo mtngFromVo) {
        mtngFromService.updateHallMtngOrd(mtngFromVo);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "update succesful"), HttpStatus.OK);
    }

    @Operation(summary = "Delete the agenda of the plenary session", description = "Plenary Delete the agenda ")
    @PostMapping(value = "/bill/mtng/hallMtng/billDelete")
    public ResponseEntity<CommonResponse> deleteHallMtngBill(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.deleteHallMtngBill(mtngFromVo)), HttpStatus.CREATED);
    }


}