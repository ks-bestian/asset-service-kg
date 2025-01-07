package kr.co.bestiansoft.ebillservicekg.document.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class DeptFolderVo extends ComDefaultVO {

	private Long folderId;
    private String folderNm;
    private Long upperFolderId;
    private String upperFolderNm;
    private String deptCd;
    private String delYn;
    
    private String title;
    private String regNm;    
}
