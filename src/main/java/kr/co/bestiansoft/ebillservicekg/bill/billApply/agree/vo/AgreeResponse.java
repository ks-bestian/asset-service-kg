package kr.co.bestiansoft.ebillservicekg.bill.billApply.agree.vo;

import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.test.vo.CommentsVo;
import lombok.Data;

@Data
public class AgreeResponse {

    List<AgreeVo> proposerList;
    List<EbsFileVo> fileList;
    AgreeVo agreeDetail;
    List<CommentsVo> commentList;
    ArrayNode commentLists;
}
