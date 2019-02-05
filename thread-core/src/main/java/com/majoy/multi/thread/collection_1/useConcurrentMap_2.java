package com.majoy.multi.thread.collection_1;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;

public class useConcurrentMap_2 {
    public static void main(String[] args) {
        //阅读该类的size(),内部对多个段进行size的求和
        ConcurrentHashMap<String,String> conHashMap = new ConcurrentHashMap();
        //求和是对 填充行CounterCell 长度进行累加
        conHashMap.size();
        long n = Long.MAX_VALUE+1;
        int i = (n < 0L) ? 0 : (n > (long) Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) n;
        System.out.println(i);

        ConcurrentSkipListMap<String,String> concurrentSkipListMap = new ConcurrentSkipListMap();
        concurrentSkipListMap.size();

        ConcurrentLinkedQueue<String >clQueue = new ConcurrentLinkedQueue<>();
        clQueue.offer("test");

    }
}
