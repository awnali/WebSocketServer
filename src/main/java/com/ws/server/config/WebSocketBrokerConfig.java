package com.ws.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 98)
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.username}")
    private String userName;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${ws.destination.prefix}")
    private String destinationPrefix;
    @Value("${ws.broker.broadcast.destination-prefix}")
    private String broadcastDestinationPrefix;
    @Value("${ws.broker.peer-to-peer.destination-prefix}")
    private String peerToPeerDestinationPrefix;
    @Value("${ws.heartbeat-interval}")
    private int heartbeatInterval;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes(destinationPrefix);
        config.setPreservePublishOrder(true);

        /*
            BroadcastRelay is used as channel for broadcasting to multiple users
            and peerRelay is used to send message to specific user (peer to peer)
            it's just a convention, you can also use same end point for broadcasting
            and peer to peer
         */

        // If you want to use in memory Broker




        /*
            If you want to use external broker like RabbitMQ or ActiveMQ,
            then remove the simple broker and uncomment following
        */

        config.enableStompBrokerRelay(broadcastDestinationPrefix, peerToPeerDestinationPrefix)
              .setRelayHost(host)
              .setRelayPort(port)
              .setSystemLogin(userName)
              .setSystemPasscode(password);
    }
}
