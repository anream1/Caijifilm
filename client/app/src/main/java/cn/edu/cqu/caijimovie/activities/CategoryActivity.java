package cn.edu.cqu.caijimovie.activities;

import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.adapter.MovieCardAdapter;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import cn.edu.cqu.caijimovie.utils.SpacesItemDecoration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class CategoryActivity extends AppCompatActivity {

    private List<Movie> movieList, listNew;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MovieCardAdapter adapter;
    private int i = 1;
    private String category;
    private Button btnMore;
    private ProgressBar progressBar;
    private TextView textCategory;
    private User user;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        user = (User) getIntent().getSerializableExtra("currentuser");
        category = getIntent().getStringExtra("movieCategory");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        textCategory = (TextView) findViewById(R.id.textCategory);
        btnMore = (Button) findViewById(R.id.btn_more);
        toolbar = findViewById(R.id.toolbar);
        layoutManager = new LinearLayoutManager(CategoryActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textCategory.setText(category);
        btnMore.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(layoutManager);
        //设置item间间隔
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));


        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/movie/movieinfobylabel?label=" + category + "&begin=" + i, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseString = response.body().string();

                movieList = new Gson().fromJson(responseString, new TypeToken<List<Movie>>() {
                }.getType());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        btnMore.setVisibility(View.VISIBLE);
                        adapter = new MovieCardAdapter(CategoryActivity.this, movieList, user);
                        recyclerView.setAdapter(adapter);
                        i += 10;


                    }
                });

            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btnMore.setVisibility(View.GONE);
                HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/movie/movieinfobylabel?label=" + category + "&begin=" + i, new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                btnMore.setVisibility(View.VISIBLE);
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String responseString = response.body().string();

                        listNew = new Gson().fromJson(responseString, new TypeToken<List<Movie>>() {
                        }.getType());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                movieList.addAll(listNew);
                                progressBar.setVisibility(View.GONE);
//                                adapter = new MovieCardAdapter(CategoryActivity.this,movieList);
//                                recyclerView.setAdapter(adapter);
                                adapter.update(movieList);

                                i += 10;
                                btnMore.setVisibility(View.VISIBLE);


                            }
                        });

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
