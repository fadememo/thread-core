package com.majoy.multi.thread.collection_1;

import java.util.concurrent.*;

public class UseBlockingQueue_5 {
    public static void main(String[] args) {
        //1,无界无阻塞高性能队列
        ConcurrentLinkedQueue clQueue = new ConcurrentLinkedQueue();
        clQueue.offer("a");
        clQueue.add("b");
        clQueue.offer("c");
        clQueue.peek();
        clQueue.poll();
        System.out.println("长度为"+clQueue.size());
        //2,有界阻塞队列,不允许空元素
        ArrayBlockingQueue abQueue = new ArrayBlockingQueue(2);
        abQueue.offer("a");//失败会返回false
        abQueue.add("b");
        abQueue.offer("c");
        abQueue.peek();
        abQueue.poll();
        System.out.println("size = "+abQueue.size()+" 元素 "+abQueue);
        ArrayBlockingQueue<String>abQueue2 = new ArrayBlockingQueue<>(5);
        abQueue.add("d");
        //abQueue.add("e");//抛出IllegalStateException: Queue full
        abQueue.drainTo(abQueue2,3);//将this de 元素 添加到abQueue2
        System.out.println("size = "+abQueue2.size()+" 元素 "+abQueue2);
        //3,基于阻塞的无界高性能队列
        LinkedBlockingQueue lbQueue = new LinkedBlockingQueue();
        lbQueue.offer("a");
        lbQueue.add("b");
        lbQueue.offer("c");
        lbQueue.peek();
        lbQueue.poll();
        System.out.println( 0x7fffffff);
        // 4,同步无缓冲阻塞 队列,不能存放任何元素,需要消费者线程使用take（）等待
        SynchronousQueue syncQueue = new SynchronousQueue();
        //syncQueue.offer("a");


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //System.out.println(syncQueue.poll(2, TimeUnit.SECONDS));
                try {
                    System.out.println(syncQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                syncQueue.add("p");
            }
        },"t1");
        t2.start();
        t1.start();


    }
}
