package com.majoy.multi.thread.design_5.future_1;

import java.util.List;

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
