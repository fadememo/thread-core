package com.majoy.multi.thread.design_5.future_1;

import java.util.List;

/**
 * 数据包装类,注意这里通过连锁返回保证了泛型的一致
 */
public class FutureData implements Data<List> {
    private boolean isReady =false;
    private RealData realData;

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public RealData getRealData() {
        return realData;
    }

    public synchronized  void setRealData(RealData realData) {
        if(isReady){
            return;
        }
        //如果证实对象已经准备好,就认为数据准备完成
        this.realData = realData;
        isReady = true;
        //唤醒
        notify();
    }


    public FutureData() {
    }
    public FutureData(boolean isReady, RealData realData) {
        this.isReady = isReady;
        this.realData = realData;
    }

    @Override
    public synchronized List getData() {
        while(!isReady){
            try {
                //数据尚未准备好时候,挂起
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.realData.getData();
    }
}
