package com.Mahmoud.producerConsumerSimulation;


import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class queue {
    private BlockingQueue<Product> queue;

    private String id;
    private final mySystem systemService;
    private ArrayList<Machine>observers=new ArrayList<>();
    public queue(mySystem mySystem, String id, LinkedBlockingQueue<Product>queue)
    {
        this.systemService=mySystem;
        this.id=id;
        this.queue=queue;
        if(id.equals("q0")) {
            int x=(int) (Math.random() * 15)+5;
            System.out.println(x);
            this.generateProducts(x);
        }
        systemService.tellFrontend(this.getId()+" "+this.checkSize());
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
            systemService.tellFrontend(this.getId()+" "+this.checkSize());
        }
       return product;
    }
    public void add(Product product)
    {
        this.queue.add(product);
        systemService.tellFrontend(this.getId()+" "+this.checkSize());
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
