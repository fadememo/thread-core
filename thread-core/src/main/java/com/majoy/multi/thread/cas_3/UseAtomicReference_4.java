package com.majoy.multi.thread.cas_3;

import java.util.concurrent.atomic.AtomicReference;

class ShaDiaoWangyou{
    private int age;
    private String experience;
    private long bullSkill ;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public long getBullSkill() {
        return bullSkill;
    }

    public void setBullSkill(long bullSkill) {
        this.bullSkill = bullSkill;
    }

    public ShaDiaoWangyou() {
    }
    public ShaDiaoWangyou(int age, String experience, long bullSkill) {
        this.age = age;
        this.experience = experience;
        this.bullSkill = bullSkill;
    }

    @Override
    public String toString() {
        return "ShaDiaoWangyou{" +
                "age=" + age +
                ", experience='" + experience + '\'' +
                ", bullSkill=" + bullSkill +
                '}';
    }
}

/**
 *
 */
public class UseAtomicReference_4 {
    private static ShaDiaoWangyou sw ;//常规不会开始就初始化
    private static ShaDiaoWangyou sw2 ;//常规不会开始就初始化
    //将沙雕网友包装一下
    private static AtomicReference<ShaDiaoWangyou>aRsw ;
    //该类存在一个弱cs方法,不清楚其中区别,暂留

    /**
     * CAS操作保证了对对象的多个修改同时成功或者同时失败
     */
    static class Task1 implements Runnable{
        @Override
        public void run() {
            sw.setAge(20);
            sw.setExperience("无所不会");
            System.out.println(sw);
        }
    }
    static class Task2 implements Runnable{
        @Override
        public void run() {
            sw.setAge(21);
            sw.setExperience("有一点点不会");
            System.out.println(sw);
        }
    }
    //准备俩沙雕网友的原子性操作
    static class Task3 implements Runnable{
        @Override
        public void run() {
            boolean bool = aRsw.compareAndSet(
                    aRsw.get(),
                    new ShaDiaoWangyou(20,"无所不会",1)
            );
            System.out.println("修改成功?"+bool+"\t"+aRsw.get());
        }
    }
    static class Task4 implements Runnable{
        @Override
        public void run() {
            boolean bool = aRsw.compareAndSet(
                    aRsw.get(),
                    new ShaDiaoWangyou(21,"有一点点不会",1)
            );
            System.out.println("修改成功?"+bool+"\t"+aRsw.get());
        }
    }
    public static void main(String[] args) {
        sw  = new ShaDiaoWangyou(1,"现想",1);
        sw2  = new ShaDiaoWangyou(1,"现想",1);
        aRsw = new AtomicReference<ShaDiaoWangyou>(sw2);
        System.out.println(sw);
        Thread t1 = new Thread(new Task1());
        Thread t2 = new Thread(new Task2());
        Thread t3 = new Thread(new Task3());
        Thread t4 = new Thread(new Task4());

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sw);
        System.out.println(aRsw.get());
        /* 对多个属性的修改会导致同步问题出现
        * ShaDiaoWangyou{age=20, experience='有一点点不会', bullSkill=1}
        * ShaDiaoWangyou{age=20, experience='有一点点不会', bullSkill=1}
        * */

    }

}
