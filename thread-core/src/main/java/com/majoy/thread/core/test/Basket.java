package com.majoy.thread.core.test;

public class Basket<T> {
    final static int capacicity = 2;//篮子的空格数//final修饰的基本类型必须初始化
    int count = 0;//计数装入个数
    T[] col = (T[])new Object[capacicity];
    //饿汉式单实例
    //private static Basket<T> basket = new Basket<T>();
    private Basket() {
    }

    /**
     * 定义一个私有的内部类，在第一次用这个嵌套类时，会创建一个实例。而类型为SingletonHolder的类，
     * 只有在Singleton.getInstance()中调用，由于私有的属性，他人无法使用SingleHolder，不调用Singleton.getInstance()就不会创建实例。
     * 优点：达到了lazy loading的效果，即按需创建实例。
     */
    private static class BasketHolder{
        private final static Basket basket = new Basket();
    }
    public static Basket getBasket(){
        return BasketHolder.basket;
    }
    public synchronized  boolean put(T t){
        boolean flag = false;
        if(getBasket().capacicity-count>0){//有剩余
            col[count++] = t;
            flag = true;
        }
        return flag;
    }
    //但是如果取和放同时进行,怎么办?
    public synchronized  T remove(){
        if(count>0){//有剩余
            return (T)(col[count++]) ;
        }
        return null;
    }

}
