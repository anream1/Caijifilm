package cn.edu.cqu.caijimovie.adapter;

import android.content.Context;
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
import cn.edu.cqu.caijimovie.entities.MovieComing;


public class MovieComingAdapter extends RecyclerView.Adapter<MovieComingAdapter.MovieComingViewHolder> {

    private final Context context;
    private List<MovieComing> resultList;

    public MovieComingAdapter(Context context, List<MovieComing> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    //相当于getView方法中创建view和viewHolder
    public MovieComingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context, R.layout.cardview_moviecoming, null);
        return new MovieComingViewHolder(itemView);
    }


    //得到总条数
    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MovieComingViewHolder movieComingViewHolder, int i) {
        final MovieComing movieComing = resultList.get(i);

        movieComingViewHolder.movieTitle.setText(movieComing.getTitle());
        movieComingViewHolder.showtime.setText(" " + movieComing.getShowtime() + " ");
        Glide.with(context)
                .load(movieComing.getCover())
                .override(270, 380)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(movieComingViewHolder.cover);


        movieComingViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "即将上映，敬请期待……", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, MovieActivity.class);
//                intent.putExtra("onClickMovie", movie);
//                context.startActivity(intent);
            }
        });
    }

    class MovieComingViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView cover;
        private AppCompatTextView movieTitle, showtime;

        public MovieComingViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.imageViewCover);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            showtime = itemView.findViewById(R.id.datetime);
            //xlhRatingBar = itemView.findViewById(R.id.ratingBarRated);


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


    public void update(List<MovieComing> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }
}
