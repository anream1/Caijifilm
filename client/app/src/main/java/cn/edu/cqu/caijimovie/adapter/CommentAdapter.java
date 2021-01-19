package cn.edu.cqu.caijimovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.activities.MovieInfoActivity;
import cn.edu.cqu.caijimovie.customize_widget.SimpleRatingView;
import cn.edu.cqu.caijimovie.entities.Comment;
import cn.edu.cqu.caijimovie.entities.CommentLike;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.MovieHot;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private final Context context;
    private List<CommentLike> resultList;
    private User user;
    private Movie movie;

    public CommentAdapter(Context context, List<CommentLike> resultList, User user, Movie movie) {
        this.context = context;
        this.resultList = resultList;
        this.user = user;
        this.movie = movie;
    }

    @NonNull
    @Override
    //相当于getView方法中创建view和viewHolder
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.cardview_comment, null);
        return new CommentAdapter.CommentViewHolder(itemView);

    }

    @Override
    //数据和View绑定
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder commentViewHolder, int i) {
        //根据位置得到对应的数据
        final CommentLike comment = resultList.get(i);
        final ImageView imgView = commentViewHolder.imgAgree;
        final TextView txtLike = commentViewHolder.textLike;
        if (comment.isLike()) {
            commentViewHolder.imgAgree.setImageResource(R.drawable.ic_thumb_up_black_14dp);
        } else {
            commentViewHolder.imgAgree.setImageResource(R.drawable.ic_thumb_up_grey_14dp);
        }
        commentViewHolder.textComment.setText(comment.getContent());
        commentViewHolder.textDate.setText(comment.getTime().substring(0, 10));
        commentViewHolder.textID.setText("用户ID:" + comment.getCuid());
        commentViewHolder.textLike.setText(comment.getLnum() + "");
        commentViewHolder.textRate.setText("评分:" + comment.getRate());
        commentViewHolder.imgAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comment.isLike()) {
                    HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/comment/updatenum/?luid=" + user.getUid() + "&cid=" + comment.getCid(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseString = response.body().string();
                            //movieHotList = new Gson().fromJson(responseString, new TypeToken<List<MovieHot>>() {}.getType());
                        }
                    });
                    imgView.setImageResource(R.drawable.ic_thumb_up_black_14dp);
                    int rate = comment.getLnum() + 1;
                    txtLike.setText(rate + "");


                }

            }
        });


        commentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAgree;
        private TextView textID, textComment, textDate, textLike, textRate;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAgree = itemView.findViewById(R.id.card_comment_thumb);
            textID = itemView.findViewById(R.id.card_comment_username);
            textComment = itemView.findViewById(R.id.card_comment_shot_comment);
            textDate = itemView.findViewById(R.id.card_comment_datetime);
            textLike = itemView.findViewById(R.id.card_comment_like_num);
            textRate = itemView.findViewById(R.id.card_comment_rate);


            //设置点击回调事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }


    public void update(List<CommentLike> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

}
