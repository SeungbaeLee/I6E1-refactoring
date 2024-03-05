package main_project_025.I6E1.global.chat.config;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class WebSocketTest {

    private static Set<Session> clients =
            Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("open session : " + s.toString());
        if(!clients.contains(s)) {
            clients.add(s);
            System.out.println("session open : " + s);
        }else {
            System.out.println("이미 연결된 session 입니다.");
        }
    }

    @OnMessage
    public void onMessage(String msg, Session session) throws Exception{
        System.out.println("receive message : " + msg);
        for(Session s : clients) {
            System.out.println("send data : " + msg);
            s.getBasicRemote().sendText(msg);
        }
    }

    @OnClose
    public void onClose(Session s) {
        System.out.println("session close : " + s);
        clients.remove(s);
    }

}