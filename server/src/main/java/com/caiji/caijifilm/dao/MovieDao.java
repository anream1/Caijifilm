package com.caiji.caijifilm.dao;
import com.caiji.caijifilm.pojo.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.caiji.caijifilm.pojo.MovieComing;
import com.caiji.caijifilm.pojo.MovieHot;

import java.util.List;

@Mapper
public interface MovieDao {

    /**根据电影 搜索 查询电影所有信息**/
@Select("select * from movie where title like #{search}")
   List<Movie> SelectMovieInfo(String search);


/**增加电影的关注人数**/
@Update("update movie set FollowNum = FollowNum + 1 where movieId=#{movieid}")
    void IncreaseFollowNum(int movieid);

/**减少电影的关注人数**/
@Update("update movie set FollowNum = FollowNum - 1 where movieId=#{movieid}")
    void ReduceFollowNum(int movieid);

/**根据电影id查询电影所有信息**/
@Select("select * from movie where movieId=#{mid}")
    Movie SelectMovieInfoById(int mid);

/**根据电影标签返回含有该标签的所有电影信息**/
@Select("select * from movie where instr(category,#{category}) or instr(district,#{category})order by rate desc")
    List<Movie> SelectMovieByCategory(String category);

/**从moviecoming表中取出所有记录**/
@Select("select * from moviecoming")
    List<MovieComing> SelectMovieComing();

/**返回全部热映电影**/
@Select("SELECT * FROM `movie` where district like'a\\_%'")
    List<Movie> SelectMovieHot();

/**查询将要上映电影的前6位**/
@Select("select * from moviecoming limit 6")
    List<MovieComing> SelectMovieComing6();

    /**查询正在热映电影的前6位**/
    @Select("SELECT * FROM `movie` where district like'a\\_%' limit 6")
    List<Movie> SelectMovieHot6();

    /**根据电影id返回其名字**/
    @Select("select title from movie where movieid=#{movieid}")
    String SelectTitleByMID(int movieid);
}


