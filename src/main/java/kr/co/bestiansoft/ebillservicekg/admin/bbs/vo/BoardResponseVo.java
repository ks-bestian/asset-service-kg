package kr.co.bestiansoft.ebillservicekg.admin.bbs.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class BoardResponseVo extends ComDefaultVO {

    private List<BoardVo> fileList;
    private List<BoardVo> boardList;
    private BoardVo detailVo;
    
    
}
