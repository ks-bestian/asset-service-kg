package kr.co.bestiansoft.ebillservicekg.document.controller;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
import kr.co.bestiansoft.ebillservicekg.document.service.DocumentService;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileShareVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.UserMemberAuthMappVo;
import lombok.RequiredArgsConstructor;

@Tag(name = "Document management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final EDVHelper edv;
    private final DocumentMapper documentMapper;
    

    @Operation(summary = "department Folder registration", description = "department Folder registration")
    @PostMapping("/document/dept/folders")
    public ResponseEntity<CommonResponse> insertDeptFolder(@RequestBody FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.insertDeptFolder(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "my Folder registration", description = "my Folder registration")
    @PostMapping("/document/my/folders")
    public ResponseEntity<CommonResponse> insertMyFolder(@RequestBody FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.insertMyFolder(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "Folder correction", description = "Folder modify")
    @PutMapping("/document/folders")
    public ResponseEntity<CommonResponse> updateFolder(@RequestBody FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.updateFolder(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "department Folder Full list check", description = "department Folder Full list check")
    @GetMapping("/document/dept/folders/all")
    public ResponseEntity<CommonResponse> selectDeptFolderListAll(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeptFolderListAll(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "my Folder Full list check", description = "my Folder Full list check")
    @GetMapping("/document/my/folders/all")
    public ResponseEntity<CommonResponse> selectMyFolderListAll(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectMyFolderListAll(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "department Folder inventory check", description = "department Folder inventory check")
    @GetMapping("/document/dept/folders")
    public ResponseEntity<CommonResponse> selectDeptFolderList(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeptFolderList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "my Folder inventory check", description = "my Folder inventory check")
    @GetMapping("/document/my/folders")
    public ResponseEntity<CommonResponse> selectMyFolderList(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectMyFolderList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "Retrieve subfolders within the shared folder.", description = "Retrieve subfolders within the shared folder.")
    @GetMapping("/document/share/folders")
    public ResponseEntity<CommonResponse> selectShareFolderList(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectShareFolderList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "share Folder Interior folder check", description = "share Folder Interior folder check")
    @GetMapping("/document/share/folders/content")
    public ResponseEntity<CommonResponse> selectShareFolderListByFolderId(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectShareFolderListByFolderId(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "share Folder Inner file check", description = "share Folder Inner file check")
    @GetMapping("/document/share/files/content")
    public ResponseEntity<CommonResponse> selectShareFileListByFolderId(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectShareFileListByFolderId(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "delete Folder inventory check", description = "delete Folder inventory check")
    @GetMapping("/document/trash/folders")
    public ResponseEntity<CommonResponse> selectDeleteFolderList(FolderVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeleteFolderList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "Folder/file delete", description = "Folder/file delete")
    @DeleteMapping("/document/files")
    public ResponseEntity<CommonResponse> deleteFoldersAndFiles(@RequestBody FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.deleteFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds())), HttpStatus.OK);
    }
    
    @Operation(summary = "Folder/file Restoration", description = "Folder/file Restoration")
    @PutMapping("/document/files/restore")
    public ResponseEntity<CommonResponse> restoreFoldersAndFiles(@RequestBody FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.restoreFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds())), HttpStatus.OK);
    }
    
    @Operation(summary = "Folder/file Permanent deletion", description = "Folder/file Permanent deletion")
    @DeleteMapping("/document/files/remove")
    public ResponseEntity<CommonResponse> removeFoldersAndFiles(@RequestBody FileVo vo) throws Exception {
    	documentService.removeFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds());
    	return new ResponseEntity<>(new CommonResponse(200, "OK", null), HttpStatus.OK);
    }
    
    @Operation(summary = "department file Upload", description = "department file Upload")
    @PostMapping(value = "/document/dept/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadDeptFile(FileVo vo) {
    	vo.setDeptFileYn("Y");
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.uploadFile(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "my file Upload", description = "my file Upload")
    @PostMapping(value = "/document/my/files", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonResponse> uploadMyFile(FileVo vo) {
    	vo.setDeptFileYn("N");
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.uploadFile(vo)), HttpStatus.OK);
    }
    
//    @Operation(summary = "department file bundle Upload", description = "department file bundle Upload")
//    @PostMapping(value = "/document/dept/files/group", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<CommonResponse> uploadFileGroup(FileVo vo) {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.uploadFileGroup(vo)), HttpStatus.OK);
//    }
    
//    @Operation(summary = "Folder Upload", description = "Folder Upload")
//    @PostMapping(value = "/document/dept/files/folder", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<CommonResponse> uploadFolder(FileVo vo) {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.uploadFile(vo)), HttpStatus.OK);
//    }
    
    @Operation(summary = "department file inventory check", description = "department file inventory check")
    @GetMapping("/document/dept/files")
    public ResponseEntity<CommonResponse> selectDeptFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeptFileList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "my file inventory check", description = "my file inventory check")
    @GetMapping("/document/my/files")
    public ResponseEntity<CommonResponse> selectMyFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectMyFileList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "share file inventory check", description = "share file inventory check")
    @GetMapping("/document/share/files")
    public ResponseEntity<CommonResponse> selectShareFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectShareFileList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "importance file inventory check", description = "importance file inventory check")
    @GetMapping("/document/star/files")
    public ResponseEntity<CommonResponse> selectStarFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectStarFileList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "delete file inventory check", description = "delete file inventory check")
    @GetMapping("/document/trash/files")
    public ResponseEntity<CommonResponse> selectDeleteFileList(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectDeleteFileList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "bundle file check", description = "bundle file check")
    @GetMapping("/document/dept/files/group")
    public ResponseEntity<CommonResponse> selectFileGroup(FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectFileGroup(vo)), HttpStatus.OK);
    }
    
    
    @Operation(summary = "File download", description = "File Download.")
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
    
    @Operation(summary = "file correction", description = "file correction")
    @PutMapping("/document/files")
    public ResponseEntity<CommonResponse> updateFile(@RequestBody FileVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.updateFile(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "file Or correction", description = "file Or correction")
    @PutMapping("/document/files/star")
    public ResponseEntity<CommonResponse> updateFileStar(@RequestBody FileVo vo) {
    	documentService.saveFavorite(vo);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", null), HttpStatus.OK);
    }
    
//    @Operation(summary = "department file correction(File bundle)", description = "department file correction(File bundle)")
//    @PutMapping(value = "/document/dept/files/group", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<CommonResponse> updateFileGroup(FileVo vo) throws Exception {
//    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.updateFileGroup(vo)), HttpStatus.OK);
//    }
    
    @Operation(summary = "department file movement", description = "department file movement")
    @PutMapping("/document/dept/files/move")
    public ResponseEntity<CommonResponse> moveFoldersAndFiles(@RequestBody FileVo vo) throws Exception {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.moveFoldersAndFiles(vo.getFolderIds(), vo.getFileGroupIds(), vo.getToFolderId())), HttpStatus.OK);
    }
    
    @Operation(summary = "file share", description = "file share")
    @PostMapping("/document/files/share")
    public ResponseEntity<CommonResponse> shareFile(@RequestBody FileShareVo vo) {
    	documentService.shareFile(vo);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", null), HttpStatus.OK);
    }
    
    @Operation(summary = "file share stop", description = "file share stop")
    @PostMapping("/document/files/unshare")
    public ResponseEntity<CommonResponse> unshareFile(@RequestBody FileShareVo vo) {
    	documentService.unshareFile(vo);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", null), HttpStatus.OK);
    }
    
    @Operation(summary = "file Shared target inventory", description = "file Shared target inventory")
    @GetMapping("/document/files/share")
    public ResponseEntity<CommonResponse> selectShareTargetList(FileShareVo vo) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectShareTargetList(vo)), HttpStatus.OK);
    }
    
    @Operation(summary = "file Shared target user check", description = "file Shared target user check")
    @GetMapping("/document/files/share/users")
    public ResponseEntity<CommonResponse> selectListUserMember(@RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectListUserMember(param)), HttpStatus.OK);
    }
    
    @Operation(summary = "Usage capacity check", description = "Usage capacity check")
    @GetMapping("/document/files/diskspace")
    public ResponseEntity<CommonResponse> selectUseCpct() {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectUseCpct()), HttpStatus.OK);
    }
    
    @Operation(summary = "User Folder check", description = "User Folder check")
    @GetMapping("/document/files/authmapp")
    public ResponseEntity<CommonResponse> selectListUserAuthMapp(@RequestParam HashMap<String, Object> param) {
    	return new ResponseEntity<>(new CommonResponse(200, "OK", documentService.selectListUserAuthMapp(param)), HttpStatus.OK);
    }
    
    @Operation(summary = "User Folder save", description = "User Folder save")
    @PostMapping("/document/files/authmapp")
    public ResponseEntity<CommonResponse> saveFolderAuthMapp(@RequestBody List<UserMemberAuthMappVo> list) {
    	documentService.saveFolderAuthMapp(list);
    	return new ResponseEntity<>(new CommonResponse(200, "OK", null), HttpStatus.OK);
    }
    
}