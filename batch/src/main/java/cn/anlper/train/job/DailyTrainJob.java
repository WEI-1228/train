package cn.anlper.train.job;

import cn.anlper.train.feign.BusinessFeign;
import cn.anlper.train.resp.CommonResp;
import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@DisallowConcurrentExecution
@Slf4j
public class DailyTrainJob implements Job {
    @Resource
    private BusinessFeign businessFeign;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("生成十五天后的车次数据开始");
        Date date = new Date();
        Date dateTime = DateUtil.offsetDay(date, 15).toJdkDate();
        CommonResp commonResp = businessFeign.genDaily(dateTime);
        log.info("生成每日车次数据结束，结果：{}", commonResp);
    }
}
