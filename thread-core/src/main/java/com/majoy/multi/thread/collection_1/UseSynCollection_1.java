package com.majoy.multi.thread.collection_1;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UseSynCollection_1 {
    //foreach循环 ,出现ConcurrentModificationException
    public Vector m1(Vector<String> v){
        for (String temp:v) {
            if("3".equals(temp)){
                v.remove(temp);
            }
        }
        return v;
    }
    //fori循环
    public Vector m2(Vector v){
        Iterator<String>iterator = v.iterator();
        while(iterator.hasNext()) {
            String s = iterator.next();
            if("3".equals(s)){
                //iterator.remove();该方法不报错
                v.remove(s);
            }
        }
        return v;
    }
    //fori循环
    public Vector m3(Vector v){
        for (int i = 0; i <v.size() ; i++) {
            if("3".equals(v.get(i))){
                v.remove(i);
            }
        }
        return v;
    }
    public static void main(String[] args) {
        //线程安全 意味着 容器内部有锁,可以互斥访问 vector 和 hashtable
        Vector<String>v = new Vector<>(Arrays.asList("1","2","3"));
        List<String>list = new ArrayList<>(v);
        //获取一个同步的容器
        Collection<String> collection = Collections.synchronizedCollection(list);
        UseSynCollection_1 test = new UseSynCollection_1();
        /*会抛出ConcurrentModificationException */
        //Collection<String> ret1 = test.m1(v);
        /*会抛出ConcurrentModificationException */
        //Collection<String> ret1 = test.m2(v);
        Collection<String> ret1 = test.m3(v);
        System.out.println(ret1);
        /*会抛出ConcurrentModificationException  */
        Iterator iterator = v.iterator();
        //直接移除会 IllegalStateException,iter的remove()删除的是最近操作过的元素
        iterator.next();
        iterator.remove();
        System.out.println(v);
        ConcurrentHashMap cHashMap = new ConcurrentHashMap();
    }
}
