package kr.co.bestiansoft.ebillservicekg.admin.comCode.vo;

import lombok.Data;

import java.util.List;

@Data
public class ComCodeResponse {
    private List<ComCodeVo> comCodeList;
    private ComCodeVo comCodeVo;

    private List<ComCodeDetailVo> comCodeDetailList;
    private ComCodeDetailVo comCodeDetailVo;

}
