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

    	System.out.println("configureMessageBroker method Call");

        registry.enableSimpleBroker("/topic"); // Client Subscribe channel
        registry.setApplicationDestinationPrefixes("/app"); // Client Message Sending channel
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

    	System.out.println("registerWebSocketHandlers method Call");

        registry.addEndpoint("/ws").withSockJS();

    }



}
