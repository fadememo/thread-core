package com.bfxy.thread.core.collection;

import java.util.*;

public class UseSyncCollection {
    
    // 出现java.util.ConcurrentModificationException
    public Collection<String> m1(Vector<String> list) {
        for (String temp : list) {
            if ("3".equals(temp)) {
                list.remove(temp);
            }
        }
        return list;
        
    }
    // 出现java.util.ConcurrentModificationException
    public Collection<String> m2(Vector<String> list) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            if ("3".equals(temp)) {
                list.remove(temp);
            }
        }
        return list;
    }
    //successful!
    public Collection<String> m3(Vector<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if ("3".equals(list.get(i))) {
                list.remove(i);
            }
        }
        return list;
    }
    
    
    public static void main(String[] args) {
    	
    	Vector v = new Vector<>();
    	v.add("1");
    	v.add("2");
    	v.add("3");
    	UseSyncCollection test = new UseSyncCollection();
//		Collection<String> ret1 = test.m1(v);
//		System.err.println(ret1.toString());

//        Collection<String> ret2 = test.m2(v);
//        System.err.println(ret2.toString());
    	
        Collection<String> ret3 = test.m3(v);
        System.err.println(ret3.toString());
        
        
        List<String> list = new ArrayList<>();
        Collections.synchronizedCollection(list);
        
    }
}