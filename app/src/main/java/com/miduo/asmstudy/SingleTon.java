package com.miduo.asmstudy;

public class SingleTon {

    private static  SingleTon singleTon=new SingleTon();

    private SingleTon(){}

    public static SingleTon getInstance()
    {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return singleTon;
    }

    public static void doSomeThing()
    {
        long var1=System.currentTimeMillis();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long var3=System.currentTimeMillis();
        System.out.println("====class:" +Thread.currentThread().getStackTrace()[2].getClassName()+ "=======method:" + Thread.currentThread().getStackTrace()[2].getMethodName() + "===time:" + (var3 - var1) + "ms");
    }
}
