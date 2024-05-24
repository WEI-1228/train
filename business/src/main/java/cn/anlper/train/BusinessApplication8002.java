package cn.anlper.train;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("cn.anlper.train.mapper")
@Slf4j
@EnableFeignClients
@EnableCaching
public class BusinessApplication8002 {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BusinessApplication8002.class);
        ConfigurableEnvironment environment = context.getEnvironment();
        log.info("启动成功，访问地址：\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
        // 限流规则
//        initFlowRules();
//        log.info("已定义限流规则");
    }

    private static void initFlowRules() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("doConfirm");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
