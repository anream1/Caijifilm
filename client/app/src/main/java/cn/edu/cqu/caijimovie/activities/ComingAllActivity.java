package cn.edu.cqu.caijimovie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.adapter.MovieComingAdapter;
import cn.edu.cqu.caijimovie.adapter.MovieHotAdapter;
import cn.edu.cqu.caijimovie.entities.MovieComing;
import cn.edu.cqu.caijimovie.entities.MovieHot;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ComingAllActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewComing;
    private List<MovieComing> movieComingList = new ArrayList<>();
    private MovieComingAdapter adapterComing;
    private GridLayoutManager layoutManager1;
    private User user;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_all);
        user = (User) getIntent().getSerializableExtra("currentuser");
        toolbar = findViewById(R.id.toolbar);

        mRecyclerViewComing = (RecyclerView) findViewById(R.id.mRecyclerViewComing);
        layoutManager1 = new GridLayoutManager(ComingAllActivity.this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerViewComing.setLayoutManager(layoutManager1);

        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/movie/moviecoming", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseString = response.body().string();
                movieComingList = new Gson().fromJson(responseString, new TypeToken<List<MovieComing>>() {
                }.getType());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterComing = new MovieComingAdapter(ComingAllActivity.this, movieComingList);
                        mRecyclerViewComing.setAdapter(adapterComing);
                        mRecyclerViewComing.setItemAnimator(new DefaultItemAnimator());
                    }
                });
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
