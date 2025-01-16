package kr.co.bestiansoft.ebillservicekg.admin.member.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.member.repository.MemberMapper;
import kr.co.bestiansoft.ebillservicekg.admin.member.service.MemberService;
import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
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
        return memberMapper.selectListMember(param);
    }

    @Override
    public MemberVo getMemberDetail(String memberId) {
        return memberMapper.selectMember(memberId);
    }

    @Override
    public MemberVo createMember(MemberVo memberVo) {
        memberVo.setRegId(new SecurityInfoUtil().getAccountId());
        memberMapper.insertMember(memberVo);
        return memberVo;
    }

    @Override
    public int updateMember(MemberVo memberVo) {
        memberVo.setModId(new SecurityInfoUtil().getAccountId());
        return memberMapper.updateMember(memberVo);
    }

    @Override
    public void deleteMember(List<String> memberId) {
        for(String id : memberId) {
            memberMapper.deleteMember(id);
        }
    }

	@Override
	public List<MemberVo> getMemberByPoly(HashMap<String, Object> param) {
		return memberMapper.selectListMemberByPoly(param);
	}
}
