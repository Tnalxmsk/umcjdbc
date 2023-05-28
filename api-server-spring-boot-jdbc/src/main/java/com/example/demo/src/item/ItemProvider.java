package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.item.model.GetItemRes;
import com.example.demo.src.post.model.GetPostRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ItemProvider {
    private final ItemDao itemDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ItemProvider(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<GetItemRes> getItems() throws BaseException {
        try {
            List<GetItemRes> getItemRes = itemDao.getItems();
            return getItemRes;
        }
        catch (Exception exception) {
            logger.error("Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetItemRes getItem(Long itemIdx) throws BaseException {
        try {
            GetItemRes getItemRes = itemDao.getItem(itemIdx);
            return getItemRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
