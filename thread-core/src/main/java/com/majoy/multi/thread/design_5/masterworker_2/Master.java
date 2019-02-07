package com.majoy.multi.thread.design_5.masterworker_2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 怎么将master 和 worker 建立对应关系啊???
 */
public class Master {
    // 1, 任务队列
    private ConcurrentLinkedQueue<Task> taskQueue = new ConcurrentLinkedQueue<>();
    // 2,承装 worker 执行器 的 容器
    private HashMap<String, Thread> workerMap = new HashMap<>();
    // 3,接受worker 处理成功的结果集合
    private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();
    // 8,增加一个进程通讯机制
    private int workcount = 0;

    CountDownLatch latch = null;

    //要给worker返回以上两项的引用

    public ConcurrentLinkedQueue<Task> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(ConcurrentLinkedQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public ConcurrentHashMap<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    //4,构造方法里面要对worker进行初始化
    public Master(Worker worker, int workCount) {
        //4.4 放入一个latch的引用
        latch = new CountDownLatch(workCount);
        worker.setLatch(latch);
        //4.1 给worker设定任务队列的引用
        worker.setTaskQueue(taskQueue);
        //4.2 给worker 设定结果集的引用
        worker.setResultMap(resultMap);
        //4.3 将所有worker初始化,放入容器
        for (int i = 0; i < workCount; i++) {
            this.workerMap.put(Integer.toString(i), new Thread(worker));
        }

    }

    // 5.提交任务 submit
    public void submit(Task task) {
        this.taskQueue.add(task);
    }

    // 6.执行任务 execute
    public void execute() {
        for (Map.Entry<String, Thread> me : workerMap.entrySet()) {
            me.getValue().start();
        }
    }

    //7 汇总任务结果
    public int summary() {
        int sum = 0;
        for (Map.Entry<String, Object> me : resultMap.entrySet()) {
            System.out.println("(Integer) me.getValue()= "+(Integer) me.getValue() );
            sum += (Integer) me.getValue();
        }
        return sum;
    }

    //8,判断任务执行结果,完成则发起通知
    public boolean isComplete() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

}
