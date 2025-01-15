package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngFrom.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class MtngFromResponse {

    private List<MtngFromVo> boardList;
    private List<MtngFromVo> boardList2;
    private MtngFromVo boardVo;
    
}
