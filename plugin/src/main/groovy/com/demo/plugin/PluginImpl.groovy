package com.demo.plugin


import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginImpl  implements Plugin<Project> {
    void apply(Project project) {
//        project.task('testPlugin') {
//            doLast {
//                println "自定义插件ccc"
//            }
//        }

//        project.extensions.getByType(AppExtension).registerTransform(new ASMTransform(project))

        def android = project.extensions.getByType(AppExtension)
        if(android==null)
        {
            println "android 为null"
        }
        else
        {
            println "android 不为null"
        }
//        android.registerTransform(new ASMTransform(project))
        android.registerTransform(new ASMTransform(project))
    }
}
