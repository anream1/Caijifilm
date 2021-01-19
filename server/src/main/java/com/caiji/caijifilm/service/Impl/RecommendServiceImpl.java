package com.caiji.caijifilm.service.Impl;

import com.caiji.caijifilm.dao.LabelDao;
import com.caiji.caijifilm.dao.MovieDao;
import com.caiji.caijifilm.pojo.Label;
import com.caiji.caijifilm.pojo.Movie;
import com.caiji.caijifilm.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private LabelDao labelDao;

    /**根据用户id返回其标签**/
    @Override
    public List<String> BackUserLabel(int uid){
        return labelDao.Backlabel(uid);
    }

    /**从movie表中提取出含有该标签的所有电影**/
    @Override
     public  List<Movie> SelectMovieByCategory(String category)
    {
        return movieDao.SelectMovieByCategory(category);
    }
}
