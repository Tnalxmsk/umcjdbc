package com.example.demo.src.member;

import com.example.demo.config.BaseException;
import com.example.demo.src.member.model.GetMemberRes;
import com.example.demo.src.member.model.Member;
import com.example.demo.src.member.model.PatchMemberReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MemberDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private Long userIdx;
    private String name;
    private String email;
    private String nickName;
    private String profileImage;
    private String birth;
    private String location;
    private Float mannerTemper;
    private Integer rateReTrading;
    private Integer rateResponse;
    public List<GetMemberRes> getMembers() {
        String getMembersQuery = "select * form UserInfo";
        return this.jdbcTemplate.query(getMembersQuery,
                (rs, rowNum) -> new GetMemberRes(
                        rs.getLong("userIdx"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("profileImage"),
                        rs.getString("birth"),
                        rs.getString("location"),
                        rs.getFloat("mannerTemper"),
                        rs.getInt("rateReTrading"),
                        rs.getInt("rateResponse")
                )
        );
    }

    public List<GetMemberRes> getMembersByEmail(String email) {
        String getMembersByEmailQuery = "select * form UserInfo where email = ?";
        return this.jdbcTemplate.query(getMembersByEmailQuery,
                (rs, rowNum) -> new GetMemberRes(
                        rs.getLong("userIdx"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("profileImage"),
                        rs.getString("birth"),
                        rs.getString("location"),
                        rs.getFloat("mannerTemper"),
                        rs.getInt("rateReTrading"),
                        rs.getInt("rateResponse")
                )
        );
    }

    public GetMemberRes getMember(Long userIdx) {
        String getMemberQuery = "select * from UserInfo where userIdx = ?";
        Long getMemberParams = userIdx;
        return this.jdbcTemplate.queryForObject(getMemberQuery,
                (rs, rowNum) -> new GetMemberRes(
                        rs.getLong("userIdx"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("profileImage"),
                        rs.getString("birth"),
                        rs.getString("location"),
                        rs.getFloat("mannerTemper"),
                        rs.getInt("rateReTrading"),
                        rs.getInt("rateResponse")),
                getMemberParams);
    }

    public int modifyMember(PatchMemberReq patchMemberReq) {
        String modifyMemberNameQuery = "update UserInfo set name = ? nickname = ? profileImage =? where userIdx ";
        Object[] modifyMemberParams = new Object[]{
                patchMemberReq.getName(), patchMemberReq.getNickname(), patchMemberReq.getProfileImage(), patchMemberReq.getUserIdx()
        };
        return this.jdbcTemplate.update(modifyMemberNameQuery, modifyMemberParams);
    }
}
