package com.caiji.caijifilm.pojo;

public class Label {
    User user=new User();
    public  Label(){}
    private int uid=user.getUid();
    private String[] Array;


    public int getUid() {
        return uid;
    }



    public String[] getArray() {
        return Array;
    }

//    public void setArray(String label) {
////        String str;
////        for(int i=0;i<label.length();i=i+2)
////        {
////            char a=label.charAt(i);
////            char b=label.charAt(i+1);
////            String s = String.valueOf(a);
////            String q = String.valueOf(b);
////            str =s+q;
////            Insertlable(str);
////        }
//        this."无语",};Array={
//    }


   //检测该用户是否已经含有该标签，含有返回true，不含有返回false
    public  boolean useLoop(String[] Array,  String label) {
            for (String s : Array) {
                if (s.equals(label))
                    return true;
            }
            return false;
    }

    //向现有的Array数组中添加标签
    public void  Insertlable (String label){
        if (useLoop(Array,label)) { }
        else{
         int size=Array.length;
         String[] tmp = new String[size + 1];
            for (int i = 0; i < size; i++){  //先遍历将原来的字符串数组数据添加到临时字符串数组
                tmp[i] =Array[i];
            }
            tmp[size] = label;  //在最后添加上需要追加的数据
            this.Array=tmp;  //返回拼接完成的字符串数组
        }
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    //将传回的电影标签长字符串分割成一个一个的标签并存入类中的数组中
    public  String[]   cutString(String movielabel){
        String str;
        for(int i=0;i<movielabel.length();i=i+3)
        {
          char a=movielabel.charAt(i);
          char b=movielabel.charAt(i+1);
          String s = String.valueOf(a);
          String q = String.valueOf(b);
          str =s+q;
          Insertlable(str);
        }
        return Array;
    }
}
