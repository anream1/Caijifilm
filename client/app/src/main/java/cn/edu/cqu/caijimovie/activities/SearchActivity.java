package cn.edu.cqu.caijimovie.activities;

import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.adapter.MovieCardAdapter;
import cn.edu.cqu.caijimovie.adapter.SearchAdapter;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.Result;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import cn.edu.cqu.caijimovie.utils.SpacesItemDecoration;
import cn.edu.cqu.caijimovie.utils.UrlUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class SearchActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private String searchContent;
    private List<Movie> movieList = new ArrayList<>();
    private MovieCardAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        user = (User) getIntent().getSerializableExtra("currentuser");
        searchContent = getIntent().getStringExtra("searchText");
        Log.i("tag", "onCreate: " + searchContent);
        relativeLayout = (RelativeLayout) findViewById(R.id.search_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mSearchView = (SearchView) findViewById(R.id.search_movie);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(true);


        mRecyclerView = (RecyclerView) findViewById(R.id.result_recycler_verticle);

        layoutManager = new LinearLayoutManager(SearchActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        //设置item间间隔
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(30));

        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/movie/movieinfo?search=" + searchContent, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Snackbar.make(relativeLayout, "网络异常", LENGTH_LONG).show();
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
                        if (movieList.isEmpty()) {
                            Snackbar.make(relativeLayout, "没有搜索到你想要的结果", LENGTH_SHORT).show();
                        } else {

                            adapter = new MovieCardAdapter(SearchActivity.this, movieList, user);
                            mRecyclerView.setAdapter(adapter);
                        }

                    }
                });


            }
        });

//        adapter = new MovieCardAdapter(this,movieList);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(adapter);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                searchContent = s;
                progressBar.setVisibility(View.VISIBLE);
                HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/movie/movieinfo?search=" + searchContent, new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(relativeLayout, "网络异常", LENGTH_LONG).show();
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
                                if (movieList.isEmpty()) {
                                    Snackbar.make(relativeLayout, "没有搜索到你想要的结果", LENGTH_SHORT).show();
                                } else {
                                    adapter = new MovieCardAdapter(SearchActivity.this, movieList, user);
                                    mRecyclerView.setAdapter(adapter);
                                }

                            }
                        });


                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
    }
}
