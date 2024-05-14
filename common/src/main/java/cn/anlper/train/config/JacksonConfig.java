//package cn.anlper.train.config;
//
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JacksonConfig {
//
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        return builder -> {
//            // Long 会自动转换成 String
//            builder.serializerByType(Long.class, ToStringSerializer.instance);
//        };
//    }
////    @Bean
////    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
////        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
////        SimpleModule simpleModule = new SimpleModule();
////        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
////        objectMapper.registerModule(simpleModule);
////        return objectMapper;
////    }
//}
