package com.miduo.asmstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    int time=200;

    @ASMTest
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long var1=System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long var3=System.currentTimeMillis();
        if(var3-var1>time)
        {
            System.out.println("====class:" +Thread.currentThread().getStackTrace()[2].getClassName()+ "=======method:" + Thread.currentThread().getStackTrace()[2].getMethodName() + "===time:" + (var3 - var1) + "ms");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SingleTon.doSomeThing();
    }
}
