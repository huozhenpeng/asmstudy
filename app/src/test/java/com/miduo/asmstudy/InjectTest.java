package com.miduo.asmstudy;


public class InjectTest {

    @ASMTest
    public static void main(String[] args) throws InterruptedException {

        long s=System.currentTimeMillis();
        Thread.sleep(1000);
        long e=System.currentTimeMillis();


        System.out.println("e-s="+(e-s));
    }

    public InjectTest()
    {

    }
}
