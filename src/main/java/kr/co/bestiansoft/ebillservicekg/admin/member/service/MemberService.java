package kr.co.bestiansoft.ebillservicekg.admin.member.service;

import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;

import java.util.HashMap;
import java.util.List;

public interface MemberService {

    List<MemberVo> getMemberList(HashMap<String, Object> param);
    MemberVo getMemberDetail(String memberId);
    MemberVo createMember(MemberVo memberVo);
    int updateMember(MemberVo memberVo);
    void deleteMember(List<String> memberId);
    List<MemberVo> getMemberByPoly(HashMap<String, Object> param);
}
