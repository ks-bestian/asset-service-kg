package kr.co.bestiansoft.ebillservicekg.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

    	System.out.println("configureMessageBroker 메서드 호출");

        registry.enableSimpleBroker("/topic"); // 클라이언트가 구독할 경로
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 메시지를 보낼 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

    	System.out.println("registerWebSocketHandlers 메서드 호출");

        registry.addEndpoint("/ws").withSockJS();

    }



}
