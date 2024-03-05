package main_project_025.I6E1.global.chat.mapper;

import main_project_025.I6E1.global.chat.dto.ChatRoomDto;
import main_project_025.I6E1.global.chat.entity.ChatRoom;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {
    ChatRoomDto.Response chatRoomToResponse(ChatRoom room);
    List<ChatRoomDto.Response> chatRoomToResponse(List<ChatRoom> rooms);
}
