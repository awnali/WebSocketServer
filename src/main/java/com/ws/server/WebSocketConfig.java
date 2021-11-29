package com.ws.server;

import com.ws.server.security.socket.AuthChannelInterceptor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.ArrayList;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Value("${spring.rabbitmq.username}")
    private String userName;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${ws.endpoint}")
    private String endpoint;
    @Value("${ws.destination.prefix}")
    private String destinationPrefix;
    @Value("${ws.allow-origins}")
    private String allowOrigins;
    @Value("${ws.broker.broadcast.destination-prefix}")
    private String broadcastRelay;
    @Value("${ws.broker.peer-to-peer.destination-prefix}")
    private String peerRelay;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(endpoint)
                .setAllowedOrigins(allowOrigins);
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes(destinationPrefix);
        config.setPreservePublishOrder(true);

        // If you want to use in memory Broker
        config.enableSimpleBroker(broadcastRelay, peerRelay)
                .setTaskScheduler(new DefaultManagedTaskScheduler())
                .setHeartbeatValue(new long[]{0,20000});

        // If you want to use external broker like RabbitMQ or ActiveMQ,
        // then remove the simple broker and uncomment following

//        config.enableStompBrokerRelay(broadcastRelay, peerRelay)
//              .setRelayHost(host)
//              .setRelayPort(port)
//              .setSystemLogin(userName)
//              .setSystemPasscode(password);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthChannelInterceptor());
    }
}
