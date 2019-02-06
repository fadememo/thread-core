package com.majoy.multi.thread.design_5.future_1;

import java.util.concurrent.Callable;

/**
 * 模拟实现future设计模式,关键是三种角色
 * 客户端:发起请求,送出参数
 * future客户端:先返回一个空壳,然后继续执行
 * 数据包装类:包装数据为壳
 * 真实数据类:存放数据
 *
 *
 */



public class FutureSimulate implements Callable<FutureData> {
    private String queryStr;
    @Override
    public FutureData call() throws Exception {
        System.out.println("执行业务逻辑");
        FutureData futureData = request("分线程去查询结果");
        return futureData;
    }
    public FutureData request(final String queryStr){
        FutureData futureData = new FutureData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }
        }).start();
        return futureData;
    }

    public static void main(String[] args) {
        FutureSimulate futureSimulate = new FutureSimulate();
        FutureData futureData = null;
        try {
            futureData = futureSimulate.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("主线程继续S执行,GO,GO,GO");

        System.out.println(futureData.getData());

    }

}




