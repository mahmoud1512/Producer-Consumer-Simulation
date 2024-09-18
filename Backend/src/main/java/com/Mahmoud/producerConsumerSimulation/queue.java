package com.Mahmoud.producerConsumerSimulation;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class queue {
    private BlockingQueue<Product> queue;
    private ArrayList<Machine>observers=new ArrayList<>();
    private String id;

    public String getId() {
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
    public synchronized void add(Product product) throws InterruptedException {
        this.queue.put(product);
    }
    public synchronized Product take() throws InterruptedException {
        return this.queue.take();
    }

    public void AcceptObserver(Machine machine)
    {
        observers.add(machine);
    }
    public synchronized int checkSize()
    {
        return this.queue.size();
    }

    public void generateProducts(int numberOfProductsInTheInitialQueue)
    {
        for (int i = 0; i < numberOfProductsInTheInitialQueue; i++) {
            queue.add(new Product(ColorPicker.getRandomColor()));
        }
    }
}
