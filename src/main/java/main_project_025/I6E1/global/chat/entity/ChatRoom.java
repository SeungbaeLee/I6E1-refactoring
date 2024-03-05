package main_project_025.I6E1.global.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import main_project_025.I6E1.domain.member.entity.Member;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "MEMBER_ID")
    private Member user;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "MEMBER_ID")
    private Member author;

    @Column(name = "user_back")
    private Long userBack;
    @Column(name = "author_back")
    private Long authorBack;

    @Builder
    public ChatRoom(Long roomId){
        this.roomId = roomId;
    }

    @Column(name = "used")
    private boolean used = Boolean.FALSE;
}
