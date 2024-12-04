package kr.co.bestiansoft.ebillservicekg.bill.apply.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.service.BoardService;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.bill.apply.service.ApplyService;
import kr.co.bestiansoft.ebillservicekg.bill.apply.vo.ApplyVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Api(tags = "안건제출 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ApplyController {

    private final ApplyService applyService;

//    @ApiOperation(value = "안건제출", notes = "안건을 생성한다")
//    @PostMapping("/bill/apply")
//    public ResponseEntity<CommonResponse> getBoardList(@RequestBody ApplyVo applyVo) {
//        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", boardService.createBoard(boardVo, brdType)), HttpStatus.CREATED);
//    }

}