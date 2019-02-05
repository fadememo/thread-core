package com.majoy.thread.core.test;

class Base{
    static {
        System.out.println("static Base");
    }

    public Base() {
        System.out.println("create Base");
    }
}
public class ShowOutput extends Base {
    static {
        System.out.println("static ShowOutput");
    }

    public ShowOutput() {
        System.out.println("create ShowOutput");
    }

    public static void main(String[] args) {
        new ShowOutput();
    }
}
