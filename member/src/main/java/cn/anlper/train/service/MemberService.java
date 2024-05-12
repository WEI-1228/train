package cn.anlper.train.service;

import cn.anlper.train.entities.Member;
import cn.anlper.train.mapper.MemberMapper;
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

    public Long register(String mobile) {
        Member member = new Member();
        member.setMobile(mobile);
        Member one = memberMapper.selectOne(member);
        if (one != null) {
//            return -1L;
            throw new RuntimeException("用户手机号已注册");
        }

        member.setId(snowFlake.nextId());
        memberMapper.insert(member);
        return member.getId();
    }
}
