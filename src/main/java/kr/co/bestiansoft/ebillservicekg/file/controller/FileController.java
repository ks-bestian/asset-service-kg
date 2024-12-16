package kr.co.bestiansoft.ebillservicekg.file.controller;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.file.repository.FileMapper;
import kr.co.bestiansoft.ebillservicekg.file.service.FileService;
import kr.co.bestiansoft.ebillservicekg.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.file.vo.DeptFileVo;
import kr.co.bestiansoft.ebillservicekg.file.vo.DeptFolderVo;
import lombok.RequiredArgsConstructor;

@Api(tags = "문서관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileService fileService;
    private final EDVHelper edv;
    private final FileMapper fileMapper;
    

    @ApiOperation(value = "부서 폴더 등록", notes = "부서 폴더 등록")
    @PostMapping("/file/dept/folders")
    public ResponseEntity<CommonResponse> insertDeptFolder(@RequestBody DeptFolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.insertDeptFolder(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 폴더 수정", notes = "부서 폴더 수정")
    @PutMapping("/file/dept/folders")
    public ResponseEntity<CommonResponse> updateDeptFolder(@RequestBody DeptFolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.updateDeptFolder(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 폴더 전체목록 조회", notes = "부서 폴더 전체목록 조회")
    @GetMapping("/file/dept/folders/all")
    public ResponseEntity<CommonResponse> selectDeptFolderListAll(DeptFolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.selectDeptFolderListAll(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 폴더 목록 조회", notes = "부서 폴더 목록 조회")
    @GetMapping("/file/dept/folders")
    public ResponseEntity<CommonResponse> selectDeptFolderList(DeptFolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.selectDeptFolderList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 폴더/파일 삭제", notes = "부서 폴더/파일 삭제")
    @DeleteMapping("/file/dept/files")
    public ResponseEntity<CommonResponse> deleteDeptFoldersAndFiles(@RequestBody DeptFileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.deleteDeptFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds())), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 파일 업로드", notes = "부서 파일 업로드")
    @PostMapping(value = "/file/dept/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadDeptFile(DeptFileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.uploadDeptFile(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 파일 묶음 업로드", notes = "부서 파일 묶음 업로드")
    @PostMapping(value = "/file/dept/files/group", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadDeptFileGroup(DeptFileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.uploadDeptFileGroup(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "폴더 업로드", notes = "폴더 업로드")
    @PostMapping(value = "/file/dept/files/folder", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadDeptFolder(DeptFileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.uploadDeptFile(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 파일 목록 조회", notes = "부서 파일 목록 조회")
    @GetMapping("/file/dept/files")
    public ResponseEntity<CommonResponse> selectDeptFileList(DeptFileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.selectDeptFileList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "묶음 파일 조회", notes = "묶음 파일 조회")
    @GetMapping("/file/dept/files/group")
    public ResponseEntity<CommonResponse> selectDeptFileGroup(DeptFileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.selectDeptFileGroup(vo)), HttpStatus.OK);
    }
    
    
    @ApiOperation(value = "파일다운로드", notes = "파일을 다운로드한다.")
    @GetMapping("/file/dept/files/download")
    public ResponseEntity<?> fileDownLoad(@RequestParam String fileId, HttpServletResponse response, HttpServletRequest request) throws Exception{
    	
    	DeptFileVo fileVo = fileMapper.selectDeptFile(fileId);
    	String filename = fileVo.getFileTitle();
    	String filetype = fileVo.getFileType();
    	if(!StringUtil.isNullOrEmpty(filetype)) {
    		filename += "." + filetype;
    	}
    	filename = URLEncoder.encode(filename, "UTF-8");
    	InputStream is = edv.download(fileId);
    	
    	Resource resource = new InputStreamResource(is);
    	
    	if (resource == null) return ResponseEntity.notFound().build();
    	
    	return ResponseEntity.ok()
    					.contentType(MediaType.APPLICATION_OCTET_STREAM)
    					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
    					.body(resource);
    }
    
    @ApiOperation(value = "부서 파일 수정", notes = "부서 파일 수정")
    @PutMapping("/file/dept/files")
    public ResponseEntity<CommonResponse> updateDeptFile(@RequestBody DeptFileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.updateDeptFile(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 파일 수정(파일묶음)", notes = "부서 파일 수정(파일묶음)")
    @PutMapping(value = "/file/dept/files/group", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> updateDeptFileGroup(DeptFileVo vo) throws Exception {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.updateDeptFileGroup(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 파일 이동", notes = "부서 파일 이동")
    @PutMapping("/file/dept/files/move")
    public ResponseEntity<CommonResponse> moveDeptFoldersAndFiles(@RequestBody DeptFileVo vo) throws Exception {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", fileService.moveDeptFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds(), vo.getToFolderId())), HttpStatus.OK);
    }
}