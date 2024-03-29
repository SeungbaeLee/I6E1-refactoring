package main_project_025.I6E1.domain.tag.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @NotEmpty
    @Column(unique = true)
    private String tagName;

    @OneToMany(mappedBy = "tag")
    private List<CommissionTag> commissions = new ArrayList<>();

    @Builder
    public Tag(Long tagId, String tagName, List<CommissionTag> commissions) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.commissions = commissions;
    }

    public Tag(String tagName) {
        this.tagName = tagName;
        this.commissions = new ArrayList<>();
    }
}
