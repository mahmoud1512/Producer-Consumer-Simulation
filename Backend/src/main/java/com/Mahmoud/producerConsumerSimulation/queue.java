package com.Mahmoud.producerConsumerSimulation;


import com.Mahmoud.producerConsumerSimulation.SnapShot.CareTaker;
import com.Mahmoud.producerConsumerSimulation.SnapShot.Momento;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class queue {
    private BlockingQueue<Product> queue;

    private String id;
    private final mySystem systemService;
    private ArrayList<Machine>observers=new ArrayList<>();
    private final CareTaker careTaker;
    public queue(mySystem mySystem, String id, LinkedBlockingQueue<Product>queue, CareTaker careTaker)
    {
        this.systemService=mySystem;
        this.id=id;
        this.queue=queue;
        this.careTaker=careTaker;
        if(id.equals("q0")) {
            int x=(int) (Math.random() * 15)+5;
            System.out.println(x);
            this.generateProducts(x);
        }
        String data=this.getId()+" "+this.checkSize();
        careTaker.addMomento(new Momento(data));
        systemService.tellFrontend(data);
    }

    public synchronized String getId() {
        return id;
    }

    public synchronized int checkSize()
    {
        return this.queue.size();
    }

    public void generateProducts(int numberOfProductsInTheInitialQueue)
    {
        for (int i = 0; i < numberOfProductsInTheInitialQueue; i++) {
            queue.add(new Product(ColorPicker.getRandomColor(), "Product"+ i));
        }
    }


    public Product take() throws InterruptedException {
        Product product=null;
        if(this.checkSize()!=0) {
            product=queue.take();

            String data=this.getId()+" "+this.checkSize();
            careTaker.addMomento(new Momento(data));
            systemService.tellFrontend(data);
        }
       return product;
    }
    public void add(Product product)
    {
        this.queue.add(product);
        String data=this.getId()+" "+this.checkSize();
        careTaker.addMomento(new Momento(data));
        systemService.tellFrontend(data);
        for (Machine machine:observers) {
            synchronized (machine)
            {
                if(!machine.getFirstStageQueues().contains(this))
                   machine.getFirstStageQueues().add(this);
            }
        }
    }

    public void acceptObservers(Machine machine) {
        if(!observers.contains(machine))
           observers.add(machine);
    }


}
