package com.demo.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.ivy.util.FileUtil


class ASMTransform extends Transform
{
    Project project

    ASMTransform(Project project)
    {
        this.project=project
    }

    //这个是task的名字
    @Override
    String getName() {
        return "ASMTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println("===== ASM Transform =====")
        println("${transformInvocation.inputs}")
        println("${transformInvocation.referencedInputs}")
        println("${transformInvocation.outputProvider}")
        println("${transformInvocation.incremental}")

        //这些代码必须写，要不然都无法运行
        def inputs=transformInvocation.inputs
        def outputProvider=transformInvocation.outputProvider

        inputs.each {
            it.jarInputs.each {
                File dest=outputProvider.getContentLocation(it.name,
                    it.contentTypes,it.scopes, Format.JAR)
                FileUtils.copyFile(it.file,dest)
            }

            it.directoryInputs.each {
                File dest=outputProvider.getContentLocation(it.name,
                    it.contentTypes,it.scopes,Format.DIRECTORY)
                FileUtils.copyDirectory(it.file,dest)
            }
        }


    }
}