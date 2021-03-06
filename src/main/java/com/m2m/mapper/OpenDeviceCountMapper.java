package com.m2m.mapper;

import com.m2m.domain.OpenDeviceCount;
import com.m2m.domain.OpenDeviceCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OpenDeviceCountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    int countByExample(OpenDeviceCountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    int deleteByExample(OpenDeviceCountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    @Delete({
        "delete from open_device_count",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    @Insert({
        "insert into open_device_count (id, version, ",
        "ip_address, platform, ",
        "channel, device, ",
        "creat_time)",
        "values (#{id,jdbcType=INTEGER}, #{version,jdbcType=VARCHAR}, ",
        "#{ipAddress,jdbcType=VARCHAR}, #{platform,jdbcType=INTEGER}, ",
        "#{channel,jdbcType=VARCHAR}, #{device,jdbcType=VARCHAR}, ",
        "#{creatTime,jdbcType=TIMESTAMP})"
    })
    int insert(OpenDeviceCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    int insertSelective(OpenDeviceCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    List<OpenDeviceCount> selectByExample(OpenDeviceCountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    @Select({
        "select",
        "id, version, ip_address, platform, channel, device, creat_time",
        "from open_device_count",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    OpenDeviceCount selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    int updateByExampleSelective(@Param("record") OpenDeviceCount record, @Param("example") OpenDeviceCountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    int updateByExample(@Param("record") OpenDeviceCount record, @Param("example") OpenDeviceCountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    int updateByPrimaryKeySelective(OpenDeviceCount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table open_device_count
     *
     * @mbggenerated Sat Jan 20 09:51:23 CST 2018
     */
    @Update({
        "update open_device_count",
        "set version = #{version,jdbcType=VARCHAR},",
          "ip_address = #{ipAddress,jdbcType=VARCHAR},",
          "platform = #{platform,jdbcType=INTEGER},",
          "channel = #{channel,jdbcType=VARCHAR},",
          "device = #{device,jdbcType=VARCHAR},",
          "creat_time = #{creatTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(OpenDeviceCount record);
}