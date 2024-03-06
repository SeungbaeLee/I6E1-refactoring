package main_project_025.I6E1.domain.commission.dto;

import lombok.Builder;
import lombok.Getter;
import main_project_025.I6E1.domain.commission.entity.Commission;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommissionResponseDto {
    private long commissionId;
    private String title;
    private String content;
    private String subContent;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String memberName;
    private String memberEmail;
    private List<String> tags;
    private List<String> imageUrl;

    @Builder
    public CommissionResponseDto(long commissionId, String title, String content, String subContent, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String memberName, String memberEmail, List<String> tags, List<String> imageUrl) {
        this.commissionId = commissionId;
        this.title = title;
        this.content = content;
        this.subContent = subContent;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.tags = tags;
        this.imageUrl = imageUrl;
    }

    public static CommissionResponseDto fromEntity(Commission commission) {
        List<String> tagNames = commission.getTags().stream()
                .map(commissionTag -> commissionTag.getTag().getTagName())
                .collect(Collectors.toList());

        return CommissionResponseDto.builder()
                .commissionId(commission.getCommissionId())
                .title(commission.getTitle())
                .content(commission.getContent())
                .subContent(commission.getSubContent())
                .viewCount(commission.getViewCount())
                .createdAt(commission.getCreatedAt())
                .modifiedAt(commission.getModifiedAt())
                .memberName(commission.getMember().getNickname())
                .memberEmail(commission.getMember().getEmail())
                .tags(tagNames)
                .imageUrl(commission.getImageUrl())
                .build();
    }

    public static List<CommissionResponseDto> fromEntityList(List<Commission> commissionList) {
        return commissionList.stream()
                .map(CommissionResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
