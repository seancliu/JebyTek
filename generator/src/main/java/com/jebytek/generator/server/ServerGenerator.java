package com.jebytek.generator.server;

import com.jebytek.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;

public class ServerGenerator {
    static String toPath = "generator\\src\\main\\java\\com\\jebytek\\generator\\test\\";
    public static void main(String[] args) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("test.ftl");
        FreemarkerUtil.generator(toPath + "Test.java");
    }
}
