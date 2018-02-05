package com.m2m.mapper;

import com.m2m.domain.UserInvitationHis;
import com.m2m.domain.UserInvitationHisExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserInvitationHisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int countByExample(UserInvitationHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int deleteByExample(UserInvitationHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Delete({
        "delete from user_invitation_his",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Insert({
        "insert into user_invitation_his (id, uid, from_uid, ",
        "from_cid, type, coins, ",
        "create_time, status, ",
        "receive_time)",
        "values (#{id,jdbcType=BIGINT}, #{uid,jdbcType=BIGINT}, #{fromUid,jdbcType=BIGINT}, ",
        "#{fromCid,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, #{coins,jdbcType=INTEGER}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{status,jdbcType=INTEGER}, ",
        "#{receiveTime,jdbcType=TIMESTAMP})"
    })
    int insert(UserInvitationHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int insertSelective(UserInvitationHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    List<UserInvitationHis> selectByExample(UserInvitationHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Select({
        "select",
        "id, uid, from_uid, from_cid, type, coins, create_time, status, receive_time",
        "from user_invitation_his",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ResultMap("BaseResultMap")
    UserInvitationHis selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExampleSelective(@Param("record") UserInvitationHis record, @Param("example") UserInvitationHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByExample(@Param("record") UserInvitationHis record, @Param("example") UserInvitationHisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    int updateByPrimaryKeySelective(UserInvitationHis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_invitation_his
     *
     * @mbggenerated Thu Jan 11 17:51:54 CST 2018
     */
    @Update({
        "update user_invitation_his",
        "set uid = #{uid,jdbcType=BIGINT},",
          "from_uid = #{fromUid,jdbcType=BIGINT},",
          "from_cid = #{fromCid,jdbcType=BIGINT},",
          "type = #{type,jdbcType=INTEGER},",
          "coins = #{coins,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "status = #{status,jdbcType=INTEGER},",
          "receive_time = #{receiveTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserInvitationHis record);
}