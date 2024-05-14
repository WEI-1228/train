package cn.anlper.train.generator.server;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerGenerator {
    static String toPath = "code-generator/src/main/java/cn/anlper/train/generator/test/";
    static String configPath = "/Users/liujiawei/IdeaProjects/project/train/mybatis-generator/src/main/resources/config.properties";
    static {
        new File(toPath).mkdirs();
    }

    public static void main(String[] args) throws IOException, TemplateException {
        Properties properties = new Properties();

        FileInputStream fileInputStream = new FileInputStream(configPath);
        properties.load(fileInputStream);
        fileInputStream.close();

        // 读取属性值
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");

        // 打印属性值
        System.out.println("URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

//        FreemarkerUtil.initConfig("test.ftl");
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("domain", "Test");
//        FreemarkerUtil.generator(toPath + "Test.java", param);


    }
}
