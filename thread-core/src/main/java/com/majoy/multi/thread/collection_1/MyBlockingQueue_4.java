package com.majoy.multi.thread.collection_1;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

class MyCompareNode implements Comparable<MyCompareNode>{
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyCompareNode( ) {
    }
    public MyCompareNode(int id,String name ) {
        this.name = name;
        this.id = id;
    }

    @Override
    public int compareTo(MyCompareNode o) {
        return id-o.id;
    }
}
public class MyBlockingQueue_4 {
    //最大容量,//要用私有的.不是静态的
    private  final int maxsize ;
    private  final int minsize = 0;
    //队列元素计数 //这里要用final 以保证唯一
    private final AtomicInteger count = new AtomicInteger(0);
    //链接队列
    private LinkedList<Object>llist = new LinkedList<>();
    //内部锁
    private final Object lock = new Object();
    //要写构造方法
    public  MyBlockingQueue_4(int maxsize){
        this.maxsize = maxsize;
    }
    public AtomicInteger getCount() {
        return count;
    }

    public LinkedList<Object> getLlist() {
        return llist;
    }

    public void setLlist(LinkedList<Object> llist) {
        this.llist = llist;
    }

    //放入元素 //应该在整体上加锁吗?
    public void put(Object obj){
        //典型的线程安全问题,就是因为 询问容量和放入元素不是原子性操作导致的,因此syn应当包住全部
        synchronized (lock){
            while(count.get()>=this.maxsize){
                try {
                    lock.wait();
                    System.out.println(Thread.currentThread().getName()+"队列满,进入等待");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            llist.add(obj);//不需要使用this.访问成员变量
            count.getAndIncrement();
            System.out.println(Thread.currentThread().getName()+"放入元素"+obj);
            lock.notify();
        }
    }
    //放入元素
    public Object take(){
        Object temp = null;
        synchronized (lock){
            while(this.count.get()<=minsize){
                try {
                    lock.wait();
                    System.out.println(Thread.currentThread().getName()+"队列为空,进入等待");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            temp = this.llist.pollFirst();
            count.getAndDecrement();
            System.out.println(Thread.currentThread().getName()+"取出元素"+temp);
            lock.notify();
            return temp;
        }
    }

    public static void main(String[] args) {
        MyBlockingQueue_4 mbQueue = new MyBlockingQueue_4(5);
        mbQueue.put("obj1");
        mbQueue.put("obj2");
        mbQueue.put("obj3");
        mbQueue.put("obj4");
        mbQueue.put("obj5");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                mbQueue.put("obj6");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mbQueue.put("obj7");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                mbQueue.take();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mbQueue.take();
            }
        });
        t1.start();
        t2.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(mbQueue.getLlist());
    }

}
