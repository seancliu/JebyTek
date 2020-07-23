package com.jebytek.generator.server;

import com.jebytek.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
    static String toServicePath = "server\\src\\main\\java\\com\\jebytek\\server\\service\\";
    public static void main(String[] args) throws IOException, TemplateException {
        String Domain = "Section";
        String domain = "section";
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);
    }
}
