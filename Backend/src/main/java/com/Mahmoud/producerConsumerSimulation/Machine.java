package com.Mahmoud.producerConsumerSimulation;


import com.Mahmoud.producerConsumerSimulation.SnapShot.CareTaker;
import com.Mahmoud.producerConsumerSimulation.SnapShot.Momento;

import java.util.ArrayList;

public class Machine implements Runnable{
    private long serviceTime;
    private final String originalColor="#808080";
    private String currentColor;
    private ArrayList<queue> firstStageQueues=new ArrayList<>() ;   //will be my observable
    private queue secondStageQueue;
    private String id;
    private mySystem systemService;
    private  CareTaker careTaker;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
    }




    public void setFirstStageQueue(queue firstStageQueue) {
        this.firstStageQueues.add(firstStageQueue);
    }


    public void setSecondStageQueue(queue secondStageQueue) {
        this.secondStageQueue = secondStageQueue;
    }

    public String getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(String currentColor) {
        this.currentColor = currentColor;
    }

    public void addSystem(mySystem systemService)
    {
        this.systemService=systemService;
    }

    public ArrayList<queue> getFirstStageQueues()
    {
        return this.firstStageQueues;
    }

    public void addCareTaker(CareTaker careTaker) {
        this.careTaker=careTaker;
    }

    //Working method
    @Override
    public void run() {
        while(true)
        {
            try {
                synchronized (firstStageQueues) {
                    for (int i = 0; i < firstStageQueues.size(); i++) {
                        queue firstStageQueue = firstStageQueues.get(i);
                        Product product = firstStageQueue.take();
                        if (product == null) {
                            firstStageQueue.acceptObservers(this);
                            continue;
                        }
                        this.setCurrentColor(product.getColor());

                        String data=this.id + " " + this.getCurrentColor();
                        careTaker.addMomento(new Momento(data));
                        systemService.tellFrontend(data);

                        Thread.sleep(serviceTime);
                        data=Long.toString(serviceTime);
                        careTaker.addMomento(new Momento("wait"+" "+data));

                        data=this.id + " " + this.originalColor;
                        careTaker.addMomento(new Momento(data));
                        systemService.tellFrontend(data);

                        this.secondStageQueue.add(product);

                    }
                }
                if(this.systemService.checkEnd())
                {
                    break;
                }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }


}
