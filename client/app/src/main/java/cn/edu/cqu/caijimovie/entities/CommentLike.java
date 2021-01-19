package cn.edu.cqu.caijimovie.entities;

public class CommentLike extends Comment {
    private boolean like;

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
