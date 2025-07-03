package kr.co.bestiansoft.ebillservicekg.admin.bbs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.service.BoardService;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "notice board API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "notice board List check", notes = "notice board List Inquiry.")
    @GetMapping("/admin/board/{brdType}")
    public ResponseEntity<CommonResponse> getBoardList(@RequestParam HashMap<String, Object> param, @PathVariable String brdType) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", boardService.getBoardList(param, brdType)), HttpStatus.OK);
    }
    @ApiOperation(value = "notice board generation", notes = "The bulletin board Create.")
    @PostMapping(value = "/admin/board/{brdType}")
    public ResponseEntity<CommonResponse> createBoard(@RequestBody BoardVo boardVo, @PathVariable String brdType) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", boardService.createBoard(boardVo, brdType)), HttpStatus.CREATED);
    }
    @ApiOperation(value = "notice board correction", notes = "The bulletin board Modify.")
    @PutMapping(value = "/admin/board/{brdId}")
    public ResponseEntity<CommonResponse> updateBoard(@RequestBody BoardVo boardVo, @PathVariable Long brdId) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "Board updated successfully", boardService.updateBoard(boardVo, brdId)), HttpStatus.CREATED);
    }
    @ApiOperation(value = "notice board delete", notes = "notice board Information Delete.")
    @DeleteMapping("/admin/board")
    public ResponseEntity<CommonResponse> deleteBoard(@RequestBody List<Long> ids) {
        boardService.deleteBoard(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Board deleted successfully"), HttpStatus.OK);
    }

    @ApiOperation(value = "notice board particular check", notes = "notice board Details Inquiry.")
    @GetMapping("/admin/board/detail/{brdId}")
    public ResponseEntity<CommonResponse> getBoardById(@PathVariable Long brdId, @RequestParam String lang) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", boardService.getBoardById(brdId, lang)), HttpStatus.OK);
    }

    @ApiOperation(value = "notice board generation(file)", notes = "notice board generation(file)")
    @PostMapping(value = "/admin/board/file/{brdType}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBoardFile(BoardVo boardVo, @PathVariable String brdType) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", boardService.createBoardFile(boardVo, brdType)), HttpStatus.CREATED);
    }
    
    @ApiOperation(value = "notice board List(Main screen) check", notes = "notice board List(Main screen)cast Inquiry.")
    @GetMapping("/admin/board_main")
    public ResponseEntity<CommonResponse> getBoardMainList() {
        return new ResponseEntity<>(new CommonResponse(200, "OK", boardService.getBoardMainList()), HttpStatus.OK);
    }

}