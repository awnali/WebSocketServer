package com.ws.server.presenter;

import com.ws.server.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class MessageController {

    final Logger logger = LoggerFactory.getLogger(MessageController.class.getName());

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/greeting")
    @SendTo("/topic/messages")
    @PreAuthorize("isAuthenticated()")
    public Message handle(Message msg, @Autowired Principal principal) {
        logger.info("Thread: {} - Received the message from client: {}, {}", Thread.currentThread().getName(), msg.getFrom(), principal.getName());
        return msg;
    }

    @MessageMapping("/chat")
    @SendToUser("/queue/messages")
    @PreAuthorize("isAuthenticated()")
    public Message reply(Message msg, Principal principal) {
        logger.info("Thread: {} - Received the message from client: {}, {}", Thread.currentThread().getName(), msg.getFrom(), principal.getName());
        return msg;
    }


    @MessageMapping("/chat-with-user/{sendToUserId}")
    @PreAuthorize("isAuthenticated()")
    public void chat(Message msg, @DestinationVariable String sendToUserId, Principal principal) {
        logger.info("Thread: {} - Received the message from client: {}, for {}", Thread.currentThread().getName(), msg.getFrom(), sendToUserId);
        simpMessagingTemplate.convertAndSendToUser(sendToUserId, "/queue/messages", msg);
    }


    @SubscribeMapping("/subscribe-to-chat")
    public Message SubscribeToChat() {
        logger.info("Thread: {} - Received the message from client", Thread.currentThread().getName());
        return getSampleMessage();
    }

    @MessageExceptionHandler
    public String handleException(Exception exception) {
        return "Error";
    }


    private Message getSampleMessage() {
        Message msg = new Message();
        msg.setFrom("Nicky");
        msg.setText("Howdy!!");
        return msg;
    }
}
