package com.caiji.caijifilm.web;

import com.caiji.caijifilm.dao.CommentDao;
import com.caiji.caijifilm.dao.MovieDao;
import com.caiji.caijifilm.pojo.Movie;
import com.caiji.caijifilm.pojo.MovieRecommend;
import com.caiji.caijifilm.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/api")
public class RecommendRestController {
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private MovieDao movieDao;

//    /**
//     * 根据电影标签返回含有该标签的所有电影
//     **/
//    @RequestMapping(value = "/recommend/label", method = RequestMethod.GET)
//    public List<Movie> BackMovieByMovieLabel(@RequestParam(value = "category", required = true) String category) {
//        return recommendService.SelectMovieByCategory(category);
//    }

    /**
     * 根据用户标签返回含有用户喜欢标签的所有电影
     **/
    @RequestMapping(value = "/recommend/user", method = RequestMethod.GET)
    public List<MovieRecommend> BackMovieByUserLabel(@RequestParam(value = "uid", required = true) int uid) {
        System.out.println("开始推荐");
        List<Movie> movie3 = new ArrayList<Movie>();
        List<Movie> movie4 = new ArrayList<Movie>();
        List<MovieRecommend> movieRecommends = new ArrayList<MovieRecommend>();
        List<MovieRecommend> movieRecommends1=new ArrayList<>();
        //下面的for循环将用户所有标签的部高分电影存进了movie3中
        for (int j = 0; j < recommendService.BackUserLabel(uid).size(); j++) {
            String a = recommendService.BackUserLabel(uid).get(j);//a是分割出来的特定的标签
            System.out.println(a);
            List<Movie> movie1 = recommendService.SelectMovieByCategory(a);//movie1是根据某个标签找出来的所有电影
            for (int m = 0; m < 50; m++)//将评分最高的100部电影存到movie3中
            {
                movie3.add(movie1.get(m));//将第m个电影给movie3
            }
        }
            //将movie3中电影存入movieRecommends里面


        for (Movie movie:movie3)
        {
            MovieRecommend movieRecommend =new MovieRecommend();
            movieRecommend.setUrl(movie.getUrl());
            movieRecommend.setTitle(movie.getTitle());
            movieRecommend.setShowtime(movie.getShowtime());
            movieRecommend.setRate(movie.getRate());
            movieRecommend.setOthername(movie.getOthername());
            movieRecommend.setMovieId(movie.getMovieId());
            movieRecommend.setLength(movie.getLength());
            movieRecommend.setLanguage(movie.getLanguage());
            movieRecommend.setFollownum(movie.getFollownum());
            movieRecommend.setDistrict(movie.getDistrict());
            movieRecommend.setDirector(movie.getDirector());
            movieRecommend.setDescription(movie.getDescription());
            movieRecommend.setCover(movie.getCover());
            movieRecommend.setComposer(movie.getComposer());
            movieRecommend.setCategory(movie.getCategory());
            movieRecommend.setActor(movie.getActor());
            movieRecommend.setReason("根据您的喜好为您推荐");
            movieRecommends.add(movieRecommend);
//            movieRecommends.add((MovieRecommend)movie);

        }
//        for(MovieRecommend movieRecommend:movieRecommends)
//                  {
//                      movieRecommend.setReason("根据您的喜好为您推荐");
//                  }
//                  Object[] object =movie3.toArray();
//                  List<Object> objects = Arrays.asList(object);
//                  movieRecommends = (List)objects;
//                  for(MovieRecommend movieRecommend:movieRecommends)
//                  {
//                      movieRecommend.setReason("根据您的喜好为您推荐");
//                  }
//                MovieRecommend movieRec = (MovieRecommend) movie;
//                movieRec.setReason("根据您的喜好推荐：");
//                movieRecommends.add(movieRec);
        System.out.println(uid);
            List<Integer> listusermovie=commentDao.SelectMIDByUID(uid);//找出使用用户看过的所有电影id
            for(int nmsl=0;nmsl<listusermovie.size();nmsl++)
            {
                List<Integer> listmovieuser=commentDao.SelectUIDByMID(listusermovie.get(nmsl));//找出看过该电影的所有用户
                for(int wsnd=0;wsnd<listmovieuser.size();wsnd++)
                {
                    List<Integer> listotherusermovie=commentDao.SelectMIDByUID(listmovieuser.get(wsnd));//找出其他用户看过的所有电影的id
                    for(int cnm=0;cnm<listotherusermovie.size();cnm++)
                        movie4.add(movieDao.SelectMovieInfoById(listotherusermovie.get(cnm)));
                }
            }
            for(int i=0;i<listusermovie.size();i++)
            {
               movie4.remove(movieDao.SelectMovieInfoById(listusermovie.get(i)));
            }
                for (Movie movie1:movie4)
                {
                    MovieRecommend movieRecommend =new MovieRecommend();
                    movieRecommend.setReason("其他用户还看了");
                    movieRecommend.setUrl(movie1.getUrl());
                    movieRecommend.setTitle(movie1.getTitle());
                    movieRecommend.setShowtime(movie1.getShowtime());
                    movieRecommend.setRate(movie1.getRate());
                    movieRecommend.setOthername(movie1.getOthername());
                    movieRecommend.setMovieId(movie1.getMovieId());
                    movieRecommend.setLength(movie1.getLength());
                    movieRecommend.setLanguage(movie1.getLanguage());
                    movieRecommend.setFollownum(movie1.getFollownum());
                    movieRecommend.setDistrict(movie1.getDistrict());
                    movieRecommend.setDirector(movie1.getDirector());
                    movieRecommend.setDescription(movie1.getDescription());
                    movieRecommend.setCover(movie1.getCover());
                    movieRecommend.setComposer(movie1.getComposer());
                    movieRecommend.setCategory(movie1.getCategory());
                    movieRecommend.setActor(movie1.getActor());
                    movieRecommends.add(movieRecommend);
//            movieRecommends.add((MovieRecommend)movie);

                }


              for(int i =0;i<movieRecommends.size()-1;i++){
                       for(int j=movieRecommends.size()-1;j>i;j--){
                             if(movieRecommends.get(j).equals(movieRecommends.get(i))){
                                 movieRecommends.remove(j);
                             }
                       }
              }
        int wdnmd=0;
            do {
                Random random=new Random();
                System.out.println(movieRecommends.size());
                int cqdx=random.nextInt(movieRecommends.size());
                System.out.println(cqdx+"好耶");
                int rz=0;
                for(int i=0;i<movieRecommends1.size();i++)
                {
                    if(movieRecommends.get(cqdx).getMovieId()==movieRecommends1.get(i).getMovieId())
                        rz=1;//如果包含就返回1
                }
                if(rz==0)
                {
                    movieRecommends1.add(movieRecommends.get(cqdx));
                    wdnmd++;
                }
                else System.out.println("有错误");
            }
            while (wdnmd<10);
            return movieRecommends1;

        }
}
