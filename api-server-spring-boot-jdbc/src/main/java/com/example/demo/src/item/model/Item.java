package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {
    private Long itemIdx;
    private Long godsIndex;
    private Long userIdx;
    private String title;
    private String comment;
    private String godsImage;
    private Long chattingCnt;
    private Long heartCnt;
    private Long price;
    private Long inquiry;
}
