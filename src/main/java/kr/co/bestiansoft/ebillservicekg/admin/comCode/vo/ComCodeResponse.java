package kr.co.bestiansoft.ebillservicekg.admin.comCode.vo;

import java.util.List;

import lombok.Data;

@Data
public class ComCodeResponse {
    private List<ComCodeVo> comCodeList;
    private ComCodeVo comCodeVo;

    private List<ComCodeDetailVo> comCodeDetailList;
    private ComCodeDetailVo comCodeDetailVo;

}
