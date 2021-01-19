package cn.edu.cqu.caijimovie.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.activities.SearchActivity;
import cn.edu.cqu.caijimovie.adapter.MovieCardAdapter;
import cn.edu.cqu.caijimovie.adapter.MovieHotAdapter;
import cn.edu.cqu.caijimovie.adapter.MovieRecommendAdapter;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.MovieHot;
import cn.edu.cqu.caijimovie.entities.MovieRecommend;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import cn.edu.cqu.caijimovie.utils.SpacesItemDecoration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainRecommendFragment extends MyFragment {

    private ArrayList<Movie> recommendList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MovieRecommendAdapter movieRecommendAdapter;
    private User user;
    private List<MovieRecommend> movieRecommends = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private ImageButton refresh;
    private ProgressBar progressBar;

    public MainRecommendFragment() {
        super("Recommend");
        // Required empty public constructor
    }

    //
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initData() {
        //Todo:用来获取推荐的Movie类列表,并添加到RecommendList里面

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_recommend, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {//判断Fragment已经依附Activity
            user = (User) getArguments().getSerializable("currentuser");
        }
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.main_recommend_recycler);
        refresh = (ImageButton) getActivity().findViewById(R.id.refresh);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);

        layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        //设置item间间隔
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));
        progressBar.setVisibility(View.GONE);

        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/recommend/user/?uid=" + user.getUid(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseString = response.body().string();
                movieRecommends = new Gson().fromJson(responseString, new TypeToken<List<MovieRecommend>>() {
                }.getType());


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movieRecommendAdapter = new MovieRecommendAdapter(getActivity(), movieRecommends, user);
                        recyclerView.setAdapter(movieRecommendAdapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                    }
                });
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/recommend/user/?uid=" + user.getUid(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseString = response.body().string();
                        movieRecommends = new Gson().fromJson(responseString, new TypeToken<List<MovieRecommend>>() {
                        }.getType());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                movieRecommendAdapter = new MovieRecommendAdapter(getActivity(), movieRecommends, user);
                                recyclerView.setAdapter(movieRecommendAdapter);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                progressBar.setVisibility(View.GONE);

                            }
                        });
                    }
                });
            }
        });

    }


}
