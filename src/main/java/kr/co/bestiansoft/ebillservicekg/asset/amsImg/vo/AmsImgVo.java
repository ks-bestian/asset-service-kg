package kr.co.bestiansoft.ebillservicekg.asset.amsImg.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class AmsImgVo extends ComDefaultVO {
    private String imgId;
    private String eqpmntId;
    private String instlId;
    private String imgSe;
    private String filePath;
    private String fileNm;
    private String orgnlFileNm;
    private String fileExtn;
    private Long fileSz;

}
