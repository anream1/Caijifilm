package cn.edu.cqu.caijimovie.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.activities.MainActivity;
import cn.edu.cqu.caijimovie.activities.MovieInfoActivity;
import cn.edu.cqu.caijimovie.activities.MyMoviesActivity;
import cn.edu.cqu.caijimovie.activities.PersonalSettingActivity;
import cn.edu.cqu.caijimovie.activities.SearchActivity;
import cn.edu.cqu.caijimovie.adapter.CommentMeAdapter;
import cn.edu.cqu.caijimovie.adapter.MovieHotAdapter;
import cn.edu.cqu.caijimovie.entities.CommentMovie;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;


public class MainMeFragment extends MyFragment {
    private User user;
    private FloatingActionButton fabSetting;
    private TextView textNickname, textMail, movieAll, noMovie, noComment;
    private RecyclerView recyclerViewLike, recyclerViewComment;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager layoutManager1;
    private MovieHotAdapter adapterMovie;
    private CommentMeAdapter adapterComment;
    private List<Movie> movies = new ArrayList<>();
    private List<CommentMovie> commentMovies = new ArrayList<>();


    public MainMeFragment() {
        super("Me");
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {//判断Fragment已经依附Activity
            user = (User) getArguments().getSerializable("currentuser");
        }

        textMail = (TextView) getActivity().findViewById(R.id.frag_me_email);
        textNickname = (TextView) getActivity().findViewById(R.id.frag_me_username);
        movieAll = (TextView) getActivity().findViewById(R.id.movieall);
        recyclerViewLike = (RecyclerView) getActivity().findViewById(R.id.include).findViewById(R.id.watched);
        recyclerViewComment = (RecyclerView) getActivity().findViewById(R.id.include).findViewById(R.id.content_me_recycler);
        noMovie = (TextView) getActivity().findViewById(R.id.noMovie);
        noComment = (TextView) getActivity().findViewById(R.id.noComment);
        noMovie.setVisibility(View.GONE);
        noComment.setVisibility(View.GONE);
        textMail.setText(user.getMail());
        textNickname.setText(user.getNickname());
        fabSetting = (FloatingActionButton) getActivity().findViewById(R.id.fab_setting);

        fabSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PersonalSettingActivity.class);
                intent.putExtra("currentuser", user);
                startActivityForResult(intent, 2);
            }
        });

        //显示全部电影
        movieAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MyMoviesActivity.class);
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });

//        layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewLike.setLayoutManager(layoutManager);
        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/follow/movieinfobyuid/?uid=" + user.getUid(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseString = response.body().string();
                movies = new Gson().fromJson(responseString, new TypeToken<List<Movie>>() {
                }.getType());


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (commentMovies.isEmpty()) {
                            noMovie.setVisibility(View.VISIBLE);
                        } else {
                            adapterMovie = new MovieHotAdapter(getActivity(), movies, user);
                            recyclerViewLike.setAdapter(adapterMovie);
                            noMovie.setVisibility(View.GONE);
                        }


                    }
                });
            }
        });

        layoutManager1 = new GridLayoutManager(getActivity(), 1);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewComment.setLayoutManager(layoutManager1);
        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/comment/uid/?uid=" + user.getUid(), new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseString = response.body().string();
                commentMovies = new Gson().fromJson(responseString, new TypeToken<List<CommentMovie>>() {
                }.getType());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (commentMovies.isEmpty()) {
                            noComment.setVisibility(View.VISIBLE);
                        } else {
                            adapterComment = new CommentMeAdapter(getActivity(), commentMovies, user);
                            recyclerViewComment.setAdapter(adapterComment);
                            noComment.setVisibility(View.GONE);
                        }


                    }
                });


            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_FIRST_USER) {
                    user = (User) data.getSerializableExtra("user");
                    textMail.setText(user.getMail());
                    textNickname.setText(user.getNickname());
                }

        }
    }


}
