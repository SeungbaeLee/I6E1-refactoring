package main_project_025.I6E1.domain.member.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {

    private Long memberId;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime createdAt;
}
