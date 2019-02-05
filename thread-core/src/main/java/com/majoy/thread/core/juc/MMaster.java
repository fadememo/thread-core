package com.majoy.thread.core.juc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MMaster {
    //存放任务
    public static final ConcurrentLinkedQueue<String> Tasks = new ConcurrentLinkedQueue<String>();
    //存放返回结果
    public static final ConcurrentHashMap<String,String> rets = new ConcurrentHashMap<>();
    //存放workers
    public static final HashMap<String,Runnable> workers = new HashMap<>();

    public static ConcurrentHashMap<String, String> getRets() {
        return rets;
    }

    public static HashMap<String, Runnable> getWorkers() {
        return workers;
    }

    public static ConcurrentLinkedQueue<String> getTasks() {

        return Tasks;
    }

    public static void main(String[] args) {


        Thread t1 = new Thread(new MWorker("m1"));
        Thread t2 = new Thread(new MWorker("m2"));
        Thread t3 = new Thread(new MWorker("m3"));
        workers.put("m1",t1);
        workers.put("m2",t2);
        workers.put("m3",t3);


    }

}
