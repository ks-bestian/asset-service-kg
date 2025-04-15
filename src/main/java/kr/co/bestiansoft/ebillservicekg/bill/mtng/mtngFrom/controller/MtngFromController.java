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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.service.MtngFromService;
import kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo.MtngFromVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "회의 예정 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class MtngFromController {

    private final MtngFromService mtngFromService;

    @ApiOperation(value = "회의 예정 리스트 조회", notes = "리스트를 조회한다.")
    @GetMapping("/bill/mtng/from")
    public ResponseEntity<CommonResponse> getMtngFromList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getMtngFromList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 예정 상세 조회", notes = "상세를 조회한다.")
    @GetMapping("/bill/mtng/from/detail/{mtngId}")
    public ResponseEntity<CommonResponse> getMtngFromById(@PathVariable Long mtngId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getMtngFromById(mtngId, param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 예정 등록", notes = "회의 예정 등록")
    @PostMapping(value = "/bill/mtng/from")
    public ResponseEntity<CommonResponse> createMtngFrom(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.createMtngFrom(mtngFromVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "회의 예정 - 의원 리스트 조회", notes = "회의 예정 - 의원 리스트를 조회한다.")
    @GetMapping("/bill/mtng/from/member")
    public ResponseEntity<CommonResponse> getMemberList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.getMemberList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 예정 - 부서(위원회) 리스트 조회", notes = "회의 예정 - 부서(위원회) 리스트를 조회한다.")
    @GetMapping("/bill/mtng/from/dept")
    public ResponseEntity<CommonResponse> getDeptList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.getDeptList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 예정 - 회의 취소", notes = "회의 취소")
    @DeleteMapping("/bill/mtng/from")
    public ResponseEntity<CommonResponse> deleteMtngFrom(@RequestBody List<Long> mtngIds) {
    	mtngFromService.deleteMtng(mtngIds);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "meeting deleted successfully"), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 예정 - 위원회회의 해당안건 조회", notes = "회의 예정 - 해당안건을 조회한다.")
    @GetMapping("/bill/mtng/from/selectListMtngBill")
    public ResponseEntity<CommonResponse> selectListMtngBill(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.selectListMtngBill(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 예정 - 본회의 해당안건 조회", notes = "회의 예정 - 본회의 해당안건을 조회한다.")
    @GetMapping("/bill/mtng/from/selectListMainMtngBill")
    public ResponseEntity<CommonResponse> selectListMainMtngBill(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", mtngFromService.selectListMainMtngBill(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 예정 수정", notes = "예정된 회의를 수정한다")
    @PutMapping("/bill/mtng/from/update")
    public ResponseEntity<CommonResponse> updateMtngBill(@RequestBody MtngFromVo mtngFromVo){
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "mtng update successfully", mtngFromService.updateMtngBill(mtngFromVo)), HttpStatus.OK);
    }



    /*Hall meeting*/

    @ApiOperation(value = "Hall 회의등록", notes = "회의 예정 등록")
    @PostMapping(value = "/bill/mtng/hallMtng")
    public ResponseEntity<CommonResponse> createHallMtng(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.createHallMtng(mtngFromVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Hall회의  수정", notes = "Hall 회의를 수정한다")
    @PutMapping("/bill/mtng/hallMtng")
    public ResponseEntity<CommonResponse> updateHallMtng(@RequestBody MtngFromVo mtngFromVo){
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "mtng update successfully", mtngFromService.updateHallMtng(mtngFromVo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Hall회의 안건추가", notes = "Hall 회의 안건을 추가한다")
    @PutMapping("/bill/mtng/hallMtng/agenda")
    public ResponseEntity<CommonResponse> addHallMtngAgenda(@RequestBody MtngFromVo mtngFromVo){
    	mtngFromService.addHallMtngAgenda(mtngFromVo);
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "agenda added successfully"), HttpStatus.OK);
    }

    @ApiOperation(value = "Hall 회의 리스트 조회", notes = "리스트를 조회한다.")
    @GetMapping("/bill/mtng/hallMtng")
    public ResponseEntity<CommonResponse> getHallMtngList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getHallMtngList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "회의 안건 부의", notes = "회의 안건을 부의한다")
    @PutMapping("/bill/mtng/from/submit")
    public ResponseEntity<CommonResponse> submitMtngAgenda(@RequestBody MtngFromVo mtngFromVo){
    	mtngFromService.submitMtngAgenda(mtngFromVo.getMtngId());
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "submission successful"), HttpStatus.OK);
    }


    @ApiOperation(value = "Hall 회의 안건 리스트 조회", notes = "리스트를 조회한다.")
    @GetMapping("/bill/mtng/hallMtng/billList")
    public ResponseEntity<CommonResponse> getHallMtngBillList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mtngFromService.getHallMtngBillList(param)), HttpStatus.OK);
    }


    @ApiOperation(value = "Hall 회의결과 등록", notes = "회의 결과 등록")
    @PostMapping(value = "/bill/mtng/hallMtng/result")
    public ResponseEntity<CommonResponse> createHallMtngResult(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.updateHallMtngResult(mtngFromVo)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "본회의안건삭제", notes = "본회의 안건삭제 ")
    @PostMapping(value = "/bill/mtng/hallMtng/billDelete")
    public ResponseEntity<CommonResponse> deleteHallMtngBill(@RequestBody MtngFromVo mtngFromVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Mtng created successfully", mtngFromService.deleteHallMtngBill(mtngFromVo)), HttpStatus.CREATED);
    }


}