package main_project_025.I6E1.domain.commission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommissionPostDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "내용을 입력해주세요.")
    private String subContent;

    @NotEmpty(message = "태그를 입력해주세요.")
    private List<String> tags;
}
