package main_project_025.I6E1.global.auth.controller;


import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.global.auth.jwt.JwtTokenizer;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private MemberService memberService;
    private JwtTokenizer jwtTokenizer;

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> resetRefreshToken(@PathVariable("member-id") Long memberId) {
        Member member = memberService.findById(memberId);
        String accessToken = delegateAccessToken(member);
        return ResponseEntity.ok().header("Authorization", "Bearer " +accessToken)
                .build();
    }

    private String delegateAccessToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", member.getMemberId());
        claims.put("name", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodeSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        return jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodeSecretKey);
    }
}