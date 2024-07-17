package com.huajia.compiler;

import com.google.auto.service.AutoService;
import com.huajia.annotation.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Description:
 * Author: HuaJ1a
 * Date: 2024/7/10
 */
@AutoService({Processor.class})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions("project")
public class RouteProcessor extends AbstractProcessor {

    private Elements elementsUtil;
    private Types typeUtil;
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementsUtil = processingEnv.getElementUtils();
        typeUtil = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        messager.printMessage(Diagnostic.Kind.NOTE, " --> Hello Route");
        String project = processingEnv.getOptions().get("project");
        messager.printMessage(Diagnostic.Kind.NOTE, " --> project： " + project);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> map = new LinkedHashSet<>();
        map.add(Route.class.getCanonicalName());
        return map;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set.isEmpty()) return false;
        Writer writer = null;
        try {
            // 创建文件
            JavaFileObject sourceFile = filer.createSourceFile("RouterMap");
            writer = sourceFile.openWriter();
            writer.write(String.format("package %s;\n", "com.huajia.mac"));
            writer.write(String.format("import java.util.Map;\n"));
            writer.write(String.format("import java.util.concurrent.ConcurrentHashMap;\n"));
            writer.write(String.format("import com.huajia.annotation.RouteMeta;\n"));
            writer.write(String.format("public class RouterMap {\n"));
            writer.write(String.format("    public static Map<String, RouteMeta> map = new ConcurrentHashMap<>(128);\n"));
            writer.write(String.format("    static {\n"));
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);
            for (Element element : elements) {
                String packageName = elementsUtil.getPackageOf(element).getQualifiedName().toString();
                String className = element.getSimpleName().toString();
                messager.printMessage(Diagnostic.Kind.NOTE, String.format(" --> 路由类：【%s】 , 包名：【%s】", className, packageName));

                Route annotation = element.getAnnotation(Route.class);
                String path = annotation.path();
                float heightPercent = annotation.heightPercent();
                float widthPercent = annotation.widthPercent();

                writer.write(String.format("        map.put(\"%s\", new RouteMeta(\"%s\", \"%s\", \"%s\", %sf, %sf));\n",
                        path, path, className, packageName, heightPercent, widthPercent));
            }
            writer.write(String.format("    }\n"));
            writer.write(String.format("}\n"));
        } catch (Exception e) {

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        return true;
    }
}
