package kr.co.bestiansoft.ebillservicekg.admin.bbs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.service.BoardService;
import kr.co.bestiansoft.ebillservicekg.admin.bbs.vo.BoardVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.file.vo.DeptFileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "게시판 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "게시판 리스트 조회", notes = "게시판 리스트를 조회한다.")
    @GetMapping("/admin/board/{brdType}")
    public ResponseEntity<CommonResponse> getBoardList(@RequestParam HashMap<String, Object> param, @PathVariable String brdType) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", boardService.getBoardList(param, brdType)), HttpStatus.OK);
    }
    @ApiOperation(value = "게시판 생성", notes = "게시판을 생성한다.")
    @PostMapping(value = "/admin/board/{brdType}")
    public ResponseEntity<CommonResponse> createBoard(@RequestBody BoardVo boardVo, @PathVariable String brdType) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", boardService.createBoard(boardVo, brdType)), HttpStatus.CREATED);
    }
    @ApiOperation(value = "게시판 수정", notes = "게시판을 수정한다.")
    @PutMapping(value = "/admin/board/{brdId}")
    public ResponseEntity<CommonResponse> updateBoard(@RequestBody BoardVo boardVo, @PathVariable Long brdId) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "Board updated successfully", boardService.updateBoard(boardVo, brdId)), HttpStatus.CREATED);
    }
    @ApiOperation(value = "게시판 삭제", notes = "게시판 정보를 삭제한다.")
    @DeleteMapping("/admin/board")
    public ResponseEntity<CommonResponse> deleteBoard(@RequestBody List<Long> ids) {
        boardService.deleteBoard(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Board deleted successfully"), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 상세 조회", notes = "게시판 상세를 조회한다.")
    @GetMapping("/admin/board/detail/{brdId}")
    public ResponseEntity<CommonResponse> getBoardById(@PathVariable Long brdId) {
        BoardVo response = boardService.getBoardById(brdId);
        return new ResponseEntity<>(new CommonResponse(200, "OK", boardService.getBoardById(brdId)), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 생성(파일)", notes = "게시판 생성(파일)")
    @PostMapping(value = "/admin/board/file/{brdType}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBoardFile(BoardVo boardVo, @PathVariable String brdType) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", boardService.createBoardFile(boardVo, brdType)), HttpStatus.CREATED);
    }

}