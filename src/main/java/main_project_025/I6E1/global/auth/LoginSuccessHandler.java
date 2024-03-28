package main_project_025.I6E1.global.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String LOGIN_SUCCESS_EMAIL_LOG = "로그인에 성공하였습니다. 이메일 : {}";
    private static final String LOGIN_SUCCESS_ACCESS_TOKEN_LOG = "로그인에 성공하였습니다. AccessToken : {}";
    private static final String LOGIN_SUCCESS_TOKEN_EXPIRED_LOG = "발급된 AccessToken 만료 기간 : {}";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String email = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        memberRepository.findByEmail(email).ifPresent(user -> {
            user.updateRefreshToken(refreshToken);
            userRepository.saveAndFlush(user);
        });

        log.info(LOGIN_SUCCESS_EMAIL_LOG, email);
        log.info(LOGIN_SUCCESS_ACCESS_TOKEN_LOG, accessToken);
        log.info(LOGIN_SUCCESS_TOKEN_EXPIRED_LOG, accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
}
