package com.caiji.caijifilm.web;

import com.caiji.caijifilm.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.caiji.caijifilm.pojo.Moment;
import com.caiji.caijifilm.dao.MomentDao;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MomentRestController {
    @Autowired
    private MomentService momentService;
    @Autowired
    private MomentDao momentDao;

    /**根据用户id返回该用户所有动态信息**/
    @RequestMapping(value = "/moment/uid",method = RequestMethod.GET)
    public List<Moment> SelectInfoByUid(@RequestParam(value = "uid",required = true)int uid)
    {
        return momentService.SelectInfoByUid(uid);
    }

    /**插入用户动态信息**/
    @RequestMapping(value = "/moment/insert",method = RequestMethod.POST)
    public Integer InsertMoment(@RequestParam(value = "content",required = true)String content,
                                 @RequestParam(value = "uid",required = true)int uid
                                 //@RequestParam(value = "time",required = true) Date time,
                                )
    {
        Moment moment=new Moment();
        if (momentService.InsertMomentInfo(content,uid)==true)
            return momentDao.Selectlastid();
        else return 0;
    }

    /**更新用户动态的点赞数或点踩数**/
    @RequestMapping(value = "/moment/updatenum",method = RequestMethod.POST)
    public Integer UpdateNum(//@RequestParam(value = "uid",required = true)int uid,
                             @RequestParam(value = "id",required = true)int id,
                             @RequestParam(value = "operand",required = true)int operand) {
        if(momentService.UpdateLNumDLNum(id,operand)==true)
            return 1;
        else return 0;}
}
