package main_project_025.I6E1.domain.commission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class CommissionDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{
        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        @NotBlank(message = "내용을 입력해주세요.")
        private String subContent;

        @NotEmpty(message = "태그를 입력해주세요.")
        private List<String> tags;//tag test

        private List<String> imageUrl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        @NotBlank(message = "내용을 입력해주세요.")
        private String subContent;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
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
    }
}
