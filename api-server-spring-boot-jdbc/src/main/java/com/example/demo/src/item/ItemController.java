package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.item.model.*;
import com.example.demo.src.post.model.*;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/carrot/item")
public class ItemController {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private final ItemProvider itemProvider;
    @Autowired
    private final ItemService itemService;

    public ItemController(ItemProvider itemProvider, ItemService itemService) {
        this.itemProvider = itemProvider;
        this.itemService = itemService;
    }

    /**
     * 모든 판매 상품 조회 API
     * [GET] /item
     * @return BaseResponse<List<GetItemRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetItemRes>> getItems() {
        try {
            List<GetItemRes> getItemRes = itemProvider.getItems();
            return new BaseResponse<>(getItemRes);
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시물 1개 조회 API
     * [GET] /post
     * @return BaseResponse<List<GetPostRes>>
     */
    @ResponseBody
    @GetMapping("{itemIdx}")
    public BaseResponse<GetItemRes> getItem(@PathVariable("itemIdx") Long itemIdx ) {
        try {
            GetItemRes getItemRes = itemProvider.getItem(itemIdx);
            return new BaseResponse<>(getItemRes);
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 판매 상품 생성 API
     * [POST] /item
     * @return BaseResponse<PostItemRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostItemRes> createItem(@RequestBody PostItemReq postItemReq) {
        // 판매 상품 게시글 제목 입력 안함
        if (postItemReq.getTitle() == null) {
            return new BaseResponse<>(POST_POST_EMPTY_TITLE);
        }
        // 판매 상품 게시글 내용 입력 안함
        if (postItemReq.getComment() == null) {
            return new BaseResponse<>(POST_POST_EMPTY_POSTTEXT);
        }
        try {
            PostItemRes postItemRes = itemService.createItem(postItemReq);
            return new BaseResponse<>(postItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 판매 상품 수정 API
     * [DELETE] /item
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{itemIdx}{userIdx}")
    public BaseResponse<String> modifyItem(@PathVariable("itemIdx") Long itemIdx, @PathVariable("userIdx") Long userIdx, @RequestBody Item item) {
        try {

            PatchItemReq patchItemReq = new PatchItemReq(itemIdx, userIdx, item.getTitle(), item.getComment(), item.getGodsImage(), item.getPrice());
            itemService.modifyItem(patchItemReq);

            String result = "";
            return new BaseResponse<>(result);
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 판매 상품 삭제 API
     * [DELETE] /item
     * @return BaseResponse<String>
     */
    @ResponseBody
    @DeleteMapping("/{itemIdx}{userIdx}")
    public BaseResponse<String> deleteItem(@PathVariable("itemIdx") Long postIdx, @PathVariable("userIdx") Long userIdx) {
        try {
            DeleteItemReq deleteItemReq = new DeleteItemReq(postIdx, userIdx);
            itemService.deleteItem(deleteItemReq);

            String result = "";
            return new  BaseResponse<>(result);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
