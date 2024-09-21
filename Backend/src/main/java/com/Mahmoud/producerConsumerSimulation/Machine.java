package com.Mahmoud.producerConsumerSimulation;


public class Machine implements Runnable{
    private long serviceTime;
    private final String originalColor="#808080";
    private String currentColor;
    private queue firstStageQueue;   //will be my observable
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


    public queue getFirstStageQueue() {
        return firstStageQueue;
    }

    public void setFirstStageQueue(queue firstStageQueue) {
        this.firstStageQueue = firstStageQueue;
    }

    public queue getSecondStageQueue() {
        return secondStageQueue;
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



    //Working method
    @Override
    public void run() {
        while(true)
        {
            try {
                Product product=firstStageQueue.getQueue().take();
                this.setCurrentColor(product.getColor());
                systemService.tellFrontend(firstStageQueue.getId()+" "+firstStageQueue.checkSize());
                systemService.tellFrontend(this.id+" "+this.getCurrentColor());
                Thread.sleep(serviceTime);
                this.secondStageQueue.getQueue().add(product);
                systemService.tellFrontend(secondStageQueue.getId()+" "+secondStageQueue.checkSize());
                systemService.tellFrontend(this.id+" "+this.originalColor);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
