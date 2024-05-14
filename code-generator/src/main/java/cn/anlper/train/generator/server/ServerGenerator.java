package cn.anlper.train.generator.server;

import cn.anlper.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerGenerator {
    static String toPath = "code-generator/src/main/java/cn/anlper/train/generator/test/";
    static String configPath = "/Users/liujiawei/IdeaProjects/project/train/mybatis-generator/src/main/resources/config.properties";
    static {
        new File(toPath).mkdirs();
    }

    public static void main(String[] args) throws IOException, TemplateException {


        FreemarkerUtil.initConfig("test.ftl");
        HashMap<String, Object> param = new HashMap<>();
        param.put("domain", "Test");
        FreemarkerUtil.generator(toPath + "Test.java", param);


    }
}
