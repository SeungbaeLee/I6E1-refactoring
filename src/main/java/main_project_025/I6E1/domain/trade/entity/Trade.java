package main_project_025.I6E1.domain.trade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main_project_025.I6E1.domain.commission.entity.Commission;
import main_project_025.I6E1.global.auditable.Auditable;
import main_project_025.I6E1.domain.member.entity.Member;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@NoArgsConstructor
@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE Trade SET deleted = true WHERE trade_id = ?")
@Where(clause = "deleted = false")//where절에 반드시 포함되는 조건 설정 -> deleted = false -> 지워지지 않은 로우 -> 하지만 nativeQuery에는 적용 X
public class Trade extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;

    @ManyToOne
    @JoinColumn(name = "commission_id")
    private Commission commission;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    private String content;

    @NotNull
    private String authorEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.Waiting_Acceptance;

    public void setMember(Member member) {
        this.member = member;
        if (!member.getTrades().contains(this)) {
            member.getTrades().add(this);
        }
    }

    public void setCommission(Commission commission) {//C
        this.commission = commission;
        if (!commission.getTrades().contains(this)) {
            commission.getTrades().add(this);
        }
    }

}
