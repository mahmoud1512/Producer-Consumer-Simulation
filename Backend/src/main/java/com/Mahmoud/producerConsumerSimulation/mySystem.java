package com.Mahmoud.producerConsumerSimulation;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class mySystem {
    private  Map<String,Machine>MachinesMap=new HashMap<>();
    private  Map<String, queue> QueueMap=new HashMap<>();
    public void registerMachine(String id,Machine machine)
    {
        this.MachinesMap.put(id,machine);
    }
    public void registerQueue(String id,queue queue)
    {
        this.QueueMap.put(id,queue);
    }

}
