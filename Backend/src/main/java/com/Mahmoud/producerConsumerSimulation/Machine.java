package com.Mahmoud.producerConsumerSimulation;


import java.util.ArrayList;

public class Machine implements Runnable{
    private long serviceTime;
    private final String originalColor="#808080";
    private String currentColor;
    private ArrayList<queue> firstStageQueues=new ArrayList<>() ;   //will be my observable
    private queue secondStageQueue;
    private String id;
    private mySystem systemService;

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


    //Working method
    @Override
    public void run() {
        while(true)
        {
            try {
                synchronized (firstStageQueues) {
                    for (int i = 0; i < firstStageQueues.size(); i++) {
                        queue firstStageQueue = firstStageQueues.get(i);
                        //systemService.tellFrontend(firstStageQueue.getId()+" "+firstStageQueue.checkSize());
                        Product product = firstStageQueue.take();
                        if (product == null) {
                            firstStageQueue.acceptObservers(this);
                            continue;
                        }
                        this.setCurrentColor(product.getColor());
                        systemService.tellFrontend(firstStageQueue.getId() + " " + firstStageQueue.checkSize());
                        systemService.tellFrontend(this.id + " " + this.getCurrentColor());
                        Thread.sleep(serviceTime);
                        systemService.tellFrontend(secondStageQueue.getId() + " " + secondStageQueue.checkSize());
                        this.secondStageQueue.add(product);
                        systemService.tellFrontend(secondStageQueue.getId() + " " + secondStageQueue.checkSize());
                        systemService.tellFrontend(this.id + " " + this.originalColor);
                    }
                }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
