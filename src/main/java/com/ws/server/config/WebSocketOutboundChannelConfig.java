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
@Order(Ordered.HIGHEST_PRECEDENCE + 96)
public class WebSocketOutboundChannelConfig implements WebSocketMessageBrokerConfigurer {


    @Value("${ws.outbound-channel.queue-capacity}")
    private int outboundChannelQueueCapacity;
    @Value("${ws.outbound-channel.core-size}")
    private int outboundChannelCoreSize;
    @Value("${ws.outbound-channel.max-size}")
    private int outboundChannelMaxSize;
    @Value("${ws.outbound-channel.keep-alive-seconds}")
    private int outboundChannelKeepAlive;

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor()
                    .queueCapacity(outboundChannelQueueCapacity)
                    .corePoolSize(outboundChannelCoreSize)
                    .maxPoolSize(outboundChannelMaxSize)
                    .keepAliveSeconds(outboundChannelKeepAlive);
    }

}

