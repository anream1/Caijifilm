package com.caiji.caijifilm.web;
import com.caiji.caijifilm.dao.MovieDao;
import com.caiji.caijifilm.pojo.MovieComing;
import com.caiji.caijifilm.pojo.MovieHot;
import com.caiji.caijifilm.service.MovieService;
import  com.caiji.caijifilm.pojo.Movie;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MovieRestController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieDao movieDao;

    /**
     * 根据搜索内容返回电影信息
     * @param search
     * @return
     */
    @RequestMapping(value = "/movie/movieinfo", method = RequestMethod.GET)
    public List<Movie> SelectMovieInfo(@RequestParam(value = "search", required = true) String search){
       System.out.println("开始查询");
       return movieService.SelectMovieInfo("%"+search+"%");
    }

    /**根据标签返回10条电影信息**/
    @RequestMapping(value = "/movie/movieinfobylabel",method = RequestMethod.GET)
    public List<Movie> SelectMovieInfoByLabel(@RequestParam(value = "label",required = true)String label,
                                              @RequestParam(value = "begin",required = true)int begin)
    {
        List<Movie> movie2=new ArrayList<Movie>();
        List<Movie> movie1=movieDao.SelectMovieByCategory(label);
        for(int i=0;i<10;i++)
        {
            Movie movie=movie1.get(begin-1);
            movie2.add(movie);
            begin=begin+1;
        }
        return movie2;
    }

    /**查询将要上映的电影的所有信息**/
    @RequestMapping(value = "/movie/moviecoming",method = RequestMethod.GET)
    public List<MovieComing> SelectMovieComing(){
        return movieService.SelectMovieComing();
    }

    /**查询将要上映地电影的前6位**/
     @RequestMapping(value = "/movie/moviecoming6",method = RequestMethod.GET)
      public List<MovieComing> SelectMovieComing6()
      {return  movieService.SelectMovieComing6();}

    /**查询正在热映的电影的所有信息**/
    @RequestMapping(value = "/movie/moviehot",method = RequestMethod.GET)
    public List<Movie> SelectMovieHot(){
        return movieService.SelectMovieHot();
    }

    /**查询正在热映电影的前6位**/
    @RequestMapping(value = "/movie/moviehot6",method = RequestMethod.GET)
    public List<Movie> SelectMovieHot6()
    {return  movieService.SelectMovieHot6();}
}
