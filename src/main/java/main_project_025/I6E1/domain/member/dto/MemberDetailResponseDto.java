package main_project_025.I6E1.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import main_project_025.I6E1.domain.member.entity.Member;

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

    @Builder
    public MemberDetailResponseDto(Long memberId, String email, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt, List<String> roles) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.roles = roles;
    }

    public static MemberDetailResponseDto fromEntity(Member member) {
        return MemberDetailResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .roles(member.getRoles())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }
}
