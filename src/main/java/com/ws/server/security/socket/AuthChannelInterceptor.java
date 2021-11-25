package com.ws.server.security.socket;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String token = accessor.getFirstNativeHeader("token");
            if (token != null) {

                Claims user = Jwts.parser()
                                  .setSigningKey("secret")
                                  .parseClaimsJws(token)
                                  .getBody();
                GrantedAuthority userRoleAuthority = new SimpleGrantedAuthority("USER");
                List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
                grantedAuthorities.add(userRoleAuthority);
                UserDetails userDetails = new User(user.getSubject(), "", grantedAuthorities);

                accessor.setUser(new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>()));
//
                System.out.println(token);
            }
        }
        return message;

    }
}
