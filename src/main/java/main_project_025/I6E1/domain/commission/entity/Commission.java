package main_project_025.I6E1.domain.commission.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.tag.entity.CommissionTag;
import main_project_025.I6E1.domain.trade.entity.Trade;
import main_project_025.I6E1.global.auditable.Auditable;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE commission SET deleted = true WHERE commission_id=?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "commission")
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "commission")
    private List<CommissionTag> tags = new ArrayList<>();

    @ElementCollection
    private List<String> imageUrl;

    @Builder
    public Commission(long commissionId, String title, String content, String subContent, int viewCount, Member member, List<Trade> trades, List<CommissionTag> tags, List<String> imageUrl) {
        this.commissionId = commissionId;
        this.title = title;
        this.content = content;
        this.subContent = subContent;
        this.viewCount = viewCount;
        this.member = member;
        this.trades = trades;
        this.tags = tags;
        this.imageUrl = imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTags(List<CommissionTag> tags) {
        this.tags = tags;
    }

    public void updateViewCount() {
        this.viewCount += 1;
    }

    public void updateCommission(String title, String content, String subContent) {
        this.title = title;
        this.content = content;
        this.subContent = subContent;
    }
}
