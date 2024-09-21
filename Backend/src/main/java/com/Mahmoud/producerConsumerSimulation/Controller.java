package com.Mahmoud.producerConsumerSimulation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
public class Controller {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    @Lazy
    private mySystem systemService;


    public void sendToFrontend(String message)
    {
        messagingTemplate.convertAndSend("/topic/data",message);  //convert message to string and send it
    }
}
