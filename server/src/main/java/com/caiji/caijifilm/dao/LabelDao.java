package com.caiji.caijifilm.dao;
import com.caiji.caijifilm.pojo.Label;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LabelDao {
    /**
     * 获取一个用户的所有标签
     */
    @Select("select label from label where uid=#{uid}")
    List<String> Backlabel(Integer uid);

    /**
     * 获取一个用户的自选标签
     * @param uid
     * @return
     */
    @Select("select label from label where uid=#{uid} and checklabel =0")
    List<String>BackUserLabel(int uid);

    /**
     * 获取一个用户的系统标签
     * @param uid
     * @return
     */
    @Select("select label from label where uid=#{uid} and checklabel =1")
    List<String>BackSystemLabel(int uid);

    /**
     * 插入用户自选标签，此处的checklabel默认为0
     */
    @Insert("insert into label (uid,label) values (#{uid},#{label})")
    void InsertUserLabel(int uid,String label);

    /**
     * 查询标签是否存在，存在返回1，不存在返回0
     */
    @Select("SELECT EXISTS(SELECT * FROM label WHERE uid =#{uid} and label=#{label})")
    int CheckUserlabelexist(int uid,String label);

    /**
     * 删除用户自选标签
     */
    @Delete("delete from label where uid =#{uid} and checklabel=0")
    void DeleteUserLabel(int uid);

    /**
     * 插入系统标签
     * @param uid
     * @param label
     */
    @Insert("insert into label (uid,label,checklabel) values (#{uid},#{label},1)")
    void InsertSystemLabel(int uid,String label);
}
