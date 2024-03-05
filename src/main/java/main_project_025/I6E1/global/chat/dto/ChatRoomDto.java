package main_project_025.I6E1.global.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main_project_025.I6E1.domain.member.entity.Member;

public class ChatRoomDto {
    @Getter @Setter
    @NoArgsConstructor
    public static class Response{
        private Long roomId;
        private Member author;
    }
}
