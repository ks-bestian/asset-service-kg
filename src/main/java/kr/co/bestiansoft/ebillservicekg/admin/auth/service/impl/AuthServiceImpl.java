package kr.co.bestiansoft.ebillservicekg.admin.auth.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.auth.repository.AuthMapper;
import kr.co.bestiansoft.ebillservicekg.admin.auth.service.AuthService;
import kr.co.bestiansoft.ebillservicekg.admin.auth.vo.AuthVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;

    /**
     * Retrieves a list of authorization entities based on the provided parameters.
     *
     * @param param a HashMap containing the parameters for filtering the authorization list
     * @return a list of AuthVo objects representing the authorization entities
     */
    @Override
    public List<AuthVo> getAuthList(HashMap<String, Object> param) {
        return authMapper.selectListAuth(param);
    }

    /**
     * Retrieves the authorization details for the given authorization ID.
     *
     * @param authId the ID of the authorization entity to be retrieved
     * @return an AuthVo object containing the details of the authorization entity,
     *         or null if no authorization entity is found with the specified ID
     */
    @Override
    public AuthVo getAuthDetail(Long authId) {
        return authMapper.selectAuth(authId);
    }

    /**
     * Creates a new authorization entry using the provided AuthVo object.
     *
     * @param authVo the AuthVo object containing the details of the authorization to be created
     * @return the created AuthVo object
     */
    @Override
    public AuthVo createAuth(AuthVo authVo) {
        authMapper.insertAuth(authVo);
        return authVo;
    }

    /**
     * Updates the authorization information for the given AuthVo object.
     *
     * @param authVo the AuthVo object containing the updated authorization details
     * @return the number of rows affected by the update operation
     */
    @Override
    public int updateAuth(AuthVo authVo) {
        return authMapper.updateAuth(authVo);
    }

    /**
     * Deletes authorization entries corresponding to the provided list of IDs.
     *
     * @param ids a list of authorization IDs to be deleted
     */
    @Override
    public void deleteAuth(List<Long> ids) {
        for(Long id : ids) {
            authMapper.deleteAuth(id);
        }
    }
}
