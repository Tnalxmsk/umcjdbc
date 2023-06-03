package com.example.demo.src.member;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.member.model.PatchMemberReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class MemberService {
    private final MemberDao memberDao;
    private final MemberService memberService;
    private final JwtService jwtService;

    @Autowired
    public MemberService(MemberDao memberDao, MemberService memberService, JwtService jwtService) {
        this.memberDao = memberDao;
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    public void modifyMember(PatchMemberReq patchMemberReq) throws BaseException {
        try {
            int result = memberDao.modifyMember(patchMemberReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_MEMBER);
            }
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
