package main_project_025.I6E1.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main_project_025.I6E1.domain.trade.entity.Trade;
import main_project_025.I6E1.global.auditable.Auditable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public void setTrade(Trade trade) {
        this.getTrades().add(trade);
        if (trade.getMember() != this) {
            trade.setMember(this);
        }
    }
}
