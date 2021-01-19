package com.caiji.caijifilm.web;

import com.caiji.caijifilm.dao.FollowDao;
import com.caiji.caijifilm.dao.MovieDao;
import com.caiji.caijifilm.pojo.Movie;
import com.caiji.caijifilm.pojo.Result;
import com.caiji.caijifilm.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class FollowRestController {
    @Autowired
    private FollowService followService;
    @Autowired
    private FollowDao followDao;
    @Autowired
    private MovieDao movieDao;

    /**根据服务器传回的操作类型对用户关注电影和电影关注数进行修改**/
    @RequestMapping(value = "/follow/updatenum",method = RequestMethod.POST)
    public Integer UpdateFollowInfo(@RequestParam(value = "uid", required = true)int uid,
                                    @RequestParam(value = "mid", required = true)int mid,
                                    @RequestParam(value = "operand", required = true)int operand) {
        followService.UpdateMovieFollowNum(mid ,operand);
    if(followService.IncreaseFollowMovie(uid,mid,operand)==true)
        {
            return 1;
        }
        else return 0;
    }

    /**根据用户id和电影id查询用户是否关注该电影已关注返回1未关注返回0**/
    @RequestMapping(value = "/follow/check",method = RequestMethod.GET)
    public int CheckFollow(@RequestParam(value = "uid",required = true)int uid,
                           @RequestParam(value = "mid",required = true)int mid)
    {
   if(followDao.CheckFollow(uid, mid)==true) return 1;
   else return 0;
    }

    /**根据用户id返回其关注的所有电影的信息**/
    @RequestMapping(value = "/follow/movieinfobyuid",method = RequestMethod.GET)
    public List<Movie> MovieInfoByUid(@RequestParam(value = "uid",required = true)int uid)
    {
        List<Integer> listmovie=followDao.SelectFollowMovie(uid);
        List<Movie>followmovie=new ArrayList<>();
        for(int i=0;i<listmovie.size();i++)
        {
            followmovie.add(movieDao.SelectMovieInfoById(listmovie.get(i)));
        }
        return  followmovie;
    }
}
