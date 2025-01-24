package kr.co.bestiansoft.ebillservicekg.admin.bbs.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class BoardVo extends ComDefaultVO {

	private Long brdId;
    private String brdSj;
    private String brdType;
    private String brdCn;
    private String regDtNm;
    private Long notiInqCnt = 0L;
    private String pupYn;
    private String pupBgDt;
    private String pupEdDt;
    private Long ord;
    private String fileGroupId;
    private Long num;
    private String regNm;
    private String regDate;
    private String importantYn;
    private String hiddenYn;

    private List<BoardVo> delList;
    private MultipartFile[] files;
    
}
