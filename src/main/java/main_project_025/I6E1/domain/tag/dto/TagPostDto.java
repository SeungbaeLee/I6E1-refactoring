package main_project_025.I6E1.domain.tag.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TagPostDto {

    @NotEmpty
    private String tagName;
}
