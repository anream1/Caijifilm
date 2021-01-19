package com.caiji.caijifilm.dao;

import com.caiji.caijifilm.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface CommentDao {

    /**根据电影id获取其评论表中的所有信息**/
    @Select("select * from comment where mid=#{mid}")
    List<Comment> SelectInfoByMid(int mid);

    /**根据用户id查询其评论过的所有电影的评论信息**/
    @Select("select * from comment where uid=#{uid}")
    List<Comment> SelectInfoByUid(int uid);

    /**插入用户评论**/
    @Insert("insert into comment (content,uid,mid,rate) values (#{content},#{uid},#{mid},#{rate})")
    void InsertCommentInfo(String content, int uid, int mid,int rate);

    /**增加点赞数**/
    @Update("update comment set lnum = lnum + 1 where mid=#{mid} and uid=#{uid}")
    void IncreaseLNum(int uid,int mid);

    /**用户是否点赞**/
    @Insert("insert into likemovie (cid,uid) values (#{cid},#{uid})")
    void InserLike(int cid,int uid);

    /**增加点踩数**/
    @Update("update comment set dlnum = dlnum + 1 where mid=#{mid} and uid=#{uid}")
    void IncreaseDLNum(int uid,int mid);

    /**根据评论者id和电影id返回评论id**/
    @Select("select id from comment where mid=#{mid} and uid=#{uid}")
    int SelectCid(int mid,int uid);

    /**根据查看评论者id和评论id判断是否点赞**/
    @Select("SELECT EXISTS(SELECT * FROM likemovie WHERE cid =#{cid} and uid=#{uid})")
    boolean CheckLike(int cid ,int uid);

    /**根据用户id找出其看过的所有电影的id**/
    @Select("select mid from comment where uid=#{uid}")
    List<Integer> SelectMIDByUID(int uid);

    /**根据电影id查询所有看过该电影的用户id**/
    @Select("select uid from comment where mid=#{mid}")
    List<Integer> SelectUIDByMID(int mid);

    /**根据评论表id为某条评论点赞**/
    @Update("update comment set lnum = lnum + 1 where id=#{id}")
    void Updatelnum(int id);
}
