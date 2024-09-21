package com.Mahmoud.producerConsumerSimulation;


import java.util.concurrent.BlockingQueue;

public class queue {
    private BlockingQueue<Product> queue;

    private String id;

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



}
