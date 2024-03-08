package main_project_025.I6E1.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import main_project_025.I6E1.domain.trade.entity.Trade;
import main_project_025.I6E1.global.auditable.Auditable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Trade> trades = new ArrayList<>();


    @Builder
    public Member(Long memberId, String email, String password, String nickname, List<String> roles, List<Trade> trades) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.roles = roles;
        this.trades = trades;
    }
}
