package com.ws.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import javax.annotation.PostConstruct;

@Configuration
public class WebSocketMonitoringConfig {

    @Value("${ws.logging.period}")
    private Long loggingPeriod;

    @Autowired
    private WebSocketMessageBrokerStats webSocketMessageBrokerStats;

    @PostConstruct
    public void init() {
        webSocketMessageBrokerStats.setLoggingPeriod(loggingPeriod); // desired time in millis
    }
}
