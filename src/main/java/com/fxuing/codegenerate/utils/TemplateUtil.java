package com.fxuing.codegenerate.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.24 16:53
 * @Description:
 */
public class TemplateUtil {
    private static TemplateEngine engine = getTemplateEngine();

    public void setEngine(TemplateEngine engine) {
        TemplateUtil.engine = engine;
    }

    /**
     * 生成文件
     *
     * @param freeTempName 模板名称
     * @param context      数据内容
     * @param outFilePath  输出路径
     * @return
     */
    public static boolean process(String freeTempName, Context context, String outFilePath) {
        try (FileWriter fileWriter = new FileWriter(outFilePath)) {
            engine.process(freeTempName, context, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void process(String freeTempName, Context context, Writer writer) {
        engine.process(freeTempName, context, writer);
    }

    public static TemplateEngine getTemplateEngine() {
        final TemplateEngine templateEngine = new SpringTemplateEngine();
        // 处理txt的
        templateEngine.addTemplateResolver(textTemplateResolver());
        return templateEngine;
    }
    private static ITemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setPrefix("/templates/");
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
