package com.caiji.caijifilm.service;
import java.util.List;
import com.caiji.caijifilm.pojo.Movie;
import com.caiji.caijifilm.pojo.MovieComing;
import com.caiji.caijifilm.pojo.MovieHot;

public interface MovieService {
    /**
     * 查询电影信息
     * @param search
     * @return
     */
    List<Movie>SelectMovieInfo (String search);

    /**查询将要上映的电影的所有信息**/
     List<MovieComing> SelectMovieComing();

    /**查询正在热映的电影的所有信息**/
     List<Movie> SelectMovieHot();

    /**查询将要上映电影的前6位**/
     List<MovieComing> SelectMovieComing6();

    /**查询正在热映电影的前6位**/
    List<Movie> SelectMovieHot6();
}
