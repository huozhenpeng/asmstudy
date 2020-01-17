package com.miduo.asmstudy;


public class InjectTest {

    @ASMTest
    public static void main(String[] args) throws InterruptedException {

        long s=System.currentTimeMillis();
        Thread.sleep(1000);
        long e=System.currentTimeMillis();


        System.out.println("====class:"+Thread.currentThread().getStackTrace()[1].getClassName()+"=======method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+"===time:"+(e-s)+"ms");
    }

    public InjectTest()
    {

    }
}
