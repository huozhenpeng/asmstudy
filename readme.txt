//测试一下分支修改：这是buildsrc_branch分支

这种方式就比较简单了：也比较容易调试

调试步骤：参考https://juejin.im/post/5b3b44105188251b134e4f56

    1、Android Studio中按照如下步骤操作：
      Menu → Run → Edit Configurations... → Add New Configuration → Remote → 自定义配置name → host: localhost → port: 5005 → OK

    2、./gradlew <任务名> -Dorg.gradle.daemon=false -Dorg.gradle.debug=true
        例如： ./gradlew assembleDebug -Dorg.gradle.daemon=false -Dorg.gradle.debug=true

    3、切换到第一步新建的remote任务，执行后面的debug即可



关于在方法内部获取Java   Android中的方法名：

    参考：https://mupceet.com/2017/04/java&android-get-current-method-name/



经测试：这个才是通用的方法，在普通方法和静态方法都适用

        System.out.println("====class:" +Thread.currentThread().getStackTrace()[2].getClassName()+ "=======method:" + Thread.currentThread().getStackTrace()[2].getMethodName() + "===time:" + (var3 - var1) + "ms");