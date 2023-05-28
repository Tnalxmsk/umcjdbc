package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/carrot/post")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;

    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService) {
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    /**
     * 모든 게시물 조회 API
     * [GET] /post
     * @return BaseResponse<List<GetPostRes>>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPostRes>> getPosts() {
        try {
            List<GetPostRes> getPostRes = postProvider.getPosts();
            return new BaseResponse<>(getPostRes);
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시물 1개 조회 API
     * [GET] /post
     * @return BaseResponse<<GetPostRes>
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetPostRes> getPost(@RequestParam("postIdx") Long postIdx) {
        try {
            GetPostRes getPostRes = postProvider.getPost(postIdx);
            return new BaseResponse<>(getPostRes);
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 게시글 생성 API
     * [POST] /post
     * @return BaseResponse<PostPostRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostRes> createPost(@RequestBody PostPostReq postPostReq) {
        // 게시글 제목 입력 안함
        if (postPostReq.getTitle() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TITLE);
        }
        // 게시글 내용 입력 안함
        if (postPostReq.getPostText() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_POSTTEXT);
        }
        try {
            PostPostRes postPostRes = postService.createPost(postPostReq);
            return new BaseResponse<>(postPostRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 게시글 수정 API
     * [DELETE] /post
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{postIdx}{userIdx}/mod")
    public BaseResponse<String> modifyPost(@PathVariable("postIdx") Long postIdx, @PathVariable("userIdx") Long userIdx, @RequestBody Post post) {
        try {
            /*//jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
                }*/
            PatchPostReq patchPostReq = new PatchPostReq(postIdx, userIdx, post.getTitle(), post.getPostText(), post.getPostImage());
            postService.modifyPost(patchPostReq);

            String result = "";
            return new BaseResponse<>(result);
        }
        catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 게시글 삭제 API
     * [DELETE] /post
     * @return BaseResponse<String>
     */
    @ResponseBody
    @DeleteMapping("/{postIdx}{userIdx}/delete")
    public BaseResponse<String> deletePost(@PathVariable("postIdx") Long postIdx, @PathVariable("userIdx") Long userIdx) {
        try {
            DeletePostReq deletePostReq = new DeletePostReq(postIdx, userIdx);
            postService.deletePost(deletePostReq);

            String result = "";
            return new  BaseResponse<>(result);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
