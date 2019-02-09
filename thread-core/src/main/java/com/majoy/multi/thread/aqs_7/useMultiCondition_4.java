package com.majoy.multi.thread.aqs_7;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 总结:
 * 1、l.lock()和l.unlock()完全代替synchronized()
 * 2、await()、signal()、signalAll()与wait()、notify()、nitofyAll()功能完全相同。
 * 3、可以有多把锁。不同的锁专门用于锁不同的线程，比较明了，不像synchronized，只能用一把锁
 * 4,对没有等待的condition执行signal()是无效的但可以
 * 5,方法4说明,发出唤醒通知并不意味着阻塞线程开始执行,
 * 而只是调整到就绪状态,等待锁被释放就立即执行,
 * 也说明lock.lock只是锁定同步代码块区域,
 * 后续的逻辑完全是靠condition实现
 */
public class useMultiCondition_4 {
    private ReentrantLock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    /**
     * 执行await
     */
    public void process1(){
        //t1发现锁没被独占,占用锁并进入同步代码块
        lock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"进入m1等待");
            //await释放对锁的占用
            /**
             * Disables the current thread for thread scheduling
             * purposes unless the
             * permit is available.停止当前线程执行计划直到执行许可可用
             *
             * <p>If the permit is available
             * then it is consumed and the call returns
             * immediately; otherwise如果许可可用,那么await状态被消去并且返回即时执行调用
             * the current thread becomes disabled for thread scheduling
             * purposes and lies dormant until one of three things happens:
             * for则当前线程停止并进入休眠,除非以下三种情况
             * <ul>其他线程制定当前县城为目标 唤醒,打断,调用不和逻辑返回?
             * <li>Some other thread invokes {@link #unpark unpark} with the
             * current thread as the target; or
             *
             * <li>Some other thread {@linkplain Thread#interrupt interrupts}
             * the current thread; or
             *
             * <li>The call spuriously (that is, for no reason) returns.
             * </ul>
             *该方法 不会报告因为什么原因返回执行调用,调用者引当再次检查,是什么导致首次park
             * <p>This method does <em>not</em> report which of these caused the
             * method to return. Callers should re-check the conditions which caused
             * the thread to park 停置 in the first place. Callers may also determine,
             * for example, the interrupt status of the thread upon return.
             *
             * @param blocker the synchronization object responsible for this
             *        thread parking
             * @since 1.6
             */
            condition1.await();
            System.out.println("线程"+Thread.currentThread().getName()+"继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 执行
     */
    public void process2(){
        lock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"进入m2等待");
            condition1.await();
            System.out.println("线程"+Thread.currentThread().getName()+"继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void process3(){
        lock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"进入m3等待");
            condition2.await();
            System.out.println("线程"+Thread.currentThread().getName()+"继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 执行
     */
    public void process4(){
        lock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"进入");
            System.out.println(Thread.currentThread().getName()+"当前线程唤醒通知~~~");
            condition1.signalAll();
            Thread.sleep(3000);
            /**
             * 此处说明,发出唤醒通知并不意味着阻塞线程开始执行,
             * 而只是调整到就绪状态,等待锁被释放
             */
            System.out.println("线程"+Thread.currentThread().getName()+"继续执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void process5(){
        lock.lock();
        try {
            System.out.println("线程"+Thread.currentThread().getName()+"进入");
            System.out.println(Thread.currentThread().getName()+"当前线程唤醒通知");
            condition2.signalAll();
            System.out.println("线程"+Thread.currentThread().getName()+"继续执行");
        }  finally {
            lock.unlock();
        }
    }

    /**
     * 预期输出 吗
     * t1,t2,t3等待
     * t4唤醒,t4,t1,t2继续
     * t5唤醒,t5,t3继续
     * @param args
     */
    public static void main(String[] args) {
        useMultiCondition_4 uc = new useMultiCondition_4();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.process1();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.process2();
            }
        },"t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.process3();
            }
        },"t3");
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.process4();
            }
        },"t4");
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.process5();
            }
        },"t5");

        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t4.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t5.start();

    }

}
