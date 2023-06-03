package com.example.demo.src.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchMemberReq {
    private Long userIdx;
    private String name;
    private String nickname;
    private String profileImage;
}
