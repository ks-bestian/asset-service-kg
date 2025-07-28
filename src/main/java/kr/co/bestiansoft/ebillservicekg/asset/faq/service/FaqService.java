package kr.co.bestiansoft.ebillservicekg.asset.faq.service;

import java.util.List;

import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqVo;

public interface FaqService {
   void createFaq(List<FaqVo> faqVoList, String eqpmntId);
   List<FaqVo> getFaqList(String eqpmntId);
   void deleteFaq(String eqpmntId);
   void deleteFaqById(List<String> ids);
}
