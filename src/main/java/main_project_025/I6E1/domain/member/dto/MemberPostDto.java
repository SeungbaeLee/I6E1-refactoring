package main_project_025.I6E1.domain.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberPostDto {

    @NonNull
    @Pattern(regexp = "^[a-zA-Z0-9+.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NonNull
    @Pattern(regexp = ".{8,}")
    private String password;
    @NonNull
    @Pattern(regexp = ".{2,}")
    private String nickname;
    private List<String> roles;
}
