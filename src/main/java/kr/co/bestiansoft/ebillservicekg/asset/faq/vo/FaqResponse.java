package kr.co.bestiansoft.ebillservicekg.asset.faq.vo;

import kr.co.bestiansoft.ebillservicekg.asset.bzenty.vo.BzentyVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FaqResponse {
    private BzentyVo bzentyVo;
    private List<FaqVo> faqVoList;
}
