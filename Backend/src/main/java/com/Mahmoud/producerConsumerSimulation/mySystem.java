package com.Mahmoud.producerConsumerSimulation;

import com.Mahmoud.producerConsumerSimulation.Socket.messageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class mySystem {

   private ArrayList<Machine>machineArrayList=new ArrayList<>();
   private ArrayList<queue>queueArrayList=new ArrayList<>();
    @Autowired
    private Controller controller;
    public void CreateSystem(messageObject[]data)
    {

        for (messageObject object:data)
        {
            System.out.println(object.getSource());
            System.out.println(object.getDestination());
            if(object.getSource().charAt(0)=='m')
            {
                if(!searchMachine(object.getSource()))
                {
                    Machine machine=new Machine();
                    machine.setId(object.getSource());
                    queue queue=new queue();
                    queue.setId(object.getDestination());
                    queue.setQueue(new LinkedBlockingQueue<>());
                    machine.setSecondStageQueue(queue);
                    machine.addSystem(this);
                    machine.setServiceTime((long) (Math.random()*1000));
                    queueArrayList.add(queue);
                    machineArrayList.add(machine);
                }
            }
            else
            {
                if(!searchQueue(object.getSource()))
                {
                    queue queue=new queue();
                    queue.setId(object.getSource());
                    queue.setQueue(new LinkedBlockingQueue<>());
                    if(object.getSource().equals("q0"))
                    {
                        queue.generateProducts(10);
                    }
                    Machine machine=new Machine();
                    machine.setServiceTime((long) (Math.random()*1000));
                    machine.setId(object.getDestination());
                    machine.setFirstStageQueue(queue);
                    machine.addSystem(this);
                    queueArrayList.add(queue);
                    machineArrayList.add(machine);
                }
            }
        }
        for (Machine m:machineArrayList) {
            Thread machine=new Thread(m);
            machine.start();
        }
    }

    private boolean searchQueue(String source) {
        for (queue q:queueArrayList)
        {
            if (q.getId().equals(source))
                return true;
        }
        return false;
    }

    private boolean searchMachine(String source) {
        for (Machine machine : machineArrayList) {
            if (machine.getId().equals(source))
                return true;
        }
        return false;
    }

    public synchronized void tellFrontend(String data)  //This method is created  especially not to allow objects to talk directly with the controller
    {
        controller.sendToFrontend(data);
    }

}
