package cn.anlper.train.resp;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class PageResp<T> implements Serializable {
    private Long total;
    private List<T> list;

    public PageResp(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }
}
