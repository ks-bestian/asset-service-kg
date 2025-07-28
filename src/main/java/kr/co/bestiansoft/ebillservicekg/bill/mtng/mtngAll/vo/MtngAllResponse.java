package kr.co.bestiansoft.ebillservicekg.bill.mtng.mtngAll.vo;

import java.util.List;

import lombok.Data;

@Data
public class MtngAllResponse {

    private List<MtngAllVo> boardList;
    private List<MtngAllVo> boardList2;
    private MtngAllVo boardVo;

}
