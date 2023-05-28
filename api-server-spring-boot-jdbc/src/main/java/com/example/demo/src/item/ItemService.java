package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.item.model.DeleteItemReq;
import com.example.demo.src.item.model.PatchItemReq;
import com.example.demo.src.item.model.PostItemReq;
import com.example.demo.src.item.model.PostItemRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ItemService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ItemDao itemDao;
    private final ItemProvider itemProvider;

    public ItemService(ItemDao itemDao, ItemProvider itemProvider) {
        this.itemDao = itemDao;
        this.itemProvider = itemProvider;
    }

    public PostItemRes createItem(PostItemReq postItemReq) throws BaseException {
        try {
            Long itemIdx = itemDao.createItem(postItemReq);
            return new PostItemRes(itemIdx);
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyItem(PatchItemReq patchItemReq) throws BaseException {
        try {
            int result = itemDao.modifyItem(patchItemReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_ITEM);
            }
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteItem(DeleteItemReq deleteItemReq) throws BaseException {
        try {
            int result = itemDao.deleteItem(deleteItemReq);
            if (result == 0) {
                throw new BaseException(DELETE_ITEM_ERROR);
            }
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
