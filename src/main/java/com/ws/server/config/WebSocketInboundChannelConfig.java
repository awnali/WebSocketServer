package com.ws.server.config;

import com.ws.server.security.socket.AuthChannelInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 97)
public class WebSocketInboundChannelConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${ws.inbound-channel.queue-capacity}")
    private int inboundChannelQueueCapacity;
    @Value("${ws.inbound-channel.core-size}")
    private int inboundChannelCoreSize;
    @Value("${ws.inbound-channel.max-size}")
    private int inboundChannelMaxSize;
    @Value("${ws.inbound-channel.keep-alive-seconds}")
    private int inboundChannelKeepAlive;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthChannelInterceptor());
        registration.taskExecutor()
                    .corePoolSize(inboundChannelCoreSize)
                    .maxPoolSize(inboundChannelMaxSize)
                    .queueCapacity(inboundChannelQueueCapacity)
                    .keepAliveSeconds(inboundChannelKeepAlive);
    }

}
