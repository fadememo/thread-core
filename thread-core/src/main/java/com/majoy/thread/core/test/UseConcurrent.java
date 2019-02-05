package com.majoy.thread.core.test;


import java.util.*;
import java.util.concurrent.*;

public class UseConcurrent{
    private static final int MAXIMUM_CAPACITY = 1 << 30;



    public static void main(String[] args) throws InterruptedException {

        //Vector和HashTable是线程安全的
        Hashtable ht = new Hashtable<String,String>();
        //原因是public synchronized V replace(K key, V value) ；采用了synchronized关键字
        ht.put("k1","v1");

        Vector<String> vt = new Vector<>();
        //public synchronized boolean add(E e)
        vt.add("majoy");
        vt.add("never");
        vt.add("die");

        //ConcurrentHashMap 使用 段来保证并发 static class Segment<K,V> extends ReentrantLock implements Serializable
        ConcurrentHashMap<String,String> cMap = new ConcurrentHashMap<>();
        cMap.put("may"," i be the one ");
        System.out.println(MAXIMUM_CAPACITY);
        //位运算节省性能
        System.out.println(1 << 30);
        System.out.println(5 << 1);
        System.out.println(5 >> 1);
        System.out.println(Integer.toBinaryString(5));
        //反射获取类和对象
        try {
            //System.out.println(Class.forName("ConcurrentHashMap").getField("NCPU"));
            //System.out.println(Class.forName("ShowOutput"));
            throw new ClassNotFoundException("为毛没法找到类？");
        } catch (ClassNotFoundException e) {
            System.out.println("为毛没法找到类");
        }
        /**
         * 循环时修改抛出异常
         */
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("majoy");
        //Itr类实现Iterator接口，其中保有一个
        Iterator<String> iterator = arrayList.iterator();
        try {
            while(iterator.hasNext()){
                arrayList.add("test2");
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.out.println("会抛出ConcurrentModificationException");;
        }
        /**
         * COW写时复制容器
         public boolean add(E e) {
         final ReentrantLock lock = this.lock;
         lock.lock();
         try {
         Object[] elements = getArray();
         int len = elements.length;
         Object[] newElements = Arrays.copyOf(elements, len + 1);
         newElements[len] = e;
         setArray(newElements);
         return true;
         } finally {
         lock.unlock();
         final  retrantlock = this.lock;
         lock.lock();
         )
         */
        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();

        CopyOnWriteArraySet<String> cowSet = new CopyOnWriteArraySet<>();
        cowList.add("i say");
        cowSet.add("nothing");
        /**
         * 并发队列
         */
        Set<String>set = new HashSet<>(Arrays.asList("majoy1","majoy2","majoy3"));

        ConcurrentLinkedQueue<String> clQueue = new ConcurrentLinkedQueue<String>();

        //没有无参构造方法,必须指定容量//BlockingQueue 是一个接口
        ArrayBlockingQueue<String>abQueue = new ArrayBlockingQueue<String>(5);
        //基于链表的阻塞队列
        /**
         *
         private E dequeue() {//假设队列 4,3,2,1 ,1是队首
             // assert takeLock.isHeldByCurrentThread();
             // assert head.item == null;
             Node<E> h = head; //h指向 1
             Node<E> first = h.next;//first 指向 2
             h.next = h; // 1 的next 指向 1
             head = first;
             E x = first.item;
             first.item = null;
             return x;
         }
         */
        LinkedBlockingQueue<String>lbQueue = new LinkedBlockingQueue<String>();
        //一种没有缓冲的队列,生产者产生的数据会直接被消费者获取并消费
        SynchronousQueue<String>sycQueue = new SynchronousQueue<String>();
        //基于优先级的阻塞队列,优先级判断通过构造函数传入的compator对象决定,公平锁,无界队列
        PriorityQueue<String>pQueue = new PriorityQueue<String>();
        //
        DelayQueue<Element> dQueue = new DelayQueue<Element>();
        clQueue.add("test1");
        abQueue.add("test2");
        lbQueue.add("test3");
        //这里无法插入元素,为毛???
        //sycQueue.add("test4");
        pQueue.add("test5");
        dQueue.add(new Element("element6"));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //不允许null元素
                clQueue.add("clQueue insert 1");
                abQueue.add("abQueue insert 1");
                lbQueue.add("lbQueue insert 1");
                // //抛出异常 IllegalStateException: Queue full
                //为什么这里插入数据就会导致下面的无法插入数据?
                sycQueue.add("sycQueue insert 1");
                pQueue.add("pQueue insert 1");
                dQueue.add(new Element("dQueue insert 1"));

            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                clQueue.add("clQueue insert 2");
                abQueue.add("abQueue insert 2");
                lbQueue.add("lbQueue insert 2");
                //sycQueue.add("sycQueue insert 2");
                pQueue.add("pQueue insert 2");//插入失败
                dQueue.add(new Element("dQueue insert 2"));

            }
        },"t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                clQueue.add("clQueue insert 3");
                abQueue.add("abQueue insert 3");
                lbQueue.add("lbQueue insert 3");
                //sycQueue.add("sycQueue insert 3");
                pQueue.add("pQueue insert 3");
                dQueue.add(new Element("dQueue insert 3"));
            }
        },"t3");
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(clQueue.poll());
                    System.out.println(abQueue.poll(2,TimeUnit.SECONDS));
                    System.out.println(lbQueue.poll(2,TimeUnit.SECONDS));
                    //System.out.println(sycQueue.poll(2,TimeUnit.SECONDS));
                    System.out.println(pQueue.poll());
                    Thread.sleep(2000,500000);
                    //E first = q.peek(); if (first == null || first.getDelay(NANOSECONDS) > 0)
                    System.out.println(dQueue.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //ConcurrentLinkedQueue的方法
        //System.out.println(clQueue.add("test11"));
        //System.out.println(clQueue.addAll(set));
        //System.out.println(clQueue.offer("test12"));
        //System.out.println(clQueue.contains("test12"));
        //在队尾放入元素,等待 timeout 时间阻塞则返回失败
        //System.out.println(abQueue.offer("test23",3,TimeUnit.SECONDS));
        ////在队首取出元素,等待 timeout 时间阻塞则返回失败
        //System.out.println(abQueue.poll(3,TimeUnit.SECONDS));
        ////返回剩余位置
        //System.out.println("容量剩余+"+abQueue.remainingCapacity());
        ////System.arraycopy(items, takeIndex, a, 0, count)
        //System.out.println("返回复制的数组+"+abQueue.toArray());
        ////从队列中移除和set集合重合的元素
        //System.out.println("移除和set集合重合"+abQueue.retainAll(set));
        ////取走BlockingQueue里排在首位的对象,若BlockingQueue为空,阻断进入等待状态直到BlockingQueue有新的数据被加入
        //System.out.println("取出队首+"+lbQueue.take());
        //总之这里会死锁,为毛?
        //System.out.println("取出队首+"+abQueue.take());
        //System.out.println("取出队首+"+abQueue.take());
        //System.out.println("取出队首+"+abQueue.take());
        //System.out.println("取出队首+"+abQueue.take());
        //System.out.println("取出队首+"+abQueue.take());


        System.out.println("clQueue: ");
        //元素为 t1 cl1 cl2 cl3
        while(clQueue.iterator().hasNext()){
            System.out.print("["+clQueue.size()+",  "+clQueue.poll()+"], ");
        }
        System.out.println("\nabQueue: ");
        //元素为t2 ab1 ab2 ab3
        while(abQueue.iterator().hasNext()){
            System.out.print("["+abQueue.size()+",  "+(abQueue.peek()!=null?abQueue.poll():"null")+"], ");
        }
        System.out.println("\nlbQueue: ");
        //t3 lb1 lb2 lb3
        while(lbQueue.iterator().hasNext()){
            System.out.print("["+lbQueue.size()+",  "+lbQueue.poll()+"], ");
        }
        System.out.println("\nsycQueue: ");
        //最终插入的元素是 test5 pq1 pq3
        while(sycQueue.iterator().hasNext()){
            System.out.print("["+sycQueue.size()+",  "+sycQueue.poll()+"], ");
        }
        System.out.println("\npQueue: ");
        while(pQueue.iterator().hasNext()){
            System.out.print("["+pQueue.size()+",  "+pQueue.poll()+"], ");
        }
        System.out.println("\n dQueue: ");
        //没到delay时间始终无法取出,因此会进入死循环
        System.out.println(Arrays.toString(dQueue.toArray()));
        while(dQueue.iterator().hasNext()){
            Thread.sleep(2000);
            System.out.print("["+dQueue.size()+",  "+dQueue.poll(5,TimeUnit.NANOSECONDS)+"], ");
        }
        System.out.println(dQueue.peek());
        System.out.println(clQueue.poll());
        System.out.println(clQueue.peek());
        //Removes all available elements from this queue and adds them to the given collection
        System.out.println(abQueue.drainTo(set));
        //System.out.println(clQueue.element());//NoSuchElementException




    }
}
