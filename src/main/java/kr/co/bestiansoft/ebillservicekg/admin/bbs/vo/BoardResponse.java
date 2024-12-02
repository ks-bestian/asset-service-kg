package kr.co.bestiansoft.ebillservicekg.admin.bbs.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class BoardResponse {

    private List<BoardVo> boardList;
    private List<BoardVo> boardList2;
    private BoardVo boardVo;
    
}
