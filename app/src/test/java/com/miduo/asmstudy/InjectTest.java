package com.miduo.asmstudy;


public class InjectTest {

    private int f;
    @ASMTest
    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(1000);
        int a=10;
        int b=5;

        if(a>b)
        {
            System.out.println("111111111111");

        }
        else
        {
            System.out.println("22222222222 ");
        }
    }

    public void checkAndSetF(int f) {
        if (f >= 0) {
            this.f = f;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public InjectTest() throws InterruptedException {
        long s=System.currentTimeMillis();
        Thread.sleep(1000);
        long e=System.currentTimeMillis();


        System.out.println("====class:"+Thread.currentThread().getStackTrace()[1].getClassName()+"=======method:"+Thread.currentThread().getStackTrace()[1].getMethodName()+"===time:"+(e-s)+"ms");
    }
}
