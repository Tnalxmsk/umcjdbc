package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostPostReq {
    private Long userIdx;
    private String title;
    private String postText;
    private String postImage;
    private Long PostInquiry;
}
