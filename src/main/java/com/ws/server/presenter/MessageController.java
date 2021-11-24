package com.ws.server.presenter;

import com.ws.server.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageController {

    final Logger logger = LoggerFactory.getLogger(MessageController.class.getName());

    @MessageMapping("/greeting")
    @SendTo("/topic/messages")
    @PreAuthorize("isAuthenticated()")
    public Message handle(Message msg, @Autowired Principal principal) {
        logger.info("Received the message from client: {}, {}", msg.getFrom(), principal.getName());
        return msg;
    }


}
