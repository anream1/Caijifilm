package com.caiji.caijifilm.web;

import com.caiji.caijifilm.dao.CommentDao;
import com.caiji.caijifilm.dao.MovieDao;
import com.caiji.caijifilm.pojo.Comment;
import com.caiji.caijifilm.pojo.CommentLike;
import com.caiji.caijifilm.pojo.CommentMovie;
import com.caiji.caijifilm.pojo.Movie;
import com.caiji.caijifilm.service.CommentService;
import com.caiji.caijifilm.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/api")
public class CommentRestController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private LabelService labelService;
    @Autowired
     private CommentDao commentDao;

    /**根据电影id返回电影评论信息**/
    @RequestMapping(value = "/comment/mid",method = RequestMethod.GET)
    public List<CommentLike> SelectInfoByMid(@RequestParam(value = "mid",required = true)int mid,
    @RequestParam(value="luid",required = true) int luid)
    {
     List<Comment> comment=commentService.SelectInfoByMid(mid);
     List<CommentLike> commentLike=new ArrayList<CommentLike>();
     for(int i=0;i<comment.size();i++)
     {
        Comment comment1=new Comment();
        comment1=comment.get(i);
        CommentLike commentlike1=new CommentLike();
        commentlike1.setContent(comment1.getContent());
        commentlike1.setCid(comment1.getId());
        commentlike1.setCuid(comment1.getUid());
        commentlike1.setLnum(comment1.getLNum());
        commentlike1.setMid(comment1.getMid());
        commentlike1.setRate(comment1.getRate());
        commentlike1.setTime(comment1.getCreateTime());
         //设置日期格式化样
        if(commentDao.CheckLike(comment1.getId(),luid)==true)  commentlike1.setLike(true);
         else commentlike1.setLike(false);
         commentLike.add(commentlike1);
     }
     return commentLike;
    }

    /**根据用户id返回该用户评论过的所有电影的评论信息**/
    @RequestMapping(value = "/comment/uid",method = RequestMethod.GET)
    public List<CommentMovie> SelectInfoByUid(@RequestParam(value = "uid",required = true)int uid)
    {
        List<Comment> comments= commentService.SelectInfoByUid(uid);
        List<CommentMovie> commentMovies=new ArrayList<>();
        for(Comment comment:comments)
        {
            CommentMovie commentMovie=new CommentMovie();
            commentMovie.setMovietitle(movieDao.SelectTitleByMID(comment.getMid()));
            commentMovie.setContent(comment.getContent());
            commentMovie.setCreateTime(comment.getCreateTime());
            commentMovie.setDLNum(comment.getDLNum());
            commentMovie.setId(comment.getId());
            commentMovie.setLNum(comment.getLNum());
            commentMovie.setRate(comment.getRate());
            commentMovies.add(commentMovie);
        }
        return commentMovies;
    }

    /**根据用户id返回其看过的所有电影信息**/
    @RequestMapping(value = "/comment/movieinfobyid",method = RequestMethod.GET)
    public List<Movie> SelectMovieInfoByUid(@RequestParam(value = "uid",required = true)int uid)
    {
        List<Integer>movieid=commentDao.SelectMIDByUID(uid);
        Set<Integer>set=new LinkedHashSet<>();
        set.addAll(movieid);
        movieid.clear();
        movieid.addAll(set);
        List<Movie> movieinfo=new ArrayList<>();
        for(int i=0;i<movieid.size();i++)
        {
            movieinfo.add(movieDao.SelectMovieInfoById(movieid.get(i)));
        }
        return movieinfo;
    }

    /**插入用户评论信息**/
    @RequestMapping(value = "/comment/insert",method = RequestMethod.POST)
    public Integer InsertComment(@RequestParam(value = "content",required = true)String content,
                                @RequestParam(value = "uid",required = true)int uid,
                                 //@RequestParam(value = "time",required = true)Date time,
                                @RequestParam(value = "mid",required = true)int mid,
                                @RequestParam(value = "rate",required = true)int rate)
    {
        if(rate>=8) {
            String label= movieDao.SelectMovieInfoById(mid).getCategory();
            String [] label1=label.split("/");
            for(int i=0;i<label1.length;i++)
            {String a=label1[i];
            System.out.println(a);
            labelService.InsertSystemLabel(uid,a);}
//            labelService.InsertUserLabel(uid,label);
            if (commentService.InsertCommentInfo(content, uid, mid, rate) == true)
                return 1;
            else return 0;
        }
        else {if (commentService.InsertCommentInfo(content,uid,mid,rate)==true)
        return 1;
        else return 0;}
    }

    /**修改用户评论的点赞数或点踩数**/
    @RequestMapping(value = "/comment/updatenum",method = RequestMethod.GET)
    public Integer UpdateNum(@RequestParam(value="luid",required = true) int luid,/**点赞评论者的**/
                             @RequestParam(value = "cid",required=true)int cid) {
       if(commentDao.CheckLike(cid,luid)==false)
       {commentDao.Updatelnum(cid);
       commentDao.InserLike(cid,luid);
       return 1;}
       else return 0;
    }
}
