package com.ws.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    final Logger logger = LoggerFactory.getLogger(MessageController.class.getName());

    @MessageMapping("/greeting")
    @SendTo("/topic/messages")
    public Message handle(Message msg) {
        logger.info("Received the message from client: {}", msg.getFrom());
        return msg;
    }

}
