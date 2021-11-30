package com.ws.server;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import javax.annotation.PostConstruct;
import java.util.Date;

@SpringBootApplication
class WebSocketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketServerApplication.class, args);

		Date exp = new Date(System.currentTimeMillis() + 232232323);

        Claims claims = Jwts.claims().setSubject("2");
        String token = Jwts.builder().setClaims(claims)
                           .signWith(SignatureAlgorithm.HS512, "secret")
                           .setExpiration(exp)
                           .compact();

        System.out.println(token);
    }



}
