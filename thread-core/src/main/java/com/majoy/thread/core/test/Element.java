package com.majoy.thread.core.test;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

//这里为毛要这样写才行? 不能Comparable<Element>
public class Element implements Delayed,Comparable<Delayed> {
    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Element() {
    }
    public Element(String name ) {
        this.name = name;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return 1;
    }

    @Override
    public int compareTo(Delayed o) {
        return this.name.compareTo(((Element)o).name );
    }

    @Override
    public String toString() {
        return "Element{" +
                "name='" + name + '\'' +
                '}';
    }
}
