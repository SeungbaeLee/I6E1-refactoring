package main_project_025.I6E1.domain.commission.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main_project_025.I6E1.global.auditable.Auditable;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.tag.entity.CommissionTag;
import main_project_025.I6E1.domain.trade.entity.Trade;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Setter @Getter
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE commission SET deleted = true WHERE commission_id=?")
@NoArgsConstructor
public class Commission extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commissionId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "mediumtext", nullable = false)
    private String content;

    @Column(columnDefinition = "mediumtext", nullable = false)
    private String subContent;

    @Column(nullable = false)
    private int viewCount = 0;

    @ManyToOne(targetEntity = Member.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "commission")//cascade 추가??
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "commission")//tag 매핑
    private List<CommissionTag> tags = new ArrayList<>();

    @ElementCollection
    private List<String> imageUrl;

    public void setTrade(Trade trade) {
        this.getTrades().add(trade);
        if (trade.getCommission() != this) {
            trade.setCommission(this);
        }
    }
}
