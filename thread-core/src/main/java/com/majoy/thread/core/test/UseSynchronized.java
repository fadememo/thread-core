package com.majoy.thread.core.test;

import java.util.ArrayList;
import java.util.List;

public class UseSynchronized {
    public static List<String>list = new ArrayList<>();
    public static int count = 0 ;

    public String insertData(String s){
        /*if(list.add(s)){
            return s;
        }else{
            return  s+"插入失败";
        }*/
        list.add(s);
        return s;
    }
   /* public synchronized String insertData(String s){
        if(list.add(s)){
            return s;
        }else{
            return s+"插入失败";
        }
    }*/
    public void testSycn1(){
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                for(int i = 0;i<10;i++){
                    System.out.println("线程: "+this.getName()+"在位置: "+list.size() +" ,insert data "+new UseSynchronized().insertData(" '"+(count++)+"' " ));
                }
            }
        };
        Thread t2 = new Thread("t2"){
            @Override
            public void run() {
                for(int i = 0;i<10;i++){
                    System.out.println("线程: "+this.getName()+"在位置: "+list.size()+" ,insert data "+new UseSynchronized().insertData(" '"+(count++)+"' " ));
                }
            }
        };
        Thread t3 =  new Thread("t3"){
            @Override
            public void run() {
                for(int i = 0;i<10;i++){
                    System.out.println("线程: "+this.getName()+"在位置: "+list.size()+" ,insert data "+new UseSynchronized().insertData(" '"+(count++)+"' " ));
                }
            }
        };
        long start = System.currentTimeMillis();
        long end = 0;
        t1.start();
        t2.start();
        t3.start();
        while(true){
            if((t1.getState()== Thread.State.TERMINATED)&&(t2.getState()== Thread.State.TERMINATED)&&(t3.getState()== Thread.State.TERMINATED)){
                end = System.currentTimeMillis();
                break;
            }
        }
        System.out.println("运行时间长度为:" + (end-start));
    }
    public static void main(String[] args) {
        final UseSynchronized u = new UseSynchronized();
        //u.testSycn1();
        new Thread(){
            @Override
            public void run() {
                System.out.println("线程: "+this.getName()+"在位置: "+list.size()+" ,insert data "+new UseSynchronized().insertData(" '"+(count++)+"' " ));
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                System.out.println("线程: "+this.getName()+"在位置: "+list.size()+" ,insert data "+new UseSynchronized().insertData(" '"+(count++)+"' " ));
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                System.out.println("线程: "+this.getName()+"在位置: "+list.size()+" ,insert data "+new UseSynchronized().insertData(" '"+(count++)+"' " ));
            }
        }.start();

    }


}
