package cn.anlper.train.context;

import cn.anlper.train.resp.MemberLoginResp;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginMemberContext {
    private static final ThreadLocal<MemberLoginResp> memberLoginRespThreadLocal = new ThreadLocal<>();

    public static MemberLoginResp getMember() {
        return memberLoginRespThreadLocal.get();
    }

    public static void setMember(MemberLoginResp member) {
        memberLoginRespThreadLocal.set(member);
    }

    public static Long getId() {
        try {
            return getMember().getId();
        } catch (Exception e) {
            log.error("获取登录会员信息异常：", e);
            throw e;
        }
    }
}
