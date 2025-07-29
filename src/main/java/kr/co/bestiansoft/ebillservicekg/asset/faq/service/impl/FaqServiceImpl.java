package kr.co.bestiansoft.ebillservicekg.asset.faq.service.impl;

import kr.co.bestiansoft.ebillservicekg.asset.faq.repository.FaqMapper;
import kr.co.bestiansoft.ebillservicekg.asset.faq.service.FaqService;
import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FaqServiceImpl implements FaqService {

    private final FaqMapper faqMapper;

    @Transactional
    @Override
    public void createFaq(List<FaqVo> faqVoList, String eqpmntId) {

        for(FaqVo faqVo: faqVoList) {
            String faqId = StringUtil.getFaqUUID();
            faqVo.setFaqId(faqId);
            faqVo.setEqpmntId(eqpmntId);
            faqVo.setRgtrId(new SecurityInfoUtil().getAccountId());

        }
        faqMapper.insertFaq(faqVoList);

    }

    @Override
    public List<FaqVo> getFaqList(String eqpmntId) {
        return faqMapper.getFaqList(eqpmntId);
    }

    @Override
    public void deleteFaq(String eqpmntId) {
    	faqMapper.deleteFaq(eqpmntId);
    }

    @Override
    public void deleteFaqById(List<String> ids) {
        for(String id : ids) {
        	faqMapper.deleteFaqById(id);
        }
    }
}
