package com.majoy.multi.thread.collection_1;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class Student implements Delayed{
    private int Id;
    private String name;
    private final TimeUnit timeUnit = TimeUnit.SECONDS;
    private long endTime ;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Student(int id, String name, int endTime) {
        Id = id;
        this.name = name;
        this.endTime = endTime;
    }
    public Student() {
    }

    //用来判断时间是否已经到期
    @Override
    public long getDelay(TimeUnit unit) {
        return endTime-System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return this.getDelay(timeUnit) - o.getDelay(timeUnit)>0?1:-1;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", endTime=" + endTime +
                '}';
    }
}
public class Examnation_6 implements Runnable{
    //List<Student>dQueue = new ArrayList<>();
    private DelayQueue<Student> delayQueue= new DelayQueue();
    private boolean start = true;

    public void startExam(Student s){
        delayQueue.add(s);
        System.out.println(s+"开始考试");
    }
    public void endExam(Student s){
        System.out.println(s+"考试结束");
    }
    @Override
    public void run() {
        while(start){
            try {
                Student s = delayQueue.take();
                endExam(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Examnation_6 examnationRoom = new Examnation_6();
        System.out.println("考试开始");
        Thread Exam = new Thread(examnationRoom);
        Exam.start();
        try {
            examnationRoom.startExam(new Student(4,"majoy4",4000));
            Thread.sleep(1000);
            examnationRoom.startExam(new Student(3,"majoy3",3000));
            Thread.sleep(2000);
            examnationRoom.startExam(new Student(1,"majoy1",1000));
            Thread.sleep(2000);
            examnationRoom.startExam(new Student(2,"majoy2",2000));
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
