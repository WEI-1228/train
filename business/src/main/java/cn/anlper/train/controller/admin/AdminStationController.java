package cn.anlper.train.controller.admin;

import cn.anlper.train.req.StationQueryReq;
import cn.anlper.train.req.StationSaveReq;
import cn.anlper.train.resp.CommonResp;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.StationQueryResp;
import cn.anlper.train.service.StationService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/station")
public class AdminStationController {

    @Resource
    private StationService stationService;

    @PostMapping("/save")
    public CommonResp save(@RequestBody @Validated StationSaveReq req) {
        stationService.save(req);
        return CommonResp.ok(null);
    }

    @GetMapping("/query-list")
    public CommonResp queryList(@Validated StationQueryReq req) {
        PageResp pageResp = stationService.queryList(req);
        return CommonResp.ok(pageResp);
    }

    @GetMapping("/query-all")
    public CommonResp queryAll() {
        List<StationQueryResp> stationQueryResps = stationService.queryAll();
        return CommonResp.ok(stationQueryResps);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable("id") Long id) {
        stationService.delete(id);
        return CommonResp.ok(null);
    }
}
