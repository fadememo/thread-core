package com.majoy.thread.core.juc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MWorker implements Runnable{
    public static final ConcurrentLinkedQueue<String> tasks = MMaster.getTasks();
    public static final ConcurrentHashMap<String,String> rets = MMaster.getRets();
    private    String name ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MWorker() {
    }

    public MWorker(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        //取出任务
        String task = tasks.peek();
        try {

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //生成结果
        String ret = this.name+"执行任务"+task+",返回结果";
        rets.put(this.name,ret);
    }
}
