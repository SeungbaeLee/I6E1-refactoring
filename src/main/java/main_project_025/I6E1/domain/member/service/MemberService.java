package main_project_025.I6E1.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main_project_025.I6E1.domain.member.dto.MemberDetailResponseDto;
import main_project_025.I6E1.domain.member.dto.MemberPostDto;
import main_project_025.I6E1.global.auth.userdetails.AuthMember;
import main_project_025.I6E1.global.auth.utils.CustomAuthorityUtils;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.member.repository.MemberRepository;
import main_project_025.I6E1.global.exception.BusinessException;
import main_project_025.I6E1.global.exception.ExceptionCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public MemberDetailResponseDto create(MemberPostDto memberPostDto){
        String encryptedPassword = passwordEncoder.encode(memberPostDto.getPassword());
        Member member = Member.builder()
                .email(memberPostDto.getEmail())
                .password(encryptedPassword)
                .nickname(memberPostDto.getNickname())
                .roles(memberPostDto.getRoles())
                .build();
        memberRepository.save(member);
        return MemberDetailResponseDto.fromEntity(member);
    }

    public Member findById(Long memberId){
        return findVerifyMemberById(memberId);
    }

    public Member findByEmail(String email){
        return findVerifyMemberByEmail(email);
    }

    public Boolean checkEmail(String email){
        Optional<Member> optionalMembers = memberRepository.findByEmail(email);

        return optionalMembers.isEmpty();
    }

    public Boolean checkNickname(String nickname){
        Optional<Member> optionalMembers = memberRepository.findByNickname(nickname);

        return optionalMembers.isEmpty();
    }

    public Member findVerifyMemberById(Long memberId){
        Optional<Member> optionalMembers = memberRepository.findById(memberId);

        if(optionalMembers.isEmpty()){
            throw new BusinessException(ExceptionCode.USER_EXISTS);
        }

        return optionalMembers.get();
    }

    public Member findVerifyMemberByEmail(String email){
        Optional<Member> optionalMembers = memberRepository.findByEmail(email);

        if(optionalMembers.isEmpty()) {
            throw new BusinessException(ExceptionCode.USER_NOT_FOUND);
        }

        return optionalMembers.get();
    }

    public static String getAuthMember() {
        AuthMember authMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = authMember.getUsername();
        return userEmail;
    }
}
