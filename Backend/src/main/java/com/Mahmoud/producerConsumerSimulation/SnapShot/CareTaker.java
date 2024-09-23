package com.Mahmoud.producerConsumerSimulation.SnapShot;

import java.util.ArrayList;

public class CareTaker {
    private ArrayList<Momento> Momentoes=new ArrayList<>();
    public void addMomento(Momento momento)
    {
        Momentoes.add(momento);
    }


    public ArrayList<Momento> getData() {
        return this.Momentoes;
    }
}
