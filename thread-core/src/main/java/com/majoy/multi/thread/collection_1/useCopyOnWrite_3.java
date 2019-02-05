package com.majoy.multi.thread.collection_1;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class useCopyOnWrite_3 {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<String>();
        cowList.add("majoy");
        CopyOnWriteArraySet<String >cowSet = new CopyOnWriteArraySet<>();


    }
}
