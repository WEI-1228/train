package cn.anlper.train.service;

import cn.anlper.train.entities.Member;
import cn.anlper.train.exception.BusinessException;
import cn.anlper.train.exception.BusinessExceptionEnum;
import cn.anlper.train.mapper.MemberMapper;
import cn.anlper.train.req.MemberRegisterReq;
import cn.anlper.train.utils.SnowFlake;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private SnowFlake snowFlake;

    public int count() {
        return memberMapper.selectCount(null);
    }

    public Long register(MemberRegisterReq req) {
        Member member = new Member();
        member.setMobile(req.getMobile());
        Member one = memberMapper.selectOne(member);
        if (one != null) {
//            return -1L;
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        member.setId(snowFlake.nextId());
        memberMapper.insert(member);
        return member.getId();
    }
}
