package cn.anlper.train.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StationQueryReq extends PageReq {
    private Long id;
    private String name;
    private String namePinyin;
    private String namePy;
}
