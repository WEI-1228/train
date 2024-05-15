package cn.anlper.train.service;

import cn.anlper.train.entities.${Domain};
import cn.anlper.train.mapper.${Domain}Mapper;
import cn.anlper.train.req.${Domain}QueryReq;
import cn.anlper.train.req.${Domain}SaveReq;
import cn.anlper.train.resp.PageResp;
import cn.anlper.train.resp.${Domain}QueryResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Slf4j
public class ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;

    @Resource
    private SnowFlake snowFlake;
    public void save(${Domain}SaveReq req) {
        DateTime now = DateTime.now();
        ${Domain} ${domain} = BeanUtil.copyProperties(req, ${Domain}.class);
        if (ObjUtil.isNull(${domain}.getId())) {
            ${domain}.setId(snowFlake.nextId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
        } else {
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.updateByPrimaryKey(${domain});
        }

    }

    public PageResp queryList(${Domain}QueryReq req) {
        Example example = new Example(${Domain}.class);
        PageHelper.startPage(req.getPage(), req.getSize());

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<${Domain}QueryResp> ${domain}QueryRespList = BeanUtil.copyToList(${domain}List, ${Domain}QueryResp.class);
        PageResp<${Domain}QueryResp> pageResp = new PageResp<>(${domain}QueryRespList, pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
