package com.example.demo.src.item;

import com.example.demo.src.item.model.*;
import com.example.demo.src.post.model.GetPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ItemDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetItemRes> getItems() {
        String getItemsQuery = "select * from ItemInfo";
        return this.jdbcTemplate.query(getItemsQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getLong("itemIdx"),
                        rs.getLong("godsIdx"),
                        rs.getLong("userIdx"),
                        rs.getString("title"),
                        rs.getString("comment"),
                        rs.getString("godsImage"),
                        rs.getLong("chattingCnt"),
                        rs.getLong("heartCnt"),
                        rs.getLong("price")
                ));
    }

    public GetItemRes getItem(Long itemIdx) {
        String getItemQuery = "select * from ItemInfo where itemIdx = ?";
        Long getItemParams = itemIdx;
        return this.jdbcTemplate.queryForObject(getItemQuery,
                (rs, rowNum) -> new GetItemRes(
                        rs.getLong("itemIdx"),
                        rs.getLong("godsIdx"),
                        rs.getLong("userIdx"),
                        rs.getString("title"),
                        rs.getString("comment"),
                        rs.getString("godsImage"),
                        rs.getLong("chattingCnt"),
                        rs.getLong("heartCnt"),
                        rs.getLong("price")),
                getItemParams);
    }

    public Long createItem(PostItemReq postItemReq) {
        String createQuery = "insert into ItemInfo (title, comment, godsImage, price)";
        Object[] createItemParams = new Object[] { postItemReq.getTitle(), postItemReq.getComment(), postItemReq.getGodsImage(), postItemReq.getPrice() };
        this.jdbcTemplate.update(createQuery, createItemParams);

        String lastInsertIdQuery = "select last_insert_id";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
    }


    public int modifyItem(PatchItemReq patchItemReq) {
        String modifyItemQuery = "update ItemInfo set title = ? comment = ? godsImage = ? where postIdx = ? userIdx = ?";
        Object[] modifyItemParams = new Object[] {
                patchItemReq.getTitle(), patchItemReq.getComment(), patchItemReq.getGodsImage(), patchItemReq.getItemIdx(), patchItemReq.getUserIdx()
        };

        return this.jdbcTemplate.update(modifyItemQuery, modifyItemParams);
    }

    public int deleteItem(DeleteItemReq deleteItemReq) {
        String deleteItemQuery = "delete ItemInfo where itemIdx = ? userIdx = ?";
        Object[] deleteParams = new Object[] {
                deleteItemReq.getItemIdx(), deleteItemReq.getUserIdx()
        };

        return this.jdbcTemplate.update(deleteItemQuery, deleteParams);
    }
}
