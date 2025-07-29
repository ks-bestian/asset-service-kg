package kr.co.bestiansoft.ebillservicekg.asset.faq.repository;

import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaqMapper {

    int insertFaq(List<FaqVo> faqVoList);
    List<FaqVo> getFaqList(String eqpmntId);
    void deleteFaq(String eqpmntId);
    void deleteFaqById(String faqId);
}
