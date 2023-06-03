package com.example.demo.src.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMemberRes {
    private Long userIdx;
    private String name;
    private String email;
    private String nickName;
    private String profileImage;
    private String birth;
    private String location;
    private Float mannerTemper;
    private Integer rateReTrading;
    private Integer rateResponse;
}
