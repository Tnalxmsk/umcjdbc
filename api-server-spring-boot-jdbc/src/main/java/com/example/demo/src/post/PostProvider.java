package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.GetPostRes;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class PostProvider {

    private final PostDao postDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostProvider(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<GetPostRes> getPostsPaging() throws BaseException {
        try {
            List<GetPostRes> getPostRes = postDao.getPostsPaging(1, 10);
            return getPostRes;
        }
        catch (Exception exception) {
            logger.error("Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPostRes> getPosts() throws BaseException {
        try {
            List<GetPostRes> getPostRes = postDao.getPosts();
            return getPostRes;
        }
        catch (Exception exception) {
            logger.error("Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPostRes getPost(Long postIdx) throws BaseException {
        try {
            GetPostRes getPostRes = postDao.getPost(postIdx);
            return getPostRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
