package com.jebytek.generator.server;

import com.jebytek.generator.util.DbUtil;
import com.jebytek.generator.util.Field;
import com.jebytek.generator.util.FreemarkerUtil;

import java.util.*;

public class ServerGenerator {
    static String MODULE = "business";
    static String toDtoPath = "server\\src\\main\\java\\com\\jebytek\\server\\dto\\";
    static String toServicePath = "server\\src\\main\\java\\com\\jebytek\\server\\service\\";
    static String toControllerPath = MODULE + "\\src\\main\\java\\com\\jebytek\\" + MODULE + "\\controller\\admin\\";
    public static void main(String[] args) throws Exception {
        String Domain = "Section";
        String domain = "section";
        String module = MODULE;
        List<Field> fieldList = DbUtil.getColumnByTableName(domain);
        Set<String> typeSet = getJavaTypes(fieldList);
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("module", module);
        map.put("fieldList", fieldList);
        map.put("typeSet", typeSet);
        // GENERATE SERVICE
        FreemarkerUtil.initConfig("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);

        // GENERATE SERVICE
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);

        // GENERATE CONTROLLER
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);
    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
