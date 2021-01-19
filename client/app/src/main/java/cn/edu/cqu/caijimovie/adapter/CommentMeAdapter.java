package cn.edu.cqu.caijimovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.entities.CommentLike;
import cn.edu.cqu.caijimovie.entities.CommentMovie;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CommentMeAdapter extends RecyclerView.Adapter<CommentMeAdapter.CommentMeViewHolder> {
    private final Context context;
    private List<CommentMovie> resultList;
    private User user;


    public CommentMeAdapter(Context context, List<CommentMovie> resultList, User user) {
        this.context = context;
        this.resultList = resultList;
        this.user = user;

    }

    @NonNull
    @Override
    //相当于getView方法中创建view和viewHolder
    public CommentMeAdapter.CommentMeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.cardview_mycomments, null);
        return new CommentMeAdapter.CommentMeViewHolder(itemView);

    }

    @Override
    //数据和View绑定
    public void onBindViewHolder(@NonNull CommentMeAdapter.CommentMeViewHolder commentMeViewHolder, int i) {
        //根据位置得到对应的数据
        final CommentMovie comment = resultList.get(i);

        commentMeViewHolder.textComment.setText(comment.getContent());
        commentMeViewHolder.textDate.setText("发表时间：" + comment.getTime().substring(0, 10));
        commentMeViewHolder.textTitle.setText("评论电影:" + comment.getMovietitle().split(" ")[0]);
        commentMeViewHolder.textLike.setText(comment.getLnum() + "");
        commentMeViewHolder.textRate.setText("评分:" + comment.getRate());


        commentMeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }


    //得到总条数
    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class CommentMeViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, textComment, textDate, textLike, textRate;


        public CommentMeViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.movietitle);
            textComment = itemView.findViewById(R.id.content);
            textDate = itemView.findViewById(R.id.commenttime);
            textLike = itemView.findViewById(R.id.agrees);
            textRate = itemView.findViewById(R.id.rate);


            //设置点击回调事件
//            itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }

    }


    public void update(List<CommentMovie> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

}