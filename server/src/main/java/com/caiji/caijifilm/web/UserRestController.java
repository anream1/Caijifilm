package com.caiji.caijifilm.web;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.caiji.caijifilm.pojo.Result;
import com.caiji.caijifilm.service.Impl.UserServiceImpl;
import com.caiji.caijifilm.service.UserService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import com.caiji.caijifilm.pojo.User;
import  com.caiji.caijifilm.pojo.Label;
import com.caiji.caijifilm.service.LabelService;

import  com.caiji.caijifilm.web.LabelRestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private LabelService labelService;


    /**
     * 登录
     * @param mail
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public Result LoginCheck(@RequestParam(value = "mail", required = true) String mail, @RequestParam(value = "password", required = true) String password) {
        System.out.println("开始查询...");
        System.out.println(mail);
        User user = userService.findUserByMail(mail);
        Result result = new Result();
        //System.out.println(user.getPassword());

        if (user == null) {
            System.out.println("登录失败！请检查！");
            result.setReturnValue(1);
            result.setObject(new User());
            result.setReturnMessage("账号不存在!");
            return result;
        } else if (password.equals(user.getPassword())) {
            user.setPassword("***");
            result.setReturnValue(0);
            result.setObject(user);
            result.setReturnMessage("登录成功");

            /*登录后更新用户登录时间*/
            //设置日期格式化样
            SimpleDateFormat ltime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            //格式化当前获取的时间
            String lasttime=ltime.format(new Date());
            //System.out.println("当前的系统日期为：" + lasttime);
            user.setLasttime(lasttime);
            userService.updateLastTime(user);
            result.setReturnMessage("更新用户登录时间成功"+lasttime);

            return result;
        } else {
            result.setReturnValue(2);
            result.setObject(new User());
            result.setReturnMessage("密码错误");

            return result;
        }
    }

    /**
     * 注册
     * @param mail
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/registered", method = RequestMethod.POST)
    public Result Registered(@RequestParam(value = "mail", required = true) String mail, @RequestParam(value = "password", required = true) String password) {
        System.out.println("开始注册...");
        Result result=new Result();
        User user=new User();
        if (userService.CheckMailexist(mail)==true){
            result.setReturnValue(3);
            result.setObject(new User());
            result.setReturnMessage("用户邮箱已存在！");
            return result;
        }
        else {
            /*注册时设置用户第一次登录时间*/
            //设置日期格式化样
            SimpleDateFormat  ltime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            //格式化当前获取的时间
            String lasttime=ltime.format(new Date());

            user.setLasttime(lasttime);

            user.setMail(mail);
            user.setPassword(password);
            user.setUid(userService.SeclectLastRecord()+1);
            user.setNickname("u"+user.getUid());
            userService.addUser(user);
            result.setReturnValue(4);
            result.setObject(user);
            result.setReturnMessage("注册成功！");
            return result;
        }
    }

    /**
     * 修改个人信息
     * @param mail
     * @param nickname
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/user/updatenickname", method = RequestMethod.POST)
    public Result UpdateNickName(@RequestParam(value = "mail", required = true) String mail,
                         @RequestParam(value = "nickname", required = true) String nickname) {
        System.out.println("开始修改个人信息...");
        Result result = new Result();
        User user = new User();
            if (userService.CheckMailexist(mail) == true) {
                user.setMail(mail);
                user.setNickname(nickname);
//                user.setSex(sex);

                // user.setPassword(password);
                userService.updateUser(user);
                result.setReturnValue(7);
                result.setObject(userService.findUserByMail(mail));
                result.setReturnMessage("修改个人信息成功！");
                return result;
            } else {
                result.setReturnValue(8);
                result.setObject(null);
                result.setReturnMessage("该用户不存在！");
                return result;
            }
        }

    @RequestMapping(value = "/user/updatephone", method = RequestMethod.POST)
    public Result UpdatePhone(@RequestParam(value = "mail", required = true) String mail,
                         @RequestParam(value = "phone", required = true) String phone
    ) {
        System.out.println("开始修改个人信息...");
        Result result = new Result();
        User user = new User();
        if (userService.CheckMailexist(mail) == true) {
            user.setMail(mail);
            user.setPhone(phone);
            userService.updateUser(user);
            result.setReturnValue(7);
            result.setObject(userService.findUserByMail(mail));
            result.setReturnMessage("修改个人信息成功！");
            return result;
        } else {
            result.setReturnValue(8);
            result.setObject(null);
            result.setReturnMessage("该用户不存在！");
            return result;
        }
    }


    /**
     * 修改用户自选标签
     * @param uid
     * @param userlabel
     * @return
     */
    @RequestMapping(value = "/user/updatelabel", method = RequestMethod.POST)
    public Result UpdateUserLabel(@RequestParam(value = "uid",required = true)int uid,@RequestParam(value = "userlabel",required=true)String userlabel)
    {
        System.out.println("开始修改用户标签");
        Result result=new Result();
        if(labelService.DeleteUserLabel(uid)==true) {System.out.println("删除成功");}
        else{System.out.println("删除失败");} //删除label表中用户的自选标签方便后面操作
        String strlabel=null;//将String类型的变量strlabel初始化
        if(userService.UpdateUserLabel(uid,userlabel)==true)  //判断语句 用来判断更新userinfo表操作是否完成
        {
            //将客户端传入的label字符串识别并将其存入label表中
            String str="";
            for(int nmsl=0;nmsl<userlabel.length();nmsl++){
                char a=userlabel.charAt(nmsl);
                if(a=='/'){
                    if (labelService.CheckUserLabelexist(uid,str)==false){
                        if(labelService.InsertUserLabel(uid,str)==true)
                            System.out.println("插入成功");
                        else System.out.println("插入失败");
                    }
                    else System.out.println("用户标签已存在");
                    System.out.println(str);
                    str="";
                }
                else str=str+a;
            }
            System.out.println("修改成功");
            result.setReturnValue(0);
            result.setObject(new User());//输出服务器端要求插入的字符
            result.setReturnMessage("修改成功！");
            return result;}
        else
        {
            System.out.println("修改失败");
            result.setReturnValue(1);
            result.setObject(new User());
            result.setReturnMessage("修改失败!");
            return result;
        }
    }

    //用户头像修改
    @RequestMapping(value = "/user/updatepotrait", method = RequestMethod.POST)
    public Result Update(@RequestParam(value = "mail", required = true) String mail,
                         @RequestParam(value = "potrait", required = true) MultipartFile potraitfile) {
        System.out.println("开始修改头像...");
        Result result = new Result();
        User user = new User();
        byte[] potrait = null;
        if (userService.CheckMailexist(mail) == true) {
            try {
                //File file = new File( );
                //File file = File.createTempFile("tmp", null);
                //potraitfile.transferTo(file);
                //InputStream fin = new FileInputStream(file);
                InputStream fin = potraitfile.getInputStream();
                //ByteBuffer nbf= ByteBuffer.allocate((int)file.length());//分配文件的字节缓存区
                potrait = new byte[fin.available()];
                fin.read(potrait);
                fin.close();
               /* byte[] array=new byte[1024];
                int offset=0,length=0;
                //存放字节流
                while((length=fin.read(array))>0)
                {
                    if(length!=1024)
                        nbf.put(array,0,length);
                    else
                        nbf.put(array);
                    offset +=length;
                }
                fin.close();
                potrait=nbf.array();*/
                System.out.println(potrait.length);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            user.setMail(mail);
            user.setPotrait(potrait);
            userService.updatePortait(user);
            result.setReturnValue(4);
            result.setObject(userService.findUserByMail(mail));
            result.setReturnMessage("修改头像成功！");
            return result;
        } else {
            result.setReturnValue(5);
            result.setObject(null);
            result.setReturnMessage("该用户不存在！");
            return result;
        }
    }

    //获取用户头像
    @RequestMapping(value="/user/getpotrait",method=RequestMethod.GET)
    public Result getPhotoByMail (@RequestParam(value = "mail", required = true) String mail
                                  //final HttpServletResponse response
    ) {
        System.out.println("开始查询用户头像...");
        User user = new User();
        Result result = new Result();

        if (userService.CheckMailexist(mail) == true) {
            user = userService.findUserByMail(mail);
            //byte[] data = user.getPotrait();
            if (user.getPotrait() != null) {
                result.setReturnMessage("用户头像数据流为："+ user.getPotrait());
                result.setObject(user.getMail());
                result.setReturnValue(1);
                return result;
            } else {
                result.setReturnValue(2);
                result.setReturnMessage(user.getMail() + "用户还未设置头像！");
                //System.out.println(mail + "用户还未设置头像！");
                return result;
            }
        }
        else
        {
            result.setReturnValue(3);
            result.setReturnMessage("用户不存在");
            //System.out.println("用户不存在");
            return result;
        }
    }
}