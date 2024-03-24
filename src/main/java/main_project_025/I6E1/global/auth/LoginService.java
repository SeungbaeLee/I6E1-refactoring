package main_project_025.I6E1.global.auth;

import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.member.repository.MemberRepository;
import main_project_025.I6E1.global.exception.ExceptionCode;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(ExceptionCode.USER_NOT_FOUND.getMessage()));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRoles().toString())
                .build();
    }
}
