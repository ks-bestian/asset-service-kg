package kr.co.bestiansoft.ebillservicekg.asset.faq.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.bestiansoft.ebillservicekg.asset.faq.vo.FaqVo;

@Mapper
public interface FaqMapper {

    int insertFaq(List<FaqVo> faqVoList);
    List<FaqVo> getFaqList(String eqpmntId);
    int updateFaq(FaqVo faqVo);
    void deleteFaq(String eqpmntId);
    void deleteFaqById(String faqId);
    void deleteFaqsNotIn(@Param("eqpmntId") String eqpmntId, @Param("faqIds") List<String> faqIds);
}
