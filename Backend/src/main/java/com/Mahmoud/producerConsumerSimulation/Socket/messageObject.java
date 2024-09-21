package com.Mahmoud.producerConsumerSimulation.Socket;

public class messageObject {
    private String source;
    private String destination;

    public messageObject(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public messageObject() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
