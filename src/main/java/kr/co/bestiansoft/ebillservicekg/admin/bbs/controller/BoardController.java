package kr.co.bestiansoft.ebillservicekg.admin.bbs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "notice board API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "notice board List check", description = "notice board List Inquiry.")
    @GetMapping("/admin/board/{brdType}")
    public ResponseEntity<CommonResponse> getBoardList(@RequestParam HashMap<String, Object> param, @PathVariable String brdType) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", boardService.getBoardList(param, brdType)), HttpStatus.OK);
    }
    @Operation(summary = "notice board generation", description = "The bulletin board Create.")
    @PostMapping(value = "/admin/board/{brdType}")
    public ResponseEntity<CommonResponse> createBoard(@RequestBody BoardVo boardVo, @PathVariable String brdType) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", boardService.createBoard(boardVo, brdType)), HttpStatus.CREATED);
    }
    @Operation(summary = "notice board correction", description = "The bulletin board Modify.")
    @PutMapping(value = "/admin/board/{brdId}")
    public ResponseEntity<CommonResponse> updateBoard(@RequestBody BoardVo boardVo, @PathVariable Long brdId) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "Board updated successfully", boardService.updateBoard(boardVo, brdId)), HttpStatus.CREATED);
    }
    @Operation(summary = "notice board delete", description = "notice board Information Delete.")
    @DeleteMapping("/admin/board")
    public ResponseEntity<CommonResponse> deleteBoard(@RequestBody List<Long> ids) {
        boardService.deleteBoard(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Board deleted successfully"), HttpStatus.OK);
    }

    @Operation(summary = "notice board particular check", description = "notice board Details Inquiry.")
    @GetMapping("/admin/board/detail/{brdId}")
    public ResponseEntity<CommonResponse> getBoardById(@PathVariable Long brdId, @RequestParam String lang) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", boardService.getBoardById(brdId, lang)), HttpStatus.OK);
    }

    @Operation(summary = "notice board generation(file)", description = "notice board generation(file)")
    @PostMapping(value = "/admin/board/file/{brdType}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> createBoardFile(BoardVo boardVo, @PathVariable String brdType) {
    	return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Board created successfully", boardService.createBoardFile(boardVo, brdType)), HttpStatus.CREATED);
    }
    
    @Operation(summary = "notice board List(Main screen) check", description = "notice board List(Main screen)cast Inquiry.")
    @GetMapping("/admin/board_main")
    public ResponseEntity<CommonResponse> getBoardMainList() {
        return new ResponseEntity<>(new CommonResponse(200, "OK", boardService.getBoardMainList()), HttpStatus.OK);
    }

}