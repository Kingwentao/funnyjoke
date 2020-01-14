package com.example.libnavcompiler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.libnavnoattion.ActivityDestination;
import com.example.libnavnoattion.FragmentDestination;
import com.google.auto.service.AutoService;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;


/**
 * author: created by wentaoKing
 * date: created in 2020-01-12
 * description: 对自定义注解的相关操作：生成json文件
 */

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(
        {"com.example.libnavnoattion.FragmentDestination",
                "com.example.libnavnoattion.ActivityDestination"})
public class NavProcessor extends AbstractProcessor {

    private static final String OUTPUT_FILE_NAME = "destination.json";
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        System.out.println("init nav processor");
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        messager.printMessage(Diagnostic.Kind.NOTE,"init processor");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("nav processor process");
        Set<? extends Element> fragmentElements = roundEnvironment.getElementsAnnotatedWith(FragmentDestination.class);
        Set<? extends Element> activityElements = roundEnvironment.getElementsAnnotatedWith(ActivityDestination.class);

        if (!fragmentElements.isEmpty() || !activityElements.isEmpty()) {
            HashMap<String, JSONObject> destMap = new HashMap<>();
            handleDestination(fragmentElements, FragmentDestination.class, destMap);
            handleDestination(activityElements, ActivityDestination.class, destMap);

            //保存json文件到 src/main/assets目录下
            FileObject resource = null;
            try {
                resource = filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String resourcePath = resource.toUri().getPath();
            messager.printMessage(Diagnostic.Kind.NOTE,"resourcePath " + resourcePath);
            //获取app目录的路径
            String appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4);
            String assetPath = appPath + "src/main/assets/";

            File file = new File(assetPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            File outputFile = new File(file, OUTPUT_FILE_NAME);
            if (outputFile.exists()) {
                outputFile.delete();
            }

            FileOutputStream fos = null;
            OutputStreamWriter writer = null;
            try {
                outputFile.createNewFile();
                String content = JSON.toJSONString(destMap);

                fos = new FileOutputStream(outputFile);
                writer = new OutputStreamWriter(fos, "UTF-8");
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return true;
    }

    /**
     * 处理目的行为
     * @param elements
     * @param annotationClass 注解的类
     * @param destMap
     */
    private void handleDestination(Set<? extends Element> elements,
                                   Class<? extends Annotation> annotationClass, HashMap<String, JSONObject> destMap) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            String pageUrl = null;

            String className = typeElement.getQualifiedName().toString();
            boolean needLogin = false;
            boolean asStarter = false;
            //id
            int id = Math.abs(className.hashCode());
            boolean isFragment = false;

            Annotation annotation = typeElement.getAnnotation(annotationClass);


            if (annotation instanceof FragmentDestination) {

                isFragment = true;

                FragmentDestination dest = (FragmentDestination) annotation;
                pageUrl = dest.pageUrl();
                needLogin = dest.needLogin();
                asStarter = dest.asStarter();
            } else if (annotation instanceof ActivityDestination) {
                ActivityDestination dest = (ActivityDestination) annotation;
                pageUrl = dest.pageUrl();
                needLogin = dest.needLogin();
                asStarter = dest.asStarter();
            }

            //如果页面已经存在
            if (destMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.ERROR, "不同的页面不允许相同的url " + className);
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("needLogin", needLogin);
                jsonObject.put("pageUrl", pageUrl);
                jsonObject.put("asStarter", asStarter);
                jsonObject.put("className", className);
                destMap.put(pageUrl,jsonObject);
            }
        }
    }
}
