package com.Mahmoud.producerConsumerSimulation;


import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class queue {
    private BlockingQueue<Product> queue;

    private String id;
    private ArrayList<Machine>observers=new ArrayList<>();

    public synchronized String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BlockingQueue<Product> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Product> queue) {
        this.queue = queue;
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
        if(this.checkSize()!=0)
            return this.queue.take();
        else
            return null;
    }
    public void add(Product product)
    {
        this.queue.add(product);
        for (Machine machine:observers) {
            synchronized (machine.getFirstStageQueues())
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
