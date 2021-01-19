package com.caiji.caijifilm.service.Impl;

import com.caiji.caijifilm.dao.LabelDao;
import com.caiji.caijifilm.dao.MovieDao;
import com.caiji.caijifilm.dao.UserDao;
import com.caiji.caijifilm.pojo.Label;
import com.caiji.caijifilm.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelDao labelDao;
    private LabelService labelService;

    /**查询一个用户的所有标签**/
    @Override
    public List<String> Backlabel(Integer uid){

        return labelDao.Backlabel(uid);

    }

   /**查询一个用户的自选标签**/
    @Override
    public List<String> BackUserLabel(int uid){
        return labelDao.BackUserLabel(uid);
    }

   /** 查询一个用户的系统标签**/
    @Override
    public List<String> BackSystemLabel(int uid){
        return labelDao.BackSystemLabel(uid);
    }

   /**向label表中添加系统标签**/
    @Override
    public void InsertSystemLabel(int uid ,String label) {
        System.out.println("开始插入");
        labelDao.InsertSystemLabel(uid, label);
//        System.out.println("开始插入");
//        String str = "";
//        for (int lab = 0; lab < label.length(); lab++) {
//            char a = label.charAt(lab);
//            if (a == '/') {
//                if (labelDao.CheckUserlabelexist(uid, str) == 0) {
//                    labelDao.InsertSystemLabel(uid, str);
//                }
//                str = "";
//            } else str = str + a;
//        }
    }

    /**检查删除用户自选标签是否成功**/
    @Override
    public boolean DeleteUserLabel(int uid){
        boolean flag=false;
        try{
            labelDao.DeleteUserLabel(uid);
            flag=true;
        }catch(Exception e){
            System.out.println("新增失败!");
            e.printStackTrace();
        }
        return flag;
    }

    /**检测该用户标签是否存在 true为存在**/
    @Override
    public boolean CheckUserLabelexist(int uid ,String label){
        boolean flag=false;
        if(labelDao.CheckUserlabelexist(uid,label)==1)
        {
            flag=true;
        }
        return flag;
    }

    /**
     * 查询插入用户自选标签是否成功
     */
    @Override
    public  boolean InsertUserLabel(int uid,String label) { boolean flag=false;
        try{
            labelDao.InsertUserLabel(uid, label);
            flag=true;
        }catch(Exception e){
            System.out.println("新增失败!");
            e.printStackTrace();
        }
        return flag;}
}
