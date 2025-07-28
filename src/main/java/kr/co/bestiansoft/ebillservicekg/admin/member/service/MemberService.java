package kr.co.bestiansoft.ebillservicekg.admin.member.service;

import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;

public interface MemberService {

    List<MemberVo> getMemberList(HashMap<String, Object> param);
    MemberVo getMemberDetail(String memberId);
    MemberVo createMember(MemberVo memberVo);
    int updateMember(MemberVo memberVo);
    void deleteMember(List<String> memberId);
    List<MemberVo> getMemberByPoly(HashMap<String, Object> param);
    String resetPswd(HashMap<String, Object> param);
}
