package kr.co.bestiansoft.ebillservicekg.admin.member.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.repository.CcofMapper;
import kr.co.bestiansoft.ebillservicekg.admin.member.repository.MemberMapper;
import kr.co.bestiansoft.ebillservicekg.admin.member.service.MemberService;
import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.repository.MenuMapper;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.PasswordUtill;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginRequest;
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
    private final CcofMapper ccofMapper;

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
        String regId = new SecurityInfoUtil().getAccountId();
        memberVo.setRegId(regId);
        memberVo.setUserPassword(LoginRequest.getSha256(memberVo.getUserPassword()));
        List<String> cmitCds = memberVo.getCmitList();

        memberMapper.insertMember(memberVo);

        int ord = 1;

        for(String cmitCd : cmitCds) {
            UserVo user = new UserVo();
            user.setUserId(memberVo.getMemberId());
            user.setDeptCd(cmitCd);
            user.setOrd(ord);
            user.setRegId(regId);

            ccofMapper.insertCcofInUser(user);
            ord++;
        }
        return memberVo;
    }

    @Override
    public int updateMember(MemberVo memberVo) {
        String regId = new SecurityInfoUtil().getAccountId();
        memberVo.setModId(regId);

        ccofMapper.deleteCcof(memberVo.getMemberId());

        List<String> cmitCds = memberVo.getCmitList();

        int ord = 1;
        for(String cmitCd : cmitCds) {
            UserVo user = new UserVo();
            user.setUserId(memberVo.getMemberId());
            user.setDeptCd(cmitCd);
            user.setOrd(ord);
            user.setRegId(regId);

            ccofMapper.insertCcofInUser(user);
            ord++;
        }
        return memberMapper.updateMember(memberVo);
    }

    @Override
    public void deleteMember(List<String> memberId) {
        for(String id : memberId) {
            memberMapper.deleteMember(id);
            ccofMapper.deleteCcof(id);
        }
    }

	@Override
	public List<MemberVo> getMemberByPoly(HashMap<String, Object> param) {
		return memberMapper.selectListMemberByPoly(param);
	}

    @Override
    public String resetPswd(HashMap<String, Object> param) {
        String pswd = PasswordUtill.generatePassword();
        param.put("userPassword", LoginRequest.getSha256(pswd));
        memberMapper.updatePswd(param);
        return pswd;
    }
}
