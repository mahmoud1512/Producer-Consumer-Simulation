package com.Mahmoud.producerConsumerSimulation;


import com.Mahmoud.producerConsumerSimulation.Socket.messageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@CrossOrigin(origins={"http://localhost:8080"})
public class Controller {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    @Lazy
    private mySystem systemService;

    @MessageMapping("/receive")
    public void takeMassageFromFrontend(messageObject[] data)
    {
       systemService.CreateSystem(data);
    }


    public void sendToFrontend(String message)
    {
        messagingTemplate.convertAndSend("/topic/data",message);  //convert message to string and send it
    }
}
