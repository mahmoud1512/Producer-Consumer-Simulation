package com.Mahmoud.producerConsumerSimulation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@CrossOrigin("*")
public class Controller {
    @Autowired
    private mySystem system;

    //TODO: get the data from the frontend and wire Them
    @GetMapping("/")
    public String go() {
        System.out.println("go go go");

        queue q1 = new queue();
        q1.setQueue(new LinkedBlockingQueue<>());
        q1.setId("q1");
        q1.generateProducts(12);

        queue q2 = new queue();
        q2.setQueue(new LinkedBlockingQueue<>());
        q2.setId("q2");

        queue q3 = new queue();
        q3.setQueue(new LinkedBlockingQueue<>());
        q3.setId("q3");

        Machine machine1 = new Machine();
        machine1.setServiceTime(500);
        machine1.setId("m1");

        Machine machine2 = new Machine();
        machine2.setServiceTime(1000);
        machine2.setId("m2");

        Machine machine3 = new Machine();
        machine3.setServiceTime(3000);
        machine3.setId("m3");

        // Set queues for machines
        machine1.setFirstStageQueue(q1);
        machine1.setSecondStageQueue(q2);

        machine2.setFirstStageQueue(q1);
        machine2.setSecondStageQueue(q3);

        machine3.setFirstStageQueue(q2);
        machine3.setSecondStageQueue(q3);

        // Start the threads for each machine
        Thread m1 = new Thread(machine1);
        Thread m2 = new Thread(machine2);
        Thread m3 = new Thread(machine3);

        m1.start();
        m2.start();
        m3.start();

        return "Simulation started";
    }


}
