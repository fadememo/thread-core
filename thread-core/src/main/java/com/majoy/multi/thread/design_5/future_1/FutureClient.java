package com.majoy.multi.thread.design_5.future_1;

public class FutureClient {
    public Data request(final String queryStr){
        FutureData futureData = new FutureData();
        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                //new 的过程就是根据参数异步的去查询的过程
                RealData realData = new RealData(queryStr);
                //设置结果给包装类对象
                futureData.setRealData(realData);
            }
        }).start();
        return futureData;
    }
}
