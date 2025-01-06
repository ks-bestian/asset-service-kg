package kr.co.bestiansoft.ebillservicekg.common.file.controller;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import lombok.RequiredArgsConstructor;

@Api(tags = "파일 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ComFileController {

	private final EDVHelper edv;
    private final ComFileService comFileService;

    @ApiOperation(value = "파일다운로드", notes = "파일을 다운로드한다.")
	@GetMapping("/com/file/down/{fileId}")
	public ResponseEntity<?> fileDownload(@PathVariable String fileId,HttpServletResponse response, HttpServletRequest request) throws Exception {

    	ComFileVo fileVo = comFileService.getFile(fileId);
		String orgFileNm = URLEncoder.encode(fileVo.getOrgFileNm(),"UTF-8") ;

		InputStream ins = edv.download(fileVo.getFileId());
		Resource resource = new InputStreamResource(ins);

		if (resource == null) return ResponseEntity.notFound().build();

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + orgFileNm + "\"")
				.body(resource);
	}

	@ApiOperation(value = "file list", notes = "search file list by fileGroupId")
	@GetMapping("/com/file/{fileGroupId}")
	public ResponseEntity<List<ComFileVo>> getFileList(@PathVariable String fileGroupId) {
		List<ComFileVo> list = comFileService.getFileList(fileGroupId);
		return ResponseEntity.ok(list);
	}
}