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
}
