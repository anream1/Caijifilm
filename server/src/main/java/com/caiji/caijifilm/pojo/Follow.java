package com.caiji.caijifilm.pojo;

public class Follow {
    Movie movie=new Movie();
    User user=new User();
    private int mid=movie.getMovieId();
    private int uid=user.getUid();

    public int getFollownum() {
        return follownum;
    }

    public void setFollownum(int follownum) {
        this.follownum = follownum;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    private int follownum=movie.getFollownum();
}
