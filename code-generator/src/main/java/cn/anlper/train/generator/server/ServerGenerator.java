package cn.anlper.train.generator.server;

import cn.anlper.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServerGenerator {
    static final String TYPE = "controller";

    static String servicePath = "[module]/src/main/java/cn/anlper/train/" + TYPE + "/";
    static String configPath = "/Users/liujiawei/IdeaProjects/project/train/mybatis-generator/src/main/resources/config.properties";
    static {
        new File(servicePath).mkdirs();
    }

    public static void main(String[] args) throws IOException, TemplateException {
        Properties properties = new Properties();

        FileInputStream fileInputStream = new FileInputStream(configPath);
        properties.load(fileInputStream);
        fileInputStream.close();

        // 读取属性值
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");
        String module = properties.getProperty("module");
        String tableName = properties.getProperty("table.name");
        String Domain = properties.getProperty("object.name");
        String generateKey = properties.getProperty("generateKey");

        servicePath = servicePath.replace("[module]", module);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main = tableName.replaceAll("_", "-");

        Map<String, Object> param = new HashMap<>();
        param.put("Domain", Domain);
        param.put("domain", domain);
        param.put("do_main", do_main);
        System.out.println("组装参数：" + param);

        generate(Domain, param);

    }

    private static void generate(String Domain, Map<String, Object> param) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(ServerGenerator.TYPE + ".ftl");
        String Target = ServerGenerator.TYPE.substring(0, 1).toUpperCase() + ServerGenerator.TYPE.substring(1);
        String fileName = servicePath + Domain + Target + ".java";
        System.out.println("开始生成：" + fileName);
        FreemarkerUtil.generator(fileName, param);
    }
}
