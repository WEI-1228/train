package cn.anlper.train.req;

import lombok.Data;

@Data
public class StationSaveReq {
    private Long id;
    private String name;
    private String namePinyin;
    private String namePy;
}
