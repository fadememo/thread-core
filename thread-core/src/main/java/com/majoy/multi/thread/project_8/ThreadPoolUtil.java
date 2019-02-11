package com.majoy.multi.thread.project_8;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class ThreadPoolUtil extends ThreadPoolExecutor implements ExecutorService{
    /**
     * 常量参数区
     */
    //固定容量线程池
    public static final int FIXEDTHREAD = 0;
    //随意的多线程
    public static final int RANDOMTHREAD = 1;
    //可回收的线程池
    public static final int CACHEDTHREAD = 2;
    //带循环执行的线程池
    public static final int TIMERTHREAD = 3;
    //带有定时的线程池
    public static final int DELAYEDTHREAD = 4;
    //自适应的线程池
    public static final int SELFADAPTIONTHREAD = 8;
    //纯粹自定义的线程池
    public static final int CUSTOMEDTHREAD = 9;
    //排在阻塞队列里面的线程个数
    private volatile  int queuedSize ;//这会导致频繁更新数字....性能
    //当前存活的线程个数,用来触发
    private volatile  int aliveSize;
    //存放线程和其属性信息,key是线程名(怎么保证唯一?),属性信息存放需要一个线程的包装类?
    // 不会超过1000个的元素存放并且频繁读写,使用cowWirt容器
    private ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    //貌似没法用
    private CountDownLatch countDownLatch = null;
    //这个貌似也灭用
    private CyclicBarrier cyclicBarrier = null;
    //
    private ExecutorService executorService;
    //
    private ScheduledExecutorService scheduledExecutorService;




    /**
     * 构造方法1 通用配置
     * param corePoolSize 4
     * param maximumPoolSize
     * param keepAliveTime
     * param unit
     * param workQueue 这里的比较器方法需要传进去等待时间,怎么处理好
     * freeMemory:113 MB ,返回剩余的可用内存
     * totalMemory:117 MB,返回虚拟机尝试获取的最大内存
     * maxMemory1732 MB ,java虚拟机全部的内存
     */
    public ThreadPoolUtil(int poolType,int coreSize,long delay,long period) {
        //按照计算密集型 core~ 2*core,(IO密集型 阻塞系数0.9,10*core)来设置
        switch(poolType){
            case FIXEDTHREAD:
                executorService = Executors.newFixedThreadPool(coreSize);
                break;
            case RANDOMTHREAD:
                executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                        Runtime.getRuntime().availableProcessors()*10,
                        60L,
                        TimeUnit.SECONDS,
                        new PriorityBlockingQueue<>(Runtime.getRuntime().availableProcessors()));
            case
        }

    }
    /**
     * 构造方法2 : 自定义配置的线程池
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     * @param handler
     */
    public ThreadPoolUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
    /**
     *  返回实例方法一:返回一个固定数量线程池,阻塞队列是无界的
     */
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolUtil(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),null,null);
    }

    /**
     * 返回实例2:返回一个单实例线程,不能调用私有内部类,这里需要考虑一下怎么保证单实例串行
     * 单线程化线程池(newSingleThreadExecutor)的优点，
     * 串行执行所有任务。如果这个唯一的线程因为异常结束，
     * 那么会有一个新的线程来替代它。此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
     * param parallelism
     * @return
     */
    public static ExecutorService newSingleThreadExecutor() {
        return new  FinalizableDelegatedExecutorService(
                new ThreadPoolUtil(1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(),null,null));
    }

    /**
     * 返回实例三:返回一个可重用的
     * 这个实例会根据需要，在线程可用时，重用之前构造好的池中线程。
     * 这个线程池在执行 大量短生命周期的异步任务时（many short-lived asynchronous task），
     * 可以显著提高程序性能。调用 execute 时，可以重用之前已构造的可用线程，?怎么实现的?
     * 如果不存在可用线程，那么会重新创建一个新的线程并将其加入到线程池中。
     * 如果线程超过 60 秒还未被使用，就会被中止并从缓存中移除。
     * 因此，线程池在长时间空闲后不会消耗任何资源。
     * @return
     */
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolUtil(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),null,null);
    }
    /**
     * 功能区:
     *  功能有 执行,提交,关闭,
     *  增加一个 定时执行,周期执行,countDownLatch 和cyclibarrier 和semphore 功能
     * @param command
     */
    @Override
    public void execute(Runnable command) {
        super.execute(command);
    }

    /**
     * 实现countDownLatch 的功能
     */
    public void CountDown(Runnable master,List<Runnable> workers,int workerNum) {
        if(workers==null||workers.size()<workerNum){
            throw new IllegalArgumentException("workers 数目不足,无法唤醒master线程");
        }
        countDownLatch = new CountDownLatch(workerNum);
        for(int i=0;i<workerNum;i++){
            execute(workers.get(i));//如果报错退出呢

        }
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return super.shutdownNow();
    }

    @Override
    protected void finalize() {
        super.finalize();
    }

    @Override
    public boolean remove(Runnable task) {
        return super.remove(task);
    }

    @Override
    public void purge() {
        super.purge();
    }

    /**
     *
     * 状态查询区
     * @return
     */
    @Override
    public boolean isShutdown() {
        return super.isShutdown();
    }

    @Override
    public boolean isTerminating() {
        return super.isTerminating();
    }

    @Override
    public boolean isTerminated() {
        return super.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return super.awaitTermination(timeout, unit);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(task, result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(task);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return super.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return super.invokeAny(tasks, timeout, unit);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return super.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return super.invokeAll(tasks, timeout, unit);
    }


    /**
     * getter,setter区
     * @param threadFactory
     */
    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        super.setThreadFactory(threadFactory);
    }

    @Override
    public ThreadFactory getThreadFactory() {
        return super.getThreadFactory();
    }

    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
        super.setRejectedExecutionHandler(handler);
    }

    @Override
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return super.getRejectedExecutionHandler();
    }

    @Override
    public void setCorePoolSize(int corePoolSize) {
        super.setCorePoolSize(corePoolSize);
    }

    @Override
    public int getCorePoolSize() {
        return super.getCorePoolSize();
    }

    @Override
    public boolean prestartCoreThread() {
        return super.prestartCoreThread();
    }

        @Override
    public void setMaximumPoolSize(int maximumPoolSize) {
        super.setMaximumPoolSize(maximumPoolSize);
    }

    @Override
    public int getMaximumPoolSize() {
        return super.getMaximumPoolSize();
    }

    @Override
    public void setKeepAliveTime(long time, TimeUnit unit) {
        super.setKeepAliveTime(time, unit);
    }

    @Override
    public long getKeepAliveTime(TimeUnit unit) {
        return super.getKeepAliveTime(unit);
    }

    @Override
    public BlockingQueue<Runnable> getQueue() {
        return super.getQueue();
    }



    @Override
    public int getPoolSize() {
        return super.getPoolSize();
    }

    @Override
    public int getActiveCount() {
        return super.getActiveCount();
    }

    @Override
    public int getLargestPoolSize() {
        return super.getLargestPoolSize();
    }

    @Override
    public long getTaskCount() {
        return super.getTaskCount();
    }

    @Override
    public long getCompletedTaskCount() {
        return super.getCompletedTaskCount();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * AOP 线程生命控制区
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    @Override
    protected void terminated() {
        super.terminated();
    }

    /**
     *  hash 和 toString
     * @return
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    /**
     * 引入几个内部类来 实现其他定时和守护的功能
     */
    /**
     * A wrapper class that exposes only the ThreadPoolUtil methods
     * of an ThreadPoolUtil implementation.
     */
    static class DelegatedExecutorService extends AbstractExecutorService {
        private final ExecutorService e;

        DelegatedExecutorService(ExecutorService executor) {
            e = executor;
        }

        public void execute(Runnable command) {
            e.execute(command);
        }

        public void shutdown() {
            e.shutdown();
        }

        public List<Runnable> shutdownNow() {
            return e.shutdownNow();
        }

        public boolean isShutdown() {
            return e.isShutdown();
        }

        public boolean isTerminated() {
            return e.isTerminated();
        }

        public boolean awaitTermination(long timeout, TimeUnit unit)
                throws InterruptedException {
            return e.awaitTermination(timeout, unit);
        }
    }
    static class FinalizableDelegatedExecutorService    extends ThreadPoolUtil.DelegatedExecutorService {
        FinalizableDelegatedExecutorService(ThreadPoolUtil  executor) {
            super(executor);
        }
        protected void finalize() {
            super.shutdown();
        }
    }

    /**
     * A wrapper class that exposes only the ScheduledExecutorService
     * methods of a ScheduledExecutorService implementation.
     */
    static class DelegatedScheduledExecutorService extends ThreadPoolUtil.DelegatedExecutorService implements ScheduledExecutorService {
        private final ScheduledExecutorService e;
        DelegatedScheduledExecutorService(ScheduledExecutorService executor) {
            super(executor);
            e = executor;
        }
        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return e.schedule(command, delay, unit);
        }
        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            return e.schedule(callable, delay, unit);
        }
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            return e.scheduleAtFixedRate(command, initialDelay, period, unit);
        }
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            return e.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }
    }
}
