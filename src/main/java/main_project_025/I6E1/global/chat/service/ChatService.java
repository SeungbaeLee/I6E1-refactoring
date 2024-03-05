package main_project_025.I6E1.global.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import main_project_025.I6E1.global.auth.userdetails.AuthMember;
import main_project_025.I6E1.global.chat.entity.ChatRoom;
import main_project_025.I6E1.global.chat.entity.Message;
import main_project_025.I6E1.global.chat.repository.ChatRoomRepository;
import main_project_025.I6E1.global.chat.repository.MessageRepository;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.member.repository.MemberRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Getter
@AllArgsConstructor
@Service
public class ChatService {
    private ObjectMapper objectMapper;
    private ChatRoomRepository chatRoomRepository;
    private MessageRepository messageRepository;
    private MemberRepository memberRepository;


    public ChatRoom createRoom(long authorId){

        // member 가져오기
        AuthMember loginMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long memberId = loginMember.getMemberId();
        Member user = getMemberFromId(memberId);

        Member author = getMemberFromId(authorId);

        ChatRoom chatRoom = new ChatRoom();
        ChatRoom findRoom = chatRoomRepository.findByUserBackAndAuthorBack(user.getMemberId(), author.getMemberId());
        if (findRoom != null){
            chatRoom = findRoom;
        }

        chatRoom.setUser(user);
        chatRoom.setAuthor(author);
        chatRoom.setUserBack(user.getMemberId());
        chatRoom.setAuthorBack(author.getMemberId());

        return chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> findChatRooms(){
        AuthMember loginMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = getMemberFromId(loginMember.getMemberId());
        return chatRoomRepository.findByUserAndUsedOrAuthorAndUsed(member,true, member,true);
    }

    public void deleteChatRoom(Long roomId){
        AuthMember loginMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long memberId = loginMember.getMemberId();
        Member user = getMemberFromId(memberId);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        if (user.getRoles().contains("USER")) {
            chatRoom.setUser(null);
        } else if (user.getRoles().contains("AUTHOR")) {
            chatRoom.setAuthor(null);
        }

        chatRoomRepository.save(chatRoom);
    }

    public void useChatRoom(Long roomId){
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
        if (chatRoom.isUsed() != true){
            chatRoom.setUsed(true);
            chatRoomRepository.save(chatRoom);
        }
    }

    private Member getMemberFromId(long memberId){
        return memberRepository.findById(memberId).get();
    }

    public Message saveMessage(Message message){
        
        AuthMember loginMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long memberId = loginMember.getMemberId();
        Member user = getMemberFromId(memberId);
        message.setMember(user);

        return messageRepository.save(message);
    }

    public List<Message> findMessageList(long roomId){
        return messageRepository.findByRoomId(roomId);
    }
}
