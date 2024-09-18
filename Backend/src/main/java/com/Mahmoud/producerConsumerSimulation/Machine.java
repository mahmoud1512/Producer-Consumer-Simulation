package com.Mahmoud.producerConsumerSimulation;


public class Machine implements Runnable{
    private long serviceTime;
    private String originalColor;
    private String currentColor;
    private queue firstStageQueue;   //will be my observable
    private queue secondStageQueue;
    private long startTime;
    private long endTime;
    private String id;
    private mySystem system;

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

    public String getOriginalColor() {
        return originalColor;
    }

    public void setOriginalColor(String originalColor) {
        this.originalColor = originalColor;
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    public String getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(String currentColor) {
        this.currentColor = currentColor;
    }

    public void addSystem(mySystem system)
    {
        this.system=system;
    }
    public void registerToMyFSQ()  //Observer pattern
    {
        this.firstStageQueue.AcceptObserver(this);
    }

    //Working method
    @Override
    public void run() {
        while(true)
        {
            try {
                Product product=firstStageQueue.getQueue().take();
                this.currentColor=product.getColor();
                // TODO: inform the frontend with the Color change and first stage products decline
                long Start=java.lang.System.currentTimeMillis();
                System.out.println(product.getName()+ " is taken from "+firstStageQueue.getId()+ " by "+ this.id);
                Thread.sleep(serviceTime);
                this.secondStageQueue.getQueue().add(product);
                System.out.println(product.getName()+ " is added to "+secondStageQueue.getId()+" by "+this.id);
                // TODO : inform the frontend with product addition
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
