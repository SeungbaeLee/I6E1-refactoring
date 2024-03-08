package main_project_025.I6E1.domain.member.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MemberDetailResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<String> roles;
}
