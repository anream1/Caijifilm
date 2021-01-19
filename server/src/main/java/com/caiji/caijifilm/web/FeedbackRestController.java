package com.caiji.caijifilm.web;

import com.caiji.caijifilm.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class FeedbackRestController {
    @Autowired
    private FeedbackService feedbackService;

    /**插入用户反馈**/
    @RequestMapping(value = "/feedback/insert",method = RequestMethod.POST)
    public Integer InsertFeedback(@RequestParam(value = "uid",required = true)int uid,
                                 @RequestParam(value = "feedback",required = true)String feedback)
    {
        if (feedbackService.InsertFeedback(uid, feedback)==true)
            return 1;
        else return 0;
    }
}
