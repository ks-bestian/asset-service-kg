package kr.co.bestiansoft.ebillservicekg.asset.faq.service;

import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqVo;

import java.util.List;

public interface FaqService {
   void createFaq(List<FaqVo> faqVoList, String eqpmntId);
   List<FaqVo> getFaqList(String eqpmntId);
   void updateFaq(List<FaqVo> faqVoList, String eqpmntId);
   void deleteFaq(String eqpmntId);
   void deleteFaqById(List<String> ids);
}
