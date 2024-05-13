package cn.anlper.train.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {
    private static final String key = "258079";
    public static String createToken(Long id, String mobile) {
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR, 24);
        Map<String, Object> payload = new HashMap<>();
        // 工具包能自动帮助我们判断该token是否过期，只需要设置好这些内部时间，就能调用工具包的方法来进行判断
        payload.put(JWTPayload.ISSUED_AT, now);
        payload.put(JWTPayload.EXPIRES_AT, expTime);
        payload.put(JWTPayload.NOT_BEFORE, now);

        payload.put("id", id);
        payload.put("mobile", mobile);
        String token = JWTUtil.createToken(payload, key.getBytes());
        log.info("生成JWT token：{}", token);
        return token;
    }

    public static boolean validate(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        boolean validate = jwt.validate(0);
        log.info("JWT token校验结果：{}", validate);
        return validate;
    }

    public static JSONObject getJSONObject(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        log.info("根据JWT token获取的原始内容：{}", payloads);
        return payloads;
    }

    public static void main(String[] args) {
        JwtUtil.createToken(1L, "1234");
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYmYiOjE3MTU2MDc1MzIsIm1vYmlsZSI6IjEyMzQiLCJpZCI6MSwiZXhwIjoxNzE1NjkzOTMyLCJpYXQiOjE3MTU2MDc1MzJ9.XbtXuu1dwpW8SY9e5OOaF5bBUEvqONWUuLuPdLUzj70";
        validate(token);
        getJSONObject(token);
    }
}
