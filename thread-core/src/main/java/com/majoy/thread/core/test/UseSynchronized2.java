package com.majoy.thread.core.test;

import java.util.ArrayList;
import java.util.List;


class InsertData{
    List<String> list = new ArrayList();
    private ThreadLocal<Object>threadLocal = new ThreadLocal<>();
    int count ;
    //方法1 方法置为 synchronized
    /*synchronized  void  insertData(Thread thread){
        for(int i=0;i<10;i++){
            String s = "元素: "+count++;
            list.add(s);
            System.out.println(thread.getName() +" 插入 "+s);
        }
    }*/
    //方法2 对象锁
    void  insertData(Thread thread){
       synchronized (this){
           for(int i=0;i<10;i++){
               String s = "元素: "+count++;
               list.add(s);
               System.out.println(thread.getName() +" 插入 "+s);
           }
       }
    }
}
public class UseSynchronized2{
    public static void main(String[] args) {
        final InsertData insert = new InsertData();
        new Thread(){
            @Override
            public void run() {
                insert.insertData(this);
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                insert.insertData(this);
            }
        }.start();
    }
}

