package kr.co.bestiansoft.ebillservicekg.user.login.service.impl;

import kr.co.bestiansoft.ebillservicekg.user.department.repository.DepartmentRepository;
import kr.co.bestiansoft.ebillservicekg.user.department.repository.entity.DepartmentEntity;
import kr.co.bestiansoft.ebillservicekg.user.employee.repository.EmployeeRepository;
import kr.co.bestiansoft.ebillservicekg.user.employee.repository.entity.EmployeeEntity;
import kr.co.bestiansoft.ebillservicekg.user.login.domain.Account;
import kr.co.bestiansoft.ebillservicekg.user.member.repository.MemberRepository;
import kr.co.bestiansoft.ebillservicekg.user.member.repository.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final EmployeeRepository empRepository;
    private final DepartmentRepository deptRepository;

    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public Account loadUserByUsername(final String username) throws UsernameNotFoundException{

        Optional<MemberEntity> member = memberRepository.findByMemberId(username);
        Optional<EmployeeEntity> employee = empRepository.findById(username);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        AtomicReference<Account> account = new AtomicReference<>();

        member.ifPresent(m -> {
            String deptCd = m.getDeptId();
            Optional<DepartmentEntity> dept = deptRepository.findById(deptCd);

            account.set(new Account(m, dept.orElse(null), grantedAuthorities));
        });

        employee.ifPresent(e -> {
            String deptCd = e.getDeptId();
            Optional<DepartmentEntity> dept = deptRepository.findById(deptCd);

            account.set(new Account(e, dept.orElse(null), grantedAuthorities));
        });

        if(!member.isPresent() && !employee.isPresent()) {
            throw new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다.");
        }

        return account.get();
    }

}