package com.fxuing.codegenerate.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @Author: Hou_fx
 * @Date: 2020.7.24 16:53
 * @Description:
 */
@Component
public class TemplateUtil {
    private static TemplateEngine engine;

    @Autowired
    public void setEngine(TemplateEngine engine) {
        TemplateUtil.engine = engine;
    }
    //public static TemplateEngine engine = ApplicationContextHolder.getBean(TemplateEngine.class);

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
}
