package com.majoy.multi.thread.design_5.masterworker_2;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class Worker implements Runnable{
    //任务队列
    private ConcurrentLinkedQueue<Task> taskQueue ;
    //结果集
    private ConcurrentHashMap<String,Object> resultMap ;
    //要给worker返回以上两项的引用
    private CountDownLatch latch;

    public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> retMap) {
        this.resultMap = retMap;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        while(true){
            Task task = taskQueue.poll();
            //taskQueue.add(task);
            if(task == null){
                //仅当worker的所有任务执行完成,才发出通知
                latch.countDown();
                System.out.println("发出通知!!!");
                break;
            }
            Object result = handle(task);
            this.resultMap.put(Integer.toString(task.getId()),result);
        }
    }
    public Object handle(Task task){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task.getCount();
    }
}
