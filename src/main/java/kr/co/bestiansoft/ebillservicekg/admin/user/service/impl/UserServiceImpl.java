package kr.co.bestiansoft.ebillservicekg.admin.user.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.admin.ccof.repository.CcofMapper;
import kr.co.bestiansoft.ebillservicekg.admin.member.repository.MemberMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.repository.UserMapper;
import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
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
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final CcofMapper ccofMapper;
    private final MemberMapper memberMapper;

    /**
     * Retrieves a list of users based on the specified parameters.
     *
     * @param param a HashMap containing the parameters for filtering the user list
     * @return a list of UserVo objects that match the specified criteria
     */
    @Override
    public List<UserVo> getUserList(HashMap<String, Object> param) {
        return userMapper.selectListUser(param);
    }

    /**
     * Retrieves the detailed information of a user based on the specified sequence number.
     *
     * @param seq the unique identifier (sequence number) of the user
     * @return a {@code UserVo} object containing the detailed information of the user
     */
    @Override
    public UserVo getUserDetail(Long seq) {
        return userMapper.selectUser(seq);
    }

    /**
     * Retrieves detailed information of a user, along with their membership details,
     * based on the provided user ID.
     *
     * @param userId the unique identifier of the user
     * @return a {@code UserMemberVo} object containing the user's details and membership information,
     *         or {@code null} if the provided user ID is null or empty
     */
    @Override
	public UserMemberVo getUserMemberDetail(String userId) {
        if(userId == null || userId.isEmpty()) { return null;}
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return userMapper.userDetail(map);
    }

    /**
     * Creates a new user and inserts the user's information into the database,
     * including associated departments. It also hashes the user's password
     * and sets the registration ID for the user.
     *
     * @param userVo the {@code UserVo} object containing the user's details
     *               to be created, including user information and associated department codes
     * @return the {@code UserVo} object of the newly created user,
     *         including any additional information updated during the creation process
     */
    @Override
    public UserVo createUser(UserVo userVo) {
        String regId = new SecurityInfoUtil().getAccountId();
        userVo.setUserPassword(LoginRequest.getSha256(userVo.getUserPassword()));
        userVo.setRegId(regId);

        int ord = 1;

        List<String> ccofCds = userVo.getCcofCds();

        userMapper.insertUser(userVo);

        for (String deptCd : ccofCds) {
            userVo.setDeptCd(deptCd);
            userVo.setOrd(ord);
            if (deptCd != null) {
                ccofMapper.insertCcofInUser(userVo);
            }
            ord++;
        }

        return userVo;
    }

    /**
     * Updates the details of an existing user, including modifying user information,
     * setting updated department associations, and hashing the user's password.
     *
     * @param userVo the {@code UserVo} object containing the updated user details,
     *               including user information and associated department codes
     * @return an integer indicating the result of the update process (0 if successful)
     */
    @Override
    public int updateUser(UserVo userVo) {
        String modId = new SecurityInfoUtil().getAccountId();

        userVo.setModId(modId);
        userVo.setUserPassword(LoginRequest.getSha256(userVo.getUserPassword()));
        userMapper.updateUser(userVo);

        ccofMapper.deleteCcof(userVo.getUserId());
        int ord = 1;

        List<String> ccofCds = userVo.getCcofCds();

        for (String deptCd : ccofCds) {
            userVo.setDeptCd(deptCd);
            userVo.setOrd(ord);

            if(deptCd != null) {
                ccofMapper.insertCcofInUser(userVo);
            }
            ord++;
        }

        return 0;
    }

    /**
     * Deletes multiple users and their associated department information from the database.
     *
     * @param seq a list of unique user identifiers (IDs) to delete
     */
    @Override
    public void deleteUser(List<String> seq) {
        for (String id : seq) {
            userMapper.deleteUser(id);
            ccofMapper.deleteCcof(id);
        }
    }

    /**
     * Retrieves a list of users associated with specific department(s) based on the given parameters.
     *
     * @param param a HashMap containing the parameters for filtering the users by department
     *              (e.g., department*/
    @Override
    public List<UserVo> getUserByDept(HashMap<String, Object> param) {
        return userMapper.selectListUserByDept(param);
    }

    /**
     * Resets the user's password by generating a new password, hashing it,
     * and updating it in the database for the specified user.
     *
     * @param param a {@code HashMap} containing key-value pairs where keys are parameter names
     *              and values are the associated data. It must include the user's details to update the password.
     * @return a {@code String} representing the newly generated raw password before hashing.
     */
    @Override
    public String resetPswd(HashMap<String, Object> param) {
        String pswd  = PasswordUtill.generatePassword();

        param.put("userPassword", LoginRequest.getSha256(pswd));
        userMapper.updatePswd(param);
        return pswd;
    }

    /**
     * Updates the user's password by hashing the provided password and
     * updating the corresponding entry in the database based on the user type.
     *
     * @param param a {@code HashMap} containing key-value pairs where:
     *              - "userPassword" is the raw password string to be hashed and updated
     *              - Other necessary user details to identify the user and determine its type
     */
    @Override
    public void updatePswd(HashMap<String, Object> param) {
        UserMemberVo user = userMapper.userDetail(param);
        String pswd = LoginRequest.getSha256((String)param.get("userPassword"));
        param.put("userPassword", pswd);
        if(user.getType().equals("member")) {
            memberMapper.updatePswd(param);
        }else if(user.getType().equals("user")) {
            userMapper.updatePswd(param);
        }
    }

    /**
     * Updates the job information for a user in the database based on the provided parameters.
     *
     * @param param a {@code HashMap} containing key-value pairs where:
     *              - Keys are parameter names specifying the job details to be updated
     *              - Values are the corresponding data for those parameters
     */
    @Override
    public void updateJob(HashMap<String, Object> param) {
        userMapper.updateJob(param);
    }
}
