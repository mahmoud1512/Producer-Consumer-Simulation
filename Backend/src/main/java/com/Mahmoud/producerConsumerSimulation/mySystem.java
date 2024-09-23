package com.Mahmoud.producerConsumerSimulation;

import com.Mahmoud.producerConsumerSimulation.Socket.messageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class mySystem {

   private ArrayList<Machine>machineArrayList=new ArrayList<>();
   private ArrayList<queue>queueArrayList=new ArrayList<>();
   private int size;
   private int maxQueueID=Integer.MIN_VALUE;
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
                   int id=Integer.parseInt(queue.getId().substring(1));
                  if(id>maxQueueID)
                  {
                      maxQueueID=id;
                  }
                System.out.println("Max is"+" "+maxQueueID);

            }
            else
            {


                  queue q=searchQueue(object.getSource());

                  Machine machine=searchMachine(object.getDestination());
                  q.acceptObservers(machine);
                  if(q.getId().equals("q0")) {
                      size=q.checkSize();
                      System.out.println(size);
                      machine.setFirstStageQueue(q);
                  }

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

        queue queue=new queue(this,source,new LinkedBlockingQueue<>());


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
        machine.setServiceTime((long) (Math.random()*2000+100));
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

    public synchronized boolean checkEnd() {
        if (getMaxIDSize()==size) {
            System.out.println("done");
            for (queue queue:queueArrayList) {
                if(queue.getId().equals("q"+maxQueueID))
                    continue;
                this.tellFrontend(queue.getId() + " " + 0);
            }
            return true;
        }
        return false;
    }

    private int getMaxIDSize() {
        for (queue queue:queueArrayList)
        {
            if(queue.getId().equals("q"+maxQueueID)) {
                int x=queue.checkSize();
                return x;
            }
        }
        return 0;
    }
}
