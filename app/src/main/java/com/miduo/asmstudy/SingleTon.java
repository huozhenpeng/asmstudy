package com.miduo.asmstudy;

public class SingleTon {

    @ASMTest
    public static void  doSomeThing()
    {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
