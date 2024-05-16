//package cn.anlper.train.job;
//
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@EnableScheduling
//@Component
//public class SpringBootTestJob {
//
//    @Scheduled(cron = "0/5 * * * * ?")
//    private void test() throws InterruptedException {
//        System.out.println("TestJob.execute 开始，停七秒");
//        TimeUnit.SECONDS.sleep(7);
//        System.out.println("结束");
//    }
//}
