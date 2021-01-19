package cn.edu.cqu.caijimovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.activities.MovieInfoActivity;
import cn.edu.cqu.caijimovie.customize_widget.SimpleRatingView;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.MovieRecommend;
import cn.edu.cqu.caijimovie.entities.User;

public class MovieRecommendAdapter extends RecyclerView.Adapter<MovieRecommendAdapter.MovieRecommendViewHolder> {

    private final Context context;
    private List<MovieRecommend> movieList;
    private User user;

    public MovieRecommendAdapter(Context context, List<MovieRecommend> movieList, User user) {
        this.context = context;
        this.movieList = movieList;
        this.user = user;
    }

    @NonNull
    @Override
    //相当于getView方法中创建view和viewHolder
    public MovieRecommendAdapter.MovieRecommendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.cardview_recommend_movie, null);
        return new MovieRecommendAdapter.MovieRecommendViewHolder(itemView);
    }

    @Override
    //数据和View绑定
    public void onBindViewHolder(@NonNull MovieRecommendAdapter.MovieRecommendViewHolder MovieRecommendViewHolder, int i) {
        //根据位置得到对应的数据
        final MovieRecommend movie = movieList.get(i);
        String rate = String.valueOf(movie.getRate());
        String[] district = movie.getDistrict().split("/");
        String moviedis = "";
        for (String dis : district) {
            moviedis += dis.split("_")[1] + " ";
        }
        MovieRecommendViewHolder.textRate.setText("评分:" + rate);
        MovieRecommendViewHolder.textTags.setText(movie.getShowtime() + "/" + moviedis + "/" + movie.getDirector() + "/" + movie.getActor().replace("/", " "));
        MovieRecommendViewHolder.textViewTitleYear.setText(movie.getTitle() + "（" + movie.getShowtime() + "）");
        MovieRecommendViewHolder.textReason.setText(movie.getReason());
        Glide.with(context)
                .load(movie.getCover())
                .override(270, 400)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(MovieRecommendViewHolder.poster);


        MovieRecommendViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"item",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MovieInfoActivity.class);
                intent.putExtra("onClickMovie", (Movie) movie);
                intent.putExtra("currentuser", user);
                context.startActivity(intent);


            }
        });

    }


    //得到总条数
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieRecommendViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView poster;
        private AppCompatTextView textViewTitleYear, textRate, textTags, textReason;


        public MovieRecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.card_recommend_movie_poster);
            textViewTitleYear = itemView.findViewById(R.id.card_recommend_movie_title_year);
            textRate = itemView.findViewById(R.id.card_recommend_ratingText);
            textReason = itemView.findViewById(R.id.card_recommend_reason);
            textTags = itemView.findViewById(R.id.card_recommend_tags);


            //设置点击回调事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(onItemClickListener!=null){
//                        onItemClickListener.onItemClick(v,resultList.get(getLayoutPosition()));
//                    }
                    //Toast.makeText(context,"data="+recommendList.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
