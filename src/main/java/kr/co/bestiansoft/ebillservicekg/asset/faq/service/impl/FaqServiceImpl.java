package kr.co.bestiansoft.ebillservicekg.asset.faq.service.impl;

import kr.co.bestiansoft.ebillservicekg.asset.bzenty.service.BzentyService;
import kr.co.bestiansoft.ebillservicekg.asset.faq.repository.FaqMapper;
import kr.co.bestiansoft.ebillservicekg.asset.faq.service.FaqService;
import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqResponse;
import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FaqServiceImpl implements FaqService {

    private final FaqMapper faqMapper;
    private final BzentyService bzentyService;

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
    public FaqResponse getFaqsAndBzenty(String eqpmntId, String bzentyId) {
        FaqResponse result = new FaqResponse();
        result.setBzentyVo(bzentyService.getBzentyDetail(bzentyId));
        result.setFaqVoList(faqMapper.getFaqList(eqpmntId));
        return result;
    }
    
    @Transactional
    @Override
    public void updateFaq(List<FaqVo> faqVoList, String eqpmntId) {
    	List<FaqVo> insertList = new ArrayList<FaqVo>();
    	List<String> incomingFaqIds = new ArrayList<>();
    	
        for(int i = 0; i < faqVoList.size(); i++) {
        	FaqVo faqVo = faqVoList.get(i);
        	String faqId = faqVo.getFaqId();
        	if(faqId!=null&&!faqId.equals("")) {
                faqVo.setEqpmntId(eqpmntId);
                faqVo.setSeq(i+1);
                faqVo.setMdfrId(new SecurityInfoUtil().getAccountId());
        		faqMapper.updateFaq(faqVo);
        		incomingFaqIds.add(faqId);
        	} else {
                faqId = StringUtil.getFaqUUID();
                faqVo.setFaqId(faqId);
                faqVo.setSeq(i+1);
                faqVo.setEqpmntId(eqpmntId);
                faqVo.setRgtrId(new SecurityInfoUtil().getAccountId());
                insertList.add(faqVo);
                incomingFaqIds.add(faqId);
        	}


        }
        
        // ⛔ 삭제 대상: 기존 DB에 있으나 요청에 포함되지 않은 FAQ
        if (!incomingFaqIds.isEmpty()) {
            faqMapper.deleteFaqsNotIn(eqpmntId, incomingFaqIds);
        } else {
            // 전달된 리스트가 비어있을 경우: 모두 삭제
            faqMapper.deleteFaq(eqpmntId);
        }
        
        if (!insertList.isEmpty()) {
        	faqMapper.insertFaq(insertList);
        }

    }

    @Transactional
    @Override
    public void deleteFaq(String eqpmntId) {
    	faqMapper.deleteFaq(eqpmntId);
    }

    @Transactional
    @Override
    public void deleteFaqById(List<String> ids) {
        for(String id : ids) {
        	faqMapper.deleteFaqById(id);
        }
    }
}
