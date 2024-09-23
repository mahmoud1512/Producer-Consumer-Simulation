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

            if(object.getSource().charAt(0)=='m')
            {

                    Machine machine=searchMachine(object.getSource());
                    queue queue=searchQueue(object.getDestination());
                    machine.setSecondStageQueue(queue);

            }
            else
            {

                  queue q=searchQueue(object.getSource());
                  Machine machine=searchMachine(object.getDestination());
                  q.acceptObservers(machine);
                  if(q.getId().equals("q0"))
                    machine.setFirstStageQueue(q);

            }
        }
        for (Machine m:machineArrayList) {
            Thread machine=new Thread(m);
            machine.start();
        }
    }

    private queue searchQueue(String source) {
        for (queue q:queueArrayList)
        {
            if (q.getId().equals(source))
                return q;
        }
        queue queue=new queue();
        queue.setId(source);
        queue.setQueue(new LinkedBlockingQueue<>());
        if(source.equals("q0"))
            queue.generateProducts(10);
        queueArrayList.add(queue);
        return queue;
    }

    private Machine searchMachine(String source) {
        for (Machine machine : machineArrayList) {
            if (machine.getId().equals(source))
                return machine;
        }
        Machine machine=new Machine();
        machine.addSystem(this);
        machine.setId(source);
        machine.setServiceTime((long) (Math.random()*10000+100));
        machineArrayList.add(machine);
        return machine;
    }

    public synchronized void tellFrontend(String data)  //This method is created  especially not to allow objects to talk directly with the controller
    {
        controller.sendToFrontend(data);
    }
    public void clear()
    {
        this.machineArrayList=new ArrayList<>();
        this.queueArrayList=new ArrayList<>();
    }

}
