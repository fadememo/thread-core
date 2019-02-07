package com.majoy.multi.thread.juc_4;

import java.util.List;
import java.util.concurrent.*;

/**
 * java 7 提供的 用于并行任务的一个框架
 *  重点有:
 *  重要的类 RecursiveAction; RecursiveTask
 *  重要的成员变量:threadHold阈值
 *  重要的方法:fork;join
 */
class ChessBoard extends RecursiveTask<Double>{
    int start;
    int end ;
    private static final int THREADHOLD = 2;

    public ChessBoard() {
        super();
    }

    public ChessBoard(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 该方法简化了递归逻辑,让工程师可以直接套用米板就能实现递归,先判断阈值,根据阈值
     * @return
     */
    @Override
    protected Double compute() {
        double total = 0;
        boolean canCompete = (end-start)<THREADHOLD;
        if(canCompete){
            for(int i = start;i<=end;i++) {
                //total = 2 ^ start + 2 ^ end;//卧槽,居然不是这种表达式
                //total = total + (2<<(i-1));
                total = total + Math.pow(2,i);
            }
        }else{
            int middle = (int)( (start+end)/2) ;
            //新建线程
            ChessBoard leftTask = new ChessBoard(start,middle);
            ChessBoard righeTask = new ChessBoard(middle+1,end);
            //拆分任务
            leftTask.fork();
            righeTask.fork();
            //任务执行完毕获取结果
            double leftRet = leftTask.join();
            double rightRet = righeTask.join();
            total = leftRet+rightRet;
        }
        return total;//之前把return放到循环外部了,结果为什么是96?
    }

}

/**
 * 计划用来实现寻找所有的质数
 * 简单的粒度划分法是:排除所有偶数 然后 加上2 ,然后按照整除3 进行3等分,3的倍数跳过
 */
class NumSeeker extends RecursiveAction{
    private static List<Integer> numList;
    public NumSeeker() {
        super();
    }

    @Override
    protected void compute() {


    }
}
public class UseForkJoin_5 {
    /**
     * 计划实现两个功能
     * 1,求出所有10000以内的质数,貌似有个技巧是质数???
     * 2,棋盘放粮食问题
     *
     */
    public static void main(String[] args) {
        double total = 0;
        int start = 1;
        int end = 64;//不能左移超过范围
        List<Integer> primeNums = null;

        ForkJoinPool pool = new ForkJoinPool();

        ChessBoard board = new ChessBoard(start,end);
        /**
         * 已知 2^1+....2^n = 2^n*2-1
         */

        Future<Double>result = pool.submit(board);

        try {
            total= result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(total);//2.147483646E9 = .147483646*10^9
        //System.out.println((1<< (end+1) )-1 -(1+total));
        System.out.println((1<<(end+1) ));
        System.out.println(Math.pow(2,end+1));

    }




}
