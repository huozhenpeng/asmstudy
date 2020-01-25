package com.miduo.asmstudy;

import org.junit.Test;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ASMUnitTest {

    static class MyClassVisitor extends ClassVisitor
    {


        int api;
        public MyClassVisitor(int api) {
            super(api);
            this.api=api;
        }

        public MyClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
            this.api=api;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

            //访问到每一个方法时会调用一次
          MethodVisitor methodVisitor=super.visitMethod(access, name, descriptor, signature, exceptions);

          return new MyMethodVisitor(api,methodVisitor,access,name,descriptor);

        }
    }

    static  class MyMethodVisitor extends AdviceAdapter{

        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        boolean inject=false;
        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            System.out.println(getName()+"   "+descriptor);
            if("Lcom/miduo/asmstudy/ASMTest;".equals(descriptor))
            {
                inject=true;
            }
            return super.visitAnnotation(descriptor, visible);
        }

        int s;
        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();

            if(!inject)
            {
                return;
            }
            invokeStatic(Type.getType("Ljava/lang/System;"),new Method("currentTimeMillis","()J"));
            //这个s表示的是索引，并不是本地变量的类型
            s=newLocal(Type.LONG_TYPE);
            //用一个本地变量接收上一步执行的结果
            storeLocal(s);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            if(!inject)
            {
                return;
            }
                visitIntInsn(BIPUSH,10);
                visitVarInsn(ISTORE,1);

                visitInsn(ICONST_5);
                visitVarInsn(ISTORE,2);

                visitVarInsn(ILOAD,1);
                visitVarInsn(ILOAD,2);



                Label label=new Label();
                visitJumpInsn(IF_ICMPLE,label);
                getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"));
                visitLdcInsn("111111111111");

                invokeVirtual(Type.getType("Ljava/io/PrintStream;"),new Method("println","(Ljava/lang/String;)V"));

                Label end=new Label();
                visitJumpInsn(GOTO,end);

                visitLabel(label);
                //点进去看一下，只能用这个Opcodes.F_NEW
                visitFrame(Opcodes.F_NEW,2,new Object [] {Opcodes.INTEGER,Opcodes.INTEGER},0,null);
                getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"));
                visitLdcInsn("222222222222");
                invokeVirtual(Type.getType("Ljava/io/PrintStream;"),new Method("println","(Ljava/lang/String;)V"));


                //return
                visitLabel(end);
                visitFrame(Opcodes.F_NEW,0,null,0,null);

                //经过测试在onMethodExit方法中不能调用这个代码，否则死循环
//                visitInsn(Opcodes.RETURN);
//                visitMaxs(2,2);


        }


        @Override
        public void visitInsn(int opcode) {
            super.visitInsn(opcode);
        }
    }



    @Test
    public void getPath()
    {
        //user.dir指定了当前的路径
        //输出结果：/Users/huozhenpeng/Desktop/study/ASMStudy/app
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void test() throws Exception {


        //获取class数据
        FileInputStream fileInputStream=new FileInputStream(new File("src/test/java/com/miduo/asmstudy/InjectTest.class"));

        //class分析器
        ClassReader classReader=new ClassReader(fileInputStream);

        //栈帧
        ClassWriter classWriter=new ClassWriter(ClassWriter.COMPUTE_FRAMES);


        //执行分析  classvisitor类似于设置onclick，一个回调
        classReader.accept(new MyClassVisitor(Opcodes.ASM7,classWriter),ClassReader.EXPAND_FRAMES);



        //执行了插桩之后的字节码文件
        byte[] bytes=classWriter.toByteArray();
        //对字节码进行替换
        FileOutputStream fileOutputStream=new FileOutputStream(new File("src/test/java/com/miduo/asmstudy/InjectTest_c.class"));
        fileOutputStream.write(bytes);
        fileInputStream.close();


    }
}
