package cn.anlper.train.controller${admin_package};

import cn.anlper.train.req.${Domain}QueryReq;
import cn.anlper.train.req.${Domain}SaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.${Domain}Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${admin_prefix}${do_main}")
public class ${Domain}Controller {

    @Resource
    private ${Domain}Service ${domain}Service;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated ${Domain}SaveReq req) {
        ${domain}Service.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated ${Domain}QueryReq req) {
        PageResp pageResp = ${domain}Service.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        ${domain}Service.delete(id);
        return CommonResp.ok(null);
    }
}
