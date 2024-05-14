package cn.anlper.train.resp;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PageResp<T> {
    private Long count;
    private List<T> list;

    public PageResp(List<T> list, Long count) {
        this.list = list;
        this.count = count;
    }
}
