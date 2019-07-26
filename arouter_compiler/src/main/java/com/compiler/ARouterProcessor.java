package com.compiler;

import com.annotation.ARouter;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author roy.xing
 * @date 2019-07-17
 * 编码此类1句话：细心再细心，出了问题debug真的不好调试
 */
// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
//@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.annotation.ARouter"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions("content")
public class ARouterProcessor extends AbstractProcessor {

    //操作Element工具类
    private Elements elemenstUtils;

    //type(类信息)
    private Types typeUtils;

    //用来输出警告，错误日志
    private Messager messager;

    //文件生成器
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elemenstUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        Map<String, String> options = processingEnvironment.getOptions();
        messager.printMessage(Diagnostic.Kind.NOTE, options.get("content"));
    }

    /**
     * 相当于main函数，开始处理注解
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param set              使用了支持处理注解的节点集合（类 上面写了注解）
     * @param roundEnvironment 当前或是之前的运行环境,可以通过该对象查找找到的注解。
     * @return true 表示后续处理器不会再处理（已经处理完成）
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) {
            return false;
        }
        // 获取所有带ARouter注解的 类节点
        Set<? extends Element> annotated = roundEnvironment.getElementsAnnotatedWith(ARouter.class);
        // 遍历所有类节点
        for (Element element : annotated) {
            // 通过类节点获取包节点（全路径：com.netease.xxx）
            String packageName = elemenstUtils.getPackageOf(element).getQualifiedName().toString();
            //获取简单类名
            String className = element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "被注解的类有：" + className);
            String finalClassName = className + "$$ARouter";

            try {
//                commonProcessor(element, packageName, className, finalClassName);
                javaPoetProcessor(element, packageName, finalClassName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 使用Javapoet生成
     *
     * @param element
     * @param packageName
     * @param className
     * @throws IOException
     */
    private void javaPoetProcessor(Element element, String packageName, String className) throws IOException {
        ARouter annotation = element.getAnnotation(ARouter.class);
        MethodSpec methodSpec = MethodSpec.methodBuilder("findTargetClass")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(Class.class)
                .addParameter(String.class, "path")
                .addStatement("return path.equals($S) ?$T.class : null",
                        annotation.path(),
                        ClassName.get((TypeElement) element))
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(methodSpec)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();

        javaFile.writeTo(filer);
    }

    /**
     * 传统生成方法
     *
     * @param element
     * @param packageName
     * @param className
     * @param finalClassName
     * @throws IOException
     */
    private void commonProcessor(Element element, String packageName, String className, String finalClassName) throws IOException {
        // 创建一个新的源文件（Class），并返回一个对象以允许写入它
        JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + finalClassName);
        // 定义Writer对象，开启写入
        Writer writer = sourceFile.openWriter();
        // 设置包名
        writer.write("package " + packageName + ";\n");

        writer.write("public class " + finalClassName + " {\n");

        writer.write("public static Class<?> findTargetClass(String path) {\n");

        // 获取类之上@ARouter注解的path值
        ARouter aRouter = element.getAnnotation(ARouter.class);
        messager.printMessage(Diagnostic.Kind.NOTE, "@ARouter注解的path值：" + aRouter.path());
        writer.write("if (path.equals(\"" + aRouter.path() + "\")) {\n");

        writer.write("return " + className + ".class;\n}\n");

        writer.write("return null;\n");

        writer.write("}\n}");

        writer.close();
    }
}
