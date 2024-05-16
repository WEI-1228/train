package cn.anlper.train.req;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CronJobReq {
    private String group;

    private String name;

    private String description;

    private String cronExpression;
}
