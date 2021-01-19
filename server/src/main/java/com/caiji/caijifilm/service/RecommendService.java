package com.caiji.caijifilm.service;

import com.caiji.caijifilm.pojo.Movie;

import java.util.List;

public interface RecommendService {

    /**根据用户id返回其标签**/
     List<String> BackUserLabel(int uid);

    /**从movie表中提取出含有该标签的所有电影**/
      List<Movie> SelectMovieByCategory(String category);
}
