package kr.co.bestiansoft.ebillservicekg.admin.member.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.repository.CcofMapper;
import kr.co.bestiansoft.ebillservicekg.admin.member.repository.MemberMapper;
import kr.co.bestiansoft.ebillservicekg.admin.member.service.MemberService;
import kr.co.bestiansoft.ebillservicekg.admin.member.vo.MemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.PasswordUtill;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.login.vo.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final CcofMapper ccofMapper;

    /**
     * Retrieves a list of members based on the specified parameters.
     *
     * @param param a HashMap containing search parameters to filter members
     * @return a List of MemberVo objects that match the search criteria
     */
    @Override
    public List<MemberVo> getMemberList(HashMap<String, Object> param) {
        return memberMapper.selectListMember(param);
    }

    /**
     * Retrieves the details of a specific member based on the provided member ID.
     *
     * @param memberId the unique identifier of the member whose details are to be retrieved
     * @return a MemberVo object containing the details of the specified member
     */
    @Override
    public MemberVo getMemberDetail(String memberId) {
        return memberMapper.selectMember(memberId);
    }

    /**
     * Creates a new member and saves it to the database. It also associates the member
     * with the specified committee codes and saves the user details for each committee.
     *
     * @param memberVo the MemberVo object containing the details of the new member to be created
     * @return the created MemberVo object with updated details after being saved to the database
     */
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

    /**
     * Updates the information associated with a specific member and handles updates
     * for the member's related committee data. It deletes the existing committee data
     * for the member and inserts the new data provided in the member details.
     *
     * @param memberVo the MemberVo object containing updated information
     *                 about the member, including committee data and relevant fields
     * @return an integer representing the number of rows affected in the database
     *         after the update
     */
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

    /**
     * Deletes multiple members and their associated committee data based on the provided list of member IDs.
     * Iterates through the list of member IDs, removing each member's information from the member database and
     * associated committee details from the committee database.
     *
     * @param memberId a list of unique identifiers for the members to be deleted
     */
    @Override
    public void deleteMember(List<String> memberId) {
        for(String id : memberId) {
            memberMapper.deleteMember(id);
            ccofMapper.deleteCcof(id);
        }
    }

    /**
     * Retrieves a list of members based on the specified search parameters related to political information.
     *
     * @param param a HashMap containing search parameters to filter members by political criteria
     * @return a List of MemberVo objects that match the provided search criteria
     */
	@Override
	public List<MemberVo> getMemberByPoly(HashMap<String, Object> param) {
		return memberMapper.selectListMemberByPoly(param);
	}

    /**
     * Resets the password of a user based on the provided parameters, generates a new password,
     * hashes it, updates the user's password in the database, and returns the new password.
     *
     * @param param a HashMap containing keys and values required to update the user's password,
     *              including user identification details
     * @return the newly generated password as a String
     */
    @Override
    public String resetPswd(HashMap<String, Object> param) {
        String pswd = PasswordUtill.generatePassword();
        param.put("userPassword", LoginRequest.getSha256(pswd));
        memberMapper.updatePswd(param);
        return pswd;
    }
}
