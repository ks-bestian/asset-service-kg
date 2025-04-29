package kr.co.bestiansoft.ebillservicekg.admin.member.repository;

import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemberMapper {
    List<MemberVo> selectListMember(HashMap<String, Object> param);
    MemberVo selectMember(String memberId);
    int insertMember(MemberVo memberVo);
    int updateMember(MemberVo memberVo);
    void deleteMember(String memberId);
	List<MemberVo> selectListMemberByPoly(HashMap<String, Object> param);
    int resetPswd(HashMap<String, Object> param);
}
