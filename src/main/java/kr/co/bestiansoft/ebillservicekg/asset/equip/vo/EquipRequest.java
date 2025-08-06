package kr.co.bestiansoft.ebillservicekg.asset.equip.vo;

import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqVo;
import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class EquipRequest extends ComDefaultVO {
    private String eqpmntId;
    private String eqpmntCd;
    private String eqpmntSe;
    private String eqpmntNm;
    private String expln;
    private String bzentyId;
    private String tkcgDeptId;

    private String thumbNailImg;
    private List<String> detailImg;
    private MultipartFile[] files;
    private MultipartFile[] dtlImg;
    private MultipartFile[] thumbnail;
    
    private String mnulVoList;      // JSON 문자열
    private String installVoList;
    private String faqVoList;
    
    private String thumbnailId;    // 예: dtlImgKeep
    private String filesId;    // 예: thumbnailKeep
    private String dtlImgId;    // 예: thumbnailKeep
    private String instlFileId;    // 예: thumbnailKeep
    private String videoFileId;    // 예: thumbnailKeep

    private List<String> thumbnailKeep;    // 예: dtlImgKeep
    private List<String> thumbnailDelete;  // 예: dtlImgDelete
    private List<String> filesKeep;    // 예: thumbnailKeep
    private List<String> filesDelete;  // 예: thumbnailDelete
    private List<String> dtlImgKeep;    // 예: thumbnailKeep
    private List<String> dtlImgDelete;  // 예: thumbnailDelete
    private List<String> instlFileKeep;    // 예: thumbnailKeep
    private List<String> instlFileDelete;  // 예: thumbnailDelete
    private List<String> videoFileKeep;    // 예: thumbnailKeep
    private List<String> videoFileDelete;  // 예: thumbnailDelete

}
