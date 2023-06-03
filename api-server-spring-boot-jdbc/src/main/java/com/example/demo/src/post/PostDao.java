package com.example.demo.src.post;

import com.example.demo.src.post.model.*;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from postInfo", Integer.class);
    }
    public List<GetPostRes> getPostsPaging(int pageNo, int pageSize) {
        String getPostsQuery = "select * from PostInfo order by postIdx dec limit 0, 10";
        return this.jdbcTemplate.query(getPostsQuery,
                new BeanPropertyRowMapper<>(GetPostRes.class),
                pageSize,
                pageNo * pageSize
        );
    }

    public List<GetPostRes> getPosts() {
        String getPostsQuery = "select * from PostInfo";
        return this.jdbcTemplate.query(getPostsQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getLong("postIdx"),
                        rs.getLong("userIdx"),
                        rs.getString("title"),
                        rs.getString("postText"),
                        rs.getString("postImage"),
                        rs.getLong("postInquiry"))

        );
    }

    public GetPostRes getPost(Long postIdx) {
        String getPostQuery = "select * from PostInfo where postIdx = ?";
        Long getPostParams = postIdx;
        return this.jdbcTemplate.queryForObject(getPostQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getLong("postIdx"),
                        rs.getLong("userIdx"),
                        rs.getString("title"),
                        rs.getString("postText"),
                        rs.getString("postImage"),
                        rs.getLong("postInquiry")),
                getPostParams
                );
    }

    public Long createPost(PostPostReq postPostReq) {
        String createPostQuery = "insert int PostInfo (postIdx, userIdx, title, postText, postImage, PostInquiry)";
        Object[] createPostParams = new Object[] {
                postPostReq.getUserIdx(), postPostReq.getTitle(), postPostReq.getPostText(),
                postPostReq.getPostImage(), postPostReq.getPostInquiry()
        };

        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String lastInsertIdQuery = "select last_insert_id";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
    }

    public int modifyPost(PatchPostReq patchPostReq) {
        String modifyPostQuery = "update PostInfo set title = ? postText = ? postImage = ? where postIdx = ? userIdx = ?";
        Object[] modifyPostParams = new Object[] {
                patchPostReq.getTitle(), patchPostReq.getPostText(), patchPostReq.getPostImage(), patchPostReq.getPostIdx(), patchPostReq.getUserIdx()
        };

        return this.jdbcTemplate.update(modifyPostQuery, modifyPostParams);
    }

    public int deletePost(DeletePostReq deletePostReq) {
        String deletePostQuery = "delete postInfo where postIdx = ? userIdx ?";
        Object[] deletePostParams = new Object[] {
                deletePostReq.getPostIdx(), deletePostReq.getUserIdx()
        };

         return this.jdbcTemplate.update(deletePostQuery, deletePostParams);
    }
}
