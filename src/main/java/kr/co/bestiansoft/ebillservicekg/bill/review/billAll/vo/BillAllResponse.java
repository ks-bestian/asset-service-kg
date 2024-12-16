package kr.co.bestiansoft.ebillservicekg.bill.review.billAll.vo;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class BillAllResponse {

    private List<BillAllVo> boardList;
    private List<BillAllVo> boardList2;
    private BillAllVo boardVo;
    
}
