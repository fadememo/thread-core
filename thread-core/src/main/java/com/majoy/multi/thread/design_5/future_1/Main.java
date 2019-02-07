package com.majoy.multi.thread.design_5.future_1;

import java.util.List;

/**
 * 模拟实现future设计模式,关键是三种角色
 * 客户端:发起请求,送出参数
 * future客户端:先返回一个空壳,然后继续执行
 * 数据包装类:包装数据为壳
 * 真实数据类:存放数据,内部异步的执行数据初始化
 */
public class Main {
    public static void main(String[] args) {
        FutureClient futureClient = new FutureClient();
        Data futureData = futureClient.request("查询结果集");
        System.out.println("执行主线程业务");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> realdata = (List<String>)futureData.getData();
        System.out.println(realdata);
    }
}
