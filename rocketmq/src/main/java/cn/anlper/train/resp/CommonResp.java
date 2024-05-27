package cn.anlper.train.resp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommonResp<T>{
    private boolean success = true;

    private String message;

    private T content;

    public static<T> CommonResp ok(T content) {
        CommonResp commonResp = new CommonResp();
        commonResp.setContent(content);
        return commonResp;
    }

    public static CommonResp fail(String msg) {
        CommonResp commonResp = new CommonResp();
        commonResp.setSuccess(false);
        commonResp.setMessage(msg);
        return commonResp;
    }

}
