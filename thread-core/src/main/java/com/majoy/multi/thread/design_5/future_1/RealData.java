package com.majoy.multi.thread.design_5.future_1;

import java.util.Arrays;
import java.util.List;

public class RealData implements Data<List>{
    private List<String> data = null;

    public RealData(){
    }
    public RealData(String queryStr) {
        System.out.println("根据queryStr查询结果集");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        data = Arrays.asList("majoy","is","shadiao","dalao");
    }
    public void setData(List<String> data) {
        this.data = data;
    }
    /**
     * 返回查询结果及
     * @return
     */
    @Override
    public List getData() {
        return data;
    }
}