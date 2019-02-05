package com.majoy.thread.core.test;

import org.omg.CORBA.PRIVATE_MEMBER;

class Base2{
     A a= new A("join Base");
    static {
        System.out.println("static Base");
    }

    public Base2() {
        System.out.println("create Base");
    }
}
class A{
    private  String name ;
    static {
        System.out.println("a static");
    }
    public A(String name) {
        System.out.println(name+" is created");
//        System.out.println(this.name+" is created");
    }
}

public class ShowOutput2 extends Base2 {
    A a= new A("join ShowOutput");
    static {
        System.out.println("static ShowOutput");
    }

    public ShowOutput2() {
        System.out.println("create ShowOutput");
    }

    public static void main(String[] args) {
        new ShowOutput2();
    }
}
