package kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo;

import java.util.List;

import lombok.Data;

@Data
public class BillMngResponse {

    private List<BillMngVo> boardList;
    private List<BillMngVo> boardList2;
    private BillMngVo boardVo;
    
}
