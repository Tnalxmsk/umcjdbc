package com.example.demo.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchItemReq {
    private Long itemIdx;
    private Long userIdx;
    private String title;
    private String comment;
    private String godsImage;
    private Long price;

}
