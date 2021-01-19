package cn.edu.cqu.caijimovie.activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.adapter.CommentAdapter;
import cn.edu.cqu.caijimovie.adapter.MovieCardAdapter;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import cn.edu.cqu.caijimovie.utils.SpacesItemDecoration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyMoviesActivity extends AppCompatActivity {
    private Switch mSwitch;
    private RecyclerView recyclerView;
    private MovieCardAdapter adapter1, adapter2;
    private List<Movie> movieLike = new ArrayList<>();
    private List<Movie> movieWatched = new ArrayList<>();
    private User user;
    private GridLayoutManager layoutManager;
    private ImageButton back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_movies);
        user = (User) getIntent().getSerializableExtra("currentuser");
        mSwitch = findViewById(R.id.switch1);
        back = (ImageButton) findViewById(R.id.my_movie_back);
        recyclerView = (RecyclerView) findViewById(R.id.movies);
        layoutManager = new GridLayoutManager(MyMoviesActivity.this, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/follow/movieinfobyuid?uid=" + user.getUid(), new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseString = response.body().string();
                movieLike = new Gson().fromJson(responseString, new TypeToken<List<Movie>>() {
                }.getType());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter1 = new MovieCardAdapter(MyMoviesActivity.this, movieLike, user);
                        recyclerView.setAdapter(adapter1);


                    }
                });


            }
        });

        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/comment/movieinfobyid?uid=" + user.getUid(), new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseString = response.body().string();
                movieWatched = new Gson().fromJson(responseString, new TypeToken<List<Movie>>() {
                }.getType());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter2 = new MovieCardAdapter(MyMoviesActivity.this, movieWatched, user);


                    }
                });

            }
        });


        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Todo
                    System.out.println("我看过的");
                    loadWatched();
                } else {

                    System.out.println("我想看的");
                    loadWannaWatch();
                }
            }
        });


    }

    //Todo:加载你想看的
    private void loadWannaWatch() {

        recyclerView.setAdapter(adapter1);


    }

    //Todo:加载你看过的
    private void loadWatched() {


        recyclerView.setAdapter(adapter2);

    }
}
