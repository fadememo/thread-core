package com.majoy.multi.thread.project_8;

import java.util.concurrent.ExecutorService;

/**
 1 我们学习完了并发编程，那么这个时候就是考验同学们的实际能力的时候了，
 请打开你的idea，进行编写一道题，封装一个线程工具类，把自己想象成一名架构师，
 让别人使用你的工具类去进行并发编程的开发工作，展开你的想象，开阔你的思维，
 volatile、 lock、condition、 atomic、countlatch、 barrier、 信号量 通通都可以使用！
 （例如，
 1 实现投递任务的功能，并且能在任务投递失败时给出拒绝策略
 --拒绝策略采用semphore 并 数值可变
 --并线程进行优先级 策略设置
 -- 优先级策略设置应该使用有优先级的 队列,允许通过调整 compator方法 调整优先级策略
 --参数设置应该简单粗暴各具特色,以足够便利
 2 根据实际场景，能够设置线程数量的最优策略
 --底层使用有界阻塞队列的 自定义线程池,上层使用一个数据运算弹性调整
  线程coresize和maxsize和queue
 3 能够实现多个任务并行执行，最终返回统一结果
 ---应该实现多个并发功能并对调用者透明,应该有cyclibarrier等功能
 4 在线程执行失败时，能够提供优秀的日志和轨迹跟踪
 ---要建立一个线程工厂类,用于封装线程,并对线程进行跟踪和aop
 */
class A{
    int a ;
    public A() {
    }
}
class B extends A{
    public B() {
        //int a = 20;  //则输出啊=0
        a = 20; //则输出a=20;
    }
}
@interface Type{
}
public class Main {
    /**
     maxmemory是指总的可用物理内存减掉系统所占用的内存所得；
     freeMemory()
     Returns the amount of free memory in the Java Virtual Machine.
     maxMemory()
     Returns the maximum amount of memory that the Java virtual machine will attempt to use.
     totalMemory()
     Returns the total amount of memory in the Java virtual machine.
     freeMemory:113 MB ,返回剩余的可用内存
     totalMemory:117 MB,返回虚拟机尝试获取的最大内存
     maxMemory1732 MB ,java虚拟机全部的内存
     * @param args
     */
    public static void main(String[] args) {
        //System.out.println(Runtime.getRuntime().availableProcessors());
        //System.out.println("freeMemory:"+Runtime.getRuntime().freeMemory()/1024/1024);
        //System.out.println("totalMemory:"+Runtime.getRuntime().totalMemory()/1024/1024);
        //System.out.println("maxMemory"+Runtime.getRuntime().maxMemory()/1024/1024);
        //B b = new B();
        //System.out.println(b.a);
        ThreadPoolUtil u = new ThreadPoolUtil();
        ExecutorService service = u.newFixedThreadPool(5);

    }

}
