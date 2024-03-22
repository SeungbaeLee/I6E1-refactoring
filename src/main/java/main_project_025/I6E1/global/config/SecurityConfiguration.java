package main_project_025.I6E1.global.config;

import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.global.auth.filter.JwtAuthenticationFilter;
import main_project_025.I6E1.global.auth.filter.JwtVerificationFilter;
import main_project_025.I6E1.global.auth.handler.MemberAuthenticationFailureHandler;
import main_project_025.I6E1.global.auth.handler.MemberAuthenticationSuccessHandler;
import main_project_025.I6E1.global.auth.jwt.JwtTokenizer;
import main_project_025.I6E1.global.auth.utils.CustomAuthorityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(cs -> cs.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(f -> f.disable())
                .httpBasic(h -> h.disable());

        http.authorizeHttpRequests((authorizeHttpRequest) ->
                authorizeHttpRequest
                        .requestMatchers("/members").permitAll()
                        .requestMatchers(HttpMethod.POST,"/commission/**").hasRole("AUTHOR")
                        .requestMatchers(HttpMethod.PATCH,"/commission/**").hasRole("AUTHOR")
                        .requestMatchers(HttpMethod.DELETE,"/commission/**").hasRole("AUTHOR")

                        .requestMatchers(HttpMethod.POST,"/review/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH,"/review/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE,"/review/**").hasRole("USER")

                        .requestMatchers(HttpMethod.POST,"/trade/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH,"/trade/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/trade/**").authenticated()
                        .requestMatchers(HttpMethod.GET,"/trade/{tradeId}").authenticated()

        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder)throws Exception{
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter =
                    new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);

            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter =
                    new JwtVerificationFilter(jwtTokenizer, authorityUtils);


            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}
