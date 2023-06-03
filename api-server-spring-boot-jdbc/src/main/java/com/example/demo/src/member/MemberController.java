package com.example.demo.src.member;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.member.model.GetMemberRes;
import com.example.demo.src.member.model.Member;
import com.example.demo.src.member.model.PatchMemberReq;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transaction;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/carrot/members")
public class MemberController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MemberProvider memberProvider;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final JwtService jwtService;

    public MemberController(MemberProvider memberProvider, MemberService memberService, JwtService jwtService) {
        this.memberProvider = memberProvider;
        this.memberService = memberService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetMemberRes>>
     */
    @ResponseBody
    @GetMapping("")
    public List<GetMemberRes> getMembers(@RequestBody(required = false) String email) throws Exception {
        if (email == null) {
            List<GetMemberRes> getMemberRes = memberProvider.getMembers();
            return getMemberRes;
        }
        List<GetMemberRes> getMemberRes = memberProvider.getMembersByEmail(email);
        return getMemberRes;
    }

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetMemberRes>
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public GetMemberRes getMember(@PathVariable("userIdx") Long userIdx) {
        GetMemberRes getMemberRes = memberProvider.getMember(userIdx);
        return getMemberRes;
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public String modifyMember(@PathVariable("userIdx") Long userIdx, @RequestBody Member member) throws Exception {
        Long userIdxByJwt = jwtService.getUserIdx();
        if (!Objects.equals(userIdx, userIdxByJwt)) {
            // TODO: 2023-06-01 수정
        }
        PatchMemberReq patchMemberReq = new PatchMemberReq(userIdx, member.getName(), member.getNickName(), member.getProfileImage());
        memberService.modifyMember(patchMemberReq);
        String result = "";
        return result;
        }

}
