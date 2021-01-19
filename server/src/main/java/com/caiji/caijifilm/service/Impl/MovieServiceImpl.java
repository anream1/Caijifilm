package com.caiji.caijifilm.service.Impl;
import com.caiji.caijifilm.dao.MovieDao;
import com.caiji.caijifilm.pojo.MovieComing;
import com.caiji.caijifilm.pojo.MovieHot;
import com.caiji.caijifilm.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.caiji.caijifilm.pojo.Movie;

import java.util.List;

    @Service
    public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

   /**查询电影信息**/
    @Override
    public List<Movie>SelectMovieInfo (String search){

        return movieDao.SelectMovieInfo(search);
    }

    /**查询将要上映的电影的所有信息**/
    @Override
        public List<MovieComing> SelectMovieComing(){
        return movieDao.SelectMovieComing();
    }

    /**查询正在热映的电影的所有信息**/
    @Override
        public List<Movie> SelectMovieHot(){
        return movieDao.SelectMovieHot();
    }

    /**查询将要上映电影的前6位**/
    @Override
        public List<MovieComing> SelectMovieComing6()
    {return movieDao.SelectMovieComing6();}

    /**查询正在热映电影的前6位**/
    @Override
        public List<Movie> SelectMovieHot6()
    {return movieDao.SelectMovieHot6();}

}
