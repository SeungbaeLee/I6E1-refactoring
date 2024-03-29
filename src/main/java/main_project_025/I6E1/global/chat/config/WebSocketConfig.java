package main_project_025.I6E1.global.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    //정보를 처리할 Handler 와 webSocket 주소를 WebSocketHandlerRegistry에 추가해주면 -> 해당 주소로 접근하면 웹소켓 연결을 할 수가 있다.
    private final WebSocketHandler webSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(webSocketHandler, "/chat").setAllowedOriginPatterns("*");
        // url : ws://localhost:8080/chat
    }
}
