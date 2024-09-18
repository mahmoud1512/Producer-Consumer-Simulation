package com.Mahmoud.producerConsumerSimulation;


public class Machine {
    private long serviceTime;
    private String originalColor;
    private String currentColor;
    private queue firstStageQueue;   //will be my observable
    private queue secondStageQueue;
    private long startTime;
    private long endTime;
    private String id;

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

    //Working methods

    public void processProduct()
    {
        //TODO
    }
    public void registerToMyFSQ()  //Observer pattern
    {
        this.firstStageQueue.AcceptObserver(this);
    }
    public void createMomento()
    {
        //TODO
    }

}
