package main_project_025.I6E1.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main_project_025.I6E1.global.auditable.Auditable;
import main_project_025.I6E1.domain.member.entity.Member;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@NoArgsConstructor
@Getter @Setter
@SQLDelete(sql = "UPDATE review SET deleted = true WHERE review_id=?")
@Where(clause = "deleted=false")
@Entity
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @Column
    private long commissionId;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @ManyToOne(targetEntity = Member.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}
