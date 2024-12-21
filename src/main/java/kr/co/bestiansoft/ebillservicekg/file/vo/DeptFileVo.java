package kr.co.bestiansoft.ebillservicekg.file.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class DeptFileVo extends ComDefaultVO {

	private String fileId;
	private Long folderId;
	private String fileTitle;
	private String fileNm;
	private String folderNm;
	private Long fileSize;
	private String fileType;
	private String fileGroupId;
	private String fileGroupNm;
	private String deptCd;
	private String thumbnail;
	private String delYn;
	private String groupYn;
	private String edvHashStr;
	private String edvPath;
	
	private String folderYn = "N";
	
	private MultipartFile[] files;
	private MultipartFile[] addFiles;
	private MultipartFile thumbnailImage;
	
	private String userId;
	private String favoriteYn;
	
	private String title;
	private String regNm;
	
	private List<String> delFileIds;
	
	private List<Long> folderIds;
	private List<String> fileGroupIds;
	
	private Long toFolderId;
}
