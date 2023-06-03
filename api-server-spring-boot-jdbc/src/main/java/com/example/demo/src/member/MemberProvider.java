package com.example.demo.src.member;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.member.model.GetMemberRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true) //
public class MemberProvider {
    private final MemberDao memberDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MemberProvider(MemberDao memberDao, JwtService jwtService) {
        this.memberDao = memberDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public List<GetMemberRes> getMembers() {
        List<GetMemberRes> getMemberRes = memberDao.getMembers();
        return getMemberRes;
    }

    @Transactional
    public List<GetMemberRes> getMembersByEmail(String email) {
        List<GetMemberRes> getMembersRes = memberDao.getMembersByEmail(email);
        return getMembersRes;
    }

    @Transactional
    public GetMemberRes getMember(Long userIdx) {
        GetMemberRes getMemberRes = memberDao.getMember(userIdx);
        return getMemberRes;
    }
}
