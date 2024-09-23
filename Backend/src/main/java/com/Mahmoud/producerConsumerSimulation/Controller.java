package com.Mahmoud.producerConsumerSimulation;


import com.Mahmoud.producerConsumerSimulation.SnapShot.CareTaker;
import com.Mahmoud.producerConsumerSimulation.SnapShot.Momento;
import com.Mahmoud.producerConsumerSimulation.Socket.messageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    @MessageMapping("/clear")
    public void takeClearMessage(String data)
    {
        System.out.println(data);
        this.systemService=new mySystem(this);
    }
    @MessageMapping("/replay")
    @SendTo("/topic/replay")
    public ArrayList<Momento>ReplaySimulation()
    {
        return systemService.getMomentoes();
    }


    public void sendToFrontend(String message)
    {
        messagingTemplate.convertAndSend("/topic/data",message);  //convert message to string and send it
    }
}
