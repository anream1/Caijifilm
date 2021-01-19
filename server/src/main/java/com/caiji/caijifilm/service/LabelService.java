package com.caiji.caijifilm.service;
import com.caiji.caijifilm.dao.LabelDao;
import com.caiji.caijifilm.pojo.Label;
import com.caiji.caijifilm.pojo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface LabelService {
    /**
     * 查询一个用户的所有标签
     **/
    List<String> Backlabel(Integer uid);

    /**
     * 查询一个用户的自选标签
     * @param uid
     * @return
     */
    List<String> BackUserLabel(int uid);

    /**
     * 查询一个用户的系统标签
     * @param uid
     * @return
     */
    List<String> BackSystemLabel(int uid);

    /**
     * 插入系统标签
     */
    void InsertSystemLabel(int uid ,String label);

    /**
     * 检查该用户标签是否已经存在
     */
    boolean CheckUserLabelexist(int uid ,String label);

    /**
     * 检查删除用户自选标签是否成功
     */
    boolean DeleteUserLabel(int uid);

    /**
     * 插入用户自选标签
     * @param uid
     * @param label
     * @return
     */
    boolean InsertUserLabel(int uid,String label);
}
