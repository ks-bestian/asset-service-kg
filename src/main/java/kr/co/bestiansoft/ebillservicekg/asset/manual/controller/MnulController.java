package kr.co.bestiansoft.ebillservicekg.asset.manual.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.bestiansoft.ebillservicekg.asset.manual.service.MnulService;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "메뉴얼 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mnul")
public class MnulController {

    private final MnulService mnulService;
    
    private final ResourceHttpRequestHandler videoRequestHandler;

    @Operation(summary = "메뉴얼 삭제", description = "메뉴얼 삭제한다.")
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteMnulById(@RequestBody List<String> ids) {
        mnulService.deleteMnulById(ids);
        return new ResponseEntity<>(new CommonResponse(HttpStatus.OK.value(), "OK", "Manual deleted"), HttpStatus.OK);
    }

    @GetMapping(value="/preview/{eqpmntId}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> streamVideo(@PathVariable String eqpmntId) throws IOException {
        Resource video = mnulService.loadVideoAsResource(eqpmntId);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .body(video);
    }

    @Operation(summary = "장비 비디오 메뉴얼 목록 조회", description = "장비 비디오 메뉴얼 목록 조회한다.")
    @GetMapping("/video/list")
    public ResponseEntity<CommonResponse> getVideoList(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mnulService.getMnulListByEqpmntId(eqpmntId, "video")), HttpStatus.OK);
    }

    @GetMapping(value="/video/{mnlId}", produces = "video/mp4")
    public ResponseEntity<Resource> videoMnl(@PathVariable String mnlId) throws IOException {
        Resource video = mnulService.videoMnlAsResource(mnlId);
        
        
        
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .body(video);
    }

    @Operation(summary = "장비 파일 메뉴얼 목록 조회", description = "장비 파일 메뉴얼 목록 조회한다.")
    @GetMapping("/file")
    public ResponseEntity<CommonResponse> getFileMnul(@RequestParam String eqpmntId) {
        return new ResponseEntity<>(new CommonResponse(200, "OK", mnulService.getMnulListByEqpmntId(eqpmntId, "file")), HttpStatus.OK);
    }

    @Operation(summary = "File download", description = "File Download.")
    @GetMapping("/asset/file/down")
    public ResponseEntity<?> fileDownload(@RequestParam String fileId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Resource resource = mnulService.downloadFile(fileId);

        if (resource == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/asset/file/view")
    public ResponseEntity<?> fileViewer(@RequestParam String pdfFileNm, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Resource resource = mnulService.downloadFile(pdfFileNm);

        if (resource == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }


}
