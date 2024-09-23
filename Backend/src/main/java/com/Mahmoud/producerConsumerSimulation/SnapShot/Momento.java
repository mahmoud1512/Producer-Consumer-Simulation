package com.Mahmoud.producerConsumerSimulation.SnapShot;

public class Momento {
    private String snapShot;

    public Momento(String snapShot) {
        this.snapShot = snapShot;
    }

    public Momento() {
    }

    public String getSnapShot() {
        return snapShot;
    }

    public void setSnapShot(String snapShot) {
        this.snapShot = snapShot;
    }
}
