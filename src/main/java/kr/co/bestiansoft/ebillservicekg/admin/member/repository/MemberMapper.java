package kr.co.bestiansoft.ebillservicekg.admin.member.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;

@Mapper
public interface MemberMapper {
    List<MemberVo> selectListMember(HashMap<String, Object> param);
    MemberVo selectMember(String memberId);
    int insertMember(MemberVo memberVo);
    int updateMember(MemberVo memberVo);
    void deleteMember(String memberId);
	List<MemberVo> selectListMemberByPoly(HashMap<String, Object> param);
    int updatePswd(HashMap<String, Object> param);
}
