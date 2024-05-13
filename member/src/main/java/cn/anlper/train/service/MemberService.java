package cn.anlper.train.service;

import cn.anlper.train.entities.Member;
import cn.anlper.train.exception.BusinessException;
import cn.anlper.train.exception.BusinessExceptionEnum;
import cn.anlper.train.mapper.MemberMapper;
import cn.anlper.train.req.MemberLoginReq;
import cn.anlper.train.req.MemberRegisterReq;
import cn.anlper.train.req.MemberSendCodeReq;
import cn.anlper.train.resp.MemberLoginResp;
import cn.anlper.train.utils.SnowFlake;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.jwt.JWTUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private SnowFlake snowFlake;

    public int count() {
        return memberMapper.selectCount(null);
    }

    public void sendCode(MemberSendCodeReq req) {
        Member member = new Member();
        member.setMobile(req.getMobile());
        Member memberDB = memberMapper.selectOne(member);

        // 手机号不存在，插入
        if (memberDB == null) {
            log.info("手机号不存在，插入一条记录");
            member.setId(snowFlake.nextId());
            memberMapper.insert(member);
        }

        // 生成验证码
        String code = RandomUtil.randomString(4);
        code = "8888";
        log.info("生成短信验证码：{}", code);
        // 这里应该保存短信验证码到数据库
        // 然后用户登录的时候，需要从数据库中去查询验证码是否有效（业务类型一致、在有效期内、未使用过），如果有效，就允许登录

        // 最后通过短信接口发送给用户
        log.info("保存短信记录表");
        log.info("调用短信接口发送给用户");
        // TODO 将验证码放到MySQL中，用于登录校验。不需要用Redis，因为不是高并发动作
    }

    public Long register(MemberRegisterReq req) {
        Member member = new Member();
        member.setMobile(req.getMobile());
        Member memberDB = memberMapper.selectOne(member);
        if (memberDB != null) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        member.setId(snowFlake.nextId());
        memberMapper.insert(member);
        return member.getId();
    }

    public MemberLoginResp login(MemberLoginReq req) {
        Member member = new Member();
        member.setMobile(req.getMobile());
        Member memberDB = memberMapper.selectOne(member);
        if (memberDB == null) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        if (!req.getCode().equals("8888")) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        Map<String, Object> payload = BeanUtil.beanToMap(memberLoginResp);
        String key = "258079";
        String token = JWTUtil.createToken(payload, key.getBytes());
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }
}
