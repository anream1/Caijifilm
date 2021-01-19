package com.caiji.caijifilm.web;

import com.caiji.caijifilm.pojo.Result;
import com.caiji.caijifilm.service.Impl.LabelServiceImpl;
import com.caiji.caijifilm.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.caiji.caijifilm.pojo.Label;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class LabelRestController {
    @Autowired
    private LabelService labelService;

    //根据用户信息返回其标签
    @RequestMapping(value = "/label/returnlabel", method = RequestMethod.GET)
    public Result Backlabel(@RequestParam(value = "uid", required = true) int uid){
        System.out.println("开始查询");
        Result result=new Result();
        //Label label=new Label();
        List<String> userlabel= labelService.Backlabel(uid);
        result.setReturnMessage("返回用户标签");
        result.setObject(userlabel);
        result.setReturnValue(5);
        return result;
    }
}

