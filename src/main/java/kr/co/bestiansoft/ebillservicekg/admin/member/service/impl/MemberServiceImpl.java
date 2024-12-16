package kr.co.bestiansoft.ebillservicekg.admin.member.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.member.repository.MemberMapper;
import kr.co.bestiansoft.ebillservicekg.admin.member.service.MemberService;
import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public List<MemberVo> getMemberList(HashMap<String, Object> param) {
        return memberMapper.getMemberList(param);
    }

    @Override
    public MemberVo getMemberDetail(String memberId) {
        return memberMapper.getMemberDetail(memberId);
    }

    @Override
    public MemberVo createMember(MemberVo memberVo) {
        memberMapper.insertMember(memberVo);
        return memberVo;
    }

    @Override
    public int updateMember(MemberVo memberVo) {
        return memberMapper.updateMember(memberVo);
    }

    @Override
    public void deleteMember(String memberId) {
        memberMapper.deleteMember(memberId);
    }
}
