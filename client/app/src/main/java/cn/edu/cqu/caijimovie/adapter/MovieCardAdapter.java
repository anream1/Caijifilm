package cn.edu.cqu.caijimovie.adapter;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.activities.MainActivity;
import cn.edu.cqu.caijimovie.activities.MovieInfoActivity;
import cn.edu.cqu.caijimovie.activities.SearchActivity;
import cn.edu.cqu.caijimovie.customize_widget.SimpleRatingView;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.fragments.ItemMovieFragment;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.MoviewCardViewHolder> {


    private final Context context;
    private List<Movie> resultList;
    private User user;

    public MovieCardAdapter(Context context, List<Movie> resultList, User user) {
        this.context = context;
        this.resultList = resultList;
        this.user = user;
    }

    @NonNull
    @Override
    //相当于getView方法中创建view和viewHolder
    public MoviewCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.cardview_movie, null);
        return new MoviewCardViewHolder(itemView);

    }

    @Override
    //数据和View绑定
    public void onBindViewHolder(@NonNull MoviewCardViewHolder moviewCardViewHolder, int i) {
        //根据位置得到对应的数据
        final Movie movie = resultList.get(i);
        String rate = String.valueOf(movie.getRate());
        String[] s1 = rate.split("\\.");
        String[] district = movie.getDistrict().split("/");
        String moviedis = "";
        for (String dis : district) {
            moviedis += dis.split("_")[1] + " ";
        }

        moviewCardViewHolder.textDuration.setText("片长: " + movie.getLength() + "分钟");
        moviewCardViewHolder.textTags.setText(movie.getShowtime() + "/" + moviedis + "/" + movie.getDirector() + "/" + movie.getActor().replace("/", " "));
        moviewCardViewHolder.textRatedInt.setText(s1[0] + ".");
        moviewCardViewHolder.textRatedDec.setText(s1[1]);
        moviewCardViewHolder.textViewTitleYear.setText(movie.getTitle() + "（" + movie.getShowtime() + "）");
        moviewCardViewHolder.xlhRatingBar.setNumStars(5);
        moviewCardViewHolder.xlhRatingBar.setRating((float) movie.getRate() / 2);
        moviewCardViewHolder.xlhRatingBar.setEnabled(false);
        moviewCardViewHolder.xlhRatingBar.setRatingView(new SimpleRatingView());
        Glide.with(context)
                .load(movie.getCover())
                .override(270, 400)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(moviewCardViewHolder.poster);


        moviewCardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"item",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MovieInfoActivity.class);
                intent.putExtra("onClickMovie", movie);
                intent.putExtra("currentuser", user);
                context.startActivity(intent);


            }
        });
    }

    //得到总条数
    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class MoviewCardViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView poster;
        private AppCompatTextView textViewTitleYear, textRatedInt, textRatedDec, textTags, textDuration;
        private cn.edu.cqu.caijimovie.customize_widget.XLHRatingBar xlhRatingBar;

        public MoviewCardViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.card_movie_poster);
            textViewTitleYear = itemView.findViewById(R.id.card_movie_title_year);
            textRatedInt = itemView.findViewById(R.id.ratingTextRatedInt);
            textRatedDec = itemView.findViewById(R.id.ratingTextRatedDec);
            textTags = itemView.findViewById(R.id.card_tags);
            textDuration = itemView.findViewById(R.id.card_duration);
            xlhRatingBar = itemView.findViewById(R.id.ratingBarRated);


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
