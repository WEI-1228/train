package cn.anlper.train.service;

import cn.anlper.train.entities.Member;
import cn.anlper.train.mapper.MemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Resource
    private MemberMapper memberMapper;

    public int count() {
        Member member = memberMapper.selectByPrimaryKey(1);
        int c = memberMapper.selectCount(null);
        return c;
    }
}
