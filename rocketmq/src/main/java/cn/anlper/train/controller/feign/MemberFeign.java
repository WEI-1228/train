package cn.anlper.train.controller.feign;

import cn.anlper.train.req.MemberTicketSaveReq;
import cn.anlper.train.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("train-member-service")
public interface MemberFeign {
    @PostMapping("/member/feign/ticket/save")
    CommonResp save(@RequestBody @Validated MemberTicketSaveReq req);

    @GetMapping("/member/feign/ticket/query-list")
    CommonResp queryList(@Validated MemberTicketSaveReq req);
}
