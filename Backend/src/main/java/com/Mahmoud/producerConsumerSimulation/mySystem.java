package com.Mahmoud.producerConsumerSimulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class mySystem {


    @Autowired
    private Controller controller;
    public void CreateSystem()
    {
        //TODO: create System data
    }

    public synchronized void tellFrontend(String data)  //This method is created  especially not to allow objects to talk directly with the controller
    {
        controller.sendToFrontend(data);
    }

}
