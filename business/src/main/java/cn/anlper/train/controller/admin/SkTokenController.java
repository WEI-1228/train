package cn.anlper.train.controller.admin;

import cn.anlper.train.req.SkTokenQueryReq;
import cn.anlper.train.req.SkTokenSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.service.SkTokenService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/sk-token")
public class SkTokenController {

    @Resource
    private SkTokenService skTokenService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated SkTokenSaveReq req) {
        skTokenService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated SkTokenQueryReq req) {
        PageResp pageResp = skTokenService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        skTokenService.delete(id);
        return CommonResp.ok(null);
    }
}
