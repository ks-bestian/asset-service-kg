package kr.co.bestiansoft.ebillservicekg.common.file.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "file API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class ComFileController {

	private final EDVHelper edv;
    private final ComFileService comFileService;

    @Operation(summary = "File download", description = "File Download.")
	@GetMapping("/com/file/down")
	public ResponseEntity<?> fileDownload(@RequestParam String fileId,HttpServletResponse response, HttpServletRequest request) throws Exception {

//    	ComFileVo fileVo = comFileService.getFile(fileId);
//		String orgFileNm = URLEncoder.encode(fileVo.getOrgFileNm(),"UTF-8") ;

		InputStream ins = edv.download(fileId);
		Resource resource = new InputStreamResource(ins);

		if (resource == null) return ResponseEntity.notFound().build();

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + orgFileNm + "\"")
				.body(resource);
	}

	@Operation(summary = "file list", description = "search file list by fileGroupId")
	@GetMapping("/com/file/{fileGroupId}")
	public ResponseEntity<List<ComFileVo>> getFileList(@PathVariable String fileGroupId) {
		List<ComFileVo> list = comFileService.getFileList(fileGroupId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/com/file/pdf")
	public ResponseEntity<?> pdfFileDownload(@RequestParam String pdfFileNm, HttpServletResponse response, HttpServletRequest request) throws Exception {

		int idx = pdfFileNm.lastIndexOf(".pdf");
		String pdfFileId;
		if(idx == -1) {
			pdfFileId = pdfFileNm;
		}
		else {
			pdfFileId = pdfFileNm.substring(0, idx);
		}
		InputStream ins = edv.download(pdfFileId);
		Resource resource = new InputStreamResource(ins);

		if (resource == null) return ResponseEntity.notFound().build();

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + orgFileNm + "\"")
				.body(resource);
	}

	@Operation(summary = "file Upload", description = "File Upload.")
	@PostMapping(value = "/com/file/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<CommonResponse> uploadFile(@RequestPart("file") MultipartFile[] file) {
		try {
			String fileId = comFileService.saveFile(file);
			ComFileVo fileVo = comFileService.getFile(fileId);

			Map<String, Object> responseData = new HashMap<>();
			responseData.put("fileId", fileId);
			responseData.put("pdfFileId", fileVo.getPdfFileId());

			return ResponseEntity.ok(new CommonResponse(200, "File uploaded successfully.", responseData));
			}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse(500, "File upload failed.", e.getMessage()));
		}
	}
}


