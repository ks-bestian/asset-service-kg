package kr.co.bestiansoft.ebillservicekg.document.controller;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
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
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
import kr.co.bestiansoft.ebillservicekg.document.service.DocumentService;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileShareVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;
import lombok.RequiredArgsConstructor;

@Api(tags = "문서관리 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final EDVHelper edv;
    private final DocumentMapper documentMapper;
    

    @ApiOperation(value = "부서 폴더 등록", notes = "부서 폴더 등록")
    @PostMapping("/document/dept/folders")
    public ResponseEntity<CommonResponse> insertDeptFolder(@RequestBody FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.insertDeptFolder(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "내 폴더 등록", notes = "내 폴더 등록")
    @PostMapping("/document/my/folders")
    public ResponseEntity<CommonResponse> insertMyFolder(@RequestBody FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.insertMyFolder(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "폴더 수정", notes = "폴더 수정")
    @PutMapping("/document/folders")
    public ResponseEntity<CommonResponse> updateFolder(@RequestBody FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.updateFolder(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 폴더 전체목록 조회", notes = "부서 폴더 전체목록 조회")
    @GetMapping("/document/dept/folders/all")
    public ResponseEntity<CommonResponse> selectDeptFolderListAll(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeptFolderListAll(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "내 폴더 전체목록 조회", notes = "내 폴더 전체목록 조회")
    @GetMapping("/document/my/folders/all")
    public ResponseEntity<CommonResponse> selectMyFolderListAll(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectMyFolderListAll(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 폴더 목록 조회", notes = "부서 폴더 목록 조회")
    @GetMapping("/document/dept/folders")
    public ResponseEntity<CommonResponse> selectDeptFolderList(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeptFolderList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "내 폴더 목록 조회", notes = "내 폴더 목록 조회")
    @GetMapping("/document/my/folders")
    public ResponseEntity<CommonResponse> selectMyFolderList(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectMyFolderList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "삭제 폴더 목록 조회", notes = "삭제 폴더 목록 조회")
    @GetMapping("/document/trash/folders")
    public ResponseEntity<CommonResponse> selectDeleteFolderList(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeleteFolderList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "폴더/파일 삭제", notes = "폴더/파일 삭제")
    @DeleteMapping("/document/files")
    public ResponseEntity<CommonResponse> deleteFoldersAndFiles(@RequestBody FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.deleteFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds())), HttpStatus.OK);
    }
    
    @ApiOperation(value = "폴더/파일 복원", notes = "폴더/파일 복원")
    @PutMapping("/document/files/restore")
    public ResponseEntity<CommonResponse> restoreFoldersAndFiles(@RequestBody FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.restoreFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds())), HttpStatus.OK);
    }
    
    @ApiOperation(value = "폴더/파일 영구삭제", notes = "폴더/파일 영구삭제")
    @DeleteMapping("/document/files/remove")
    public ResponseEntity<CommonResponse> removeFoldersAndFiles(@RequestBody FileVo vo) throws Exception {
    	documentService.removeFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds());
    	return new ResponseEntity<>(new CommonResponse(200, "OK", null), HttpStatus.OK);
    }
    
    @ApiOperation(value = "부서 파일 업로드", notes = "부서 파일 업로드")
    @PostMapping(value = "/document/dept/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadDeptFile(FileVo vo) {
    	vo.setDeptFileYn("Y");
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.uploadFile(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "내 파일 업로드", notes = "내 파일 업로드")
    @PostMapping(value = "/document/my/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadMyFile(FileVo vo) {
    	vo.setDeptFileYn("N");
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.uploadFile(vo)), HttpStatus.OK);
    }
    
//    @ApiOperation(value = "부서 파일 묶음 업로드", notes = "부서 파일 묶음 업로드")
//    @PostMapping(value = "/document/dept/files/group", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<CommonResponse> uploadFileGroup(FileVo vo) {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.uploadFileGroup(vo)), HttpStatus.OK);
//    }
    
//    @ApiOperation(value = "폴더 업로드", notes = "폴더 업로드")
//    @PostMapping(value = "/document/dept/files/folder", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<CommonResponse> uploadFolder(FileVo vo) {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.uploadFile(vo)), HttpStatus.OK);
//    }
    
    @ApiOperation(value = "부서 파일 목록 조회", notes = "부서 파일 목록 조회")
    @GetMapping("/document/dept/files")
    public ResponseEntity<CommonResponse> selectDeptFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeptFileList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "내 파일 목록 조회", notes = "내 파일 목록 조회")
    @GetMapping("/document/my/files")
    public ResponseEntity<CommonResponse> selectMyFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectMyFileList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "중요 파일 목록 조회", notes = "중요 파일 목록 조회")
    @GetMapping("/document/star/files")
    public ResponseEntity<CommonResponse> selectStarFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectStarFileList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "삭제 파일 목록 조회", notes = "삭제 파일 목록 조회")
    @GetMapping("/document/trash/files")
    public ResponseEntity<CommonResponse> selectDeleteFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeleteFileList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "묶음 파일 조회", notes = "묶음 파일 조회")
    @GetMapping("/document/dept/files/group")
    public ResponseEntity<CommonResponse> selectFileGroup(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectFileGroup(vo)), HttpStatus.OK);
    }
    
    
    @ApiOperation(value = "파일다운로드", notes = "파일을 다운로드한다.")
    @GetMapping("/document/files/download")
    public ResponseEntity<?> fileDownLoad(@RequestParam String fileId, HttpServletResponse response, HttpServletRequest request) throws Exception{
    	
    	FileVo fileVo = documentMapper.selectFile(fileId);
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
    
    @ApiOperation(value = "파일 수정", notes = "파일 수정")
    @PutMapping("/document/files")
    public ResponseEntity<CommonResponse> updateFile(@RequestBody FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.updateFile(vo)), HttpStatus.OK);
    }
    
//    @ApiOperation(value = "부서 파일 수정(파일묶음)", notes = "부서 파일 수정(파일묶음)")
//    @PutMapping(value = "/document/dept/files/group", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<CommonResponse> updateFileGroup(FileVo vo) throws Exception {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.updateFileGroup(vo)), HttpStatus.OK);
//    }
    
    @ApiOperation(value = "부서 파일 이동", notes = "부서 파일 이동")
    @PutMapping("/document/dept/files/move")
    public ResponseEntity<CommonResponse> moveFoldersAndFiles(@RequestBody FileVo vo) throws Exception {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.moveFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds(), vo.getToFolderId())), HttpStatus.OK);
    }
    
    @ApiOperation(value = "파일 공유", notes = "파일 공유")
    @PutMapping("/document/files/share")
    public ResponseEntity<CommonResponse> shareFile(@RequestBody FileShareVo vo) {
    	documentService.shareFile(vo);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", null), HttpStatus.OK);
    }
    
    @ApiOperation(value = "파일 공유대상 목록", notes = "파일 공유대상 목록")
    @GetMapping("/document/files/share")
    public ResponseEntity<CommonResponse> selectShareTargetList(FileShareVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectShareTargetList(vo)), HttpStatus.OK);
    }
    
    @ApiOperation(value = "파일 공유대상 사용자 조회", notes = "파일 공유대상 사용자 조회")
    @GetMapping("/document/files/share/users")
    public ResponseEntity<CommonResponse> selectListUserMember(@RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectListUserMember(param)), HttpStatus.OK);
    }
    
}