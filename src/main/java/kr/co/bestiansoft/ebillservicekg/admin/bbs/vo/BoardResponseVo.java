package kr.co.bestiansoft.ebillservicekg.admin.bbs.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.DefaultVO;
import lombok.Data;

@Data
public class BoardResponseVo extends DefaultVO {

    private List<BoardVo> fileList;
    private List<BoardVo> boardList;
    private BoardVo detailVo;
    
    
}
