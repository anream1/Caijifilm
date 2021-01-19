package cn.edu.cqu.caijimovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.activities.MovieInfoActivity;
import cn.edu.cqu.caijimovie.customize_widget.SimpleRatingView;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.MovieHot;
import cn.edu.cqu.caijimovie.entities.User;

public class MovieHotAdapter extends RecyclerView.Adapter<MovieHotAdapter.MovieHotViewHolder> {


    private final Context context;
    private List<Movie> resultList;
    private User user;


    public MovieHotAdapter(Context context, List<Movie> resultList, User user) {
        this.context = context;
        this.resultList = resultList;
        this.user = user;
    }

    @NonNull
    @Override
    //相当于getView方法中创建view和viewHolder
    public MovieHotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.cardview_moviehot, null);
        return new MovieHotViewHolder(itemView);
    }


    //得到总条数
    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHotViewHolder movieHotViewHolder, int i) {
        final Movie movieHot = resultList.get(i);

        movieHotViewHolder.movieTitle.setText(movieHot.getTitle().split(" ")[0]);
        movieHotViewHolder.textRate.setText("评分：" + String.valueOf(movieHot.getRate()));
        Glide.with(context)
                .load(movieHot.getCover())
                .override(270, 400)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(movieHotViewHolder.cover);


        movieHotViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieInfoActivity.class);
                intent.putExtra("onClickMovie", movieHot);
                intent.putExtra("currentuser", user);
                context.startActivity(intent);
            }
        });
    }

    class MovieHotViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView cover;
        private AppCompatTextView movieTitle, textRate;
        //private cn.edu.cqu.caijimovie.customize_widget.XLHRatingBar xlhRatingBar;

        public MovieHotViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.imageViewCover);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            textRate = itemView.findViewById(R.id.ratingText);
            //xlhRatingBar = itemView.findViewById(R.id.ratingBarRated);


            //设置点击回调事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    public void update(List<Movie> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }


}
