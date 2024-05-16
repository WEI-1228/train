package cn.anlper.train.job;

import lombok.SneakyThrows;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.TimeUnit;

@DisallowConcurrentExecution
public class TestJob implements Job {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("TestJob.execute 开始，停七秒");
        TimeUnit.SECONDS.sleep(7);
        System.out.println("结束");
        System.out.println("TestJob.execute 结束");
    }
}
