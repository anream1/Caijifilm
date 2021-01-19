package cn.edu.cqu.caijimovie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.activities.CategoryActivity;
import cn.edu.cqu.caijimovie.activities.ComingAllActivity;
import cn.edu.cqu.caijimovie.activities.HotAllActivity;
import cn.edu.cqu.caijimovie.activities.MainActivity;
import cn.edu.cqu.caijimovie.activities.MovieInfoActivity;
import cn.edu.cqu.caijimovie.activities.SearchActivity;
import cn.edu.cqu.caijimovie.adapter.MovieCardAdapter;
import cn.edu.cqu.caijimovie.adapter.MovieComingAdapter;
import cn.edu.cqu.caijimovie.adapter.MovieHotAdapter;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.MovieComing;
import cn.edu.cqu.caijimovie.entities.MovieHot;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.GlideImageLoader;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends MyFragment {
    private SearchView mSearchView;
//    private View view1, view2, view3,view4,view5;
//    private ViewPager viewPager;  //对应的viewPager
//    private List<View> viewList;

    private RecyclerView mRecyclerViewHot, mRecyclerViewComing;
    private List<Movie> movieHotList = new ArrayList<>();
    private List<MovieComing> movieComingList = new ArrayList<>();
    private MovieHotAdapter adapterHot;
    private MovieComingAdapter adapterComing;
    private GridLayoutManager layoutManager, layoutManagerComing;

    private TextView hotall, comingall;

    private Banner banner;
    private List<Integer> images;

    private TextView comedy, action, love, science, suspense, crime, fantasy, music, gay, risk, war;

    private User user;


    public MainHomeFragment() {
        super("Home");
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);


        comedy = view.findViewById(R.id.textComedy);
        action = view.findViewById(R.id.textAction);
        love = view.findViewById(R.id.textLove);
        suspense = view.findViewById(R.id.textSuspense);
        science = view.findViewById(R.id.textScience);
        crime = view.findViewById(R.id.textCrime);
        fantasy = view.findViewById(R.id.textFantasy);
        music = view.findViewById(R.id.textMusic);
        gay = view.findViewById(R.id.textGay);
        risk = view.findViewById(R.id.textRisk);
        war = view.findViewById(R.id.textWar);

        banner = (Banner) view.findViewById(R.id.banner);

        images = new ArrayList<>();
        images.add(R.drawable.b1);
        images.add(R.drawable.b2);
        images.add(R.drawable.b3);
        images.add(R.drawable.b4);

        //加载banner
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置是否允许手动滑动轮播图
        banner.setViewPagerIsScroll(true);
        //设置是否自动轮播（不设置则默认自动）
        banner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(4000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImages(images);
        banner.start();


        hotall = (TextView) view.findViewById(R.id.hotall);
        comingall = (TextView) view.findViewById(R.id.comingall);
        mRecyclerViewHot = (RecyclerView) view.findViewById(R.id.recycler_hot);
        layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mRecyclerViewHot.setLayoutManager(layoutManager);

        mRecyclerViewComing = (RecyclerView) view.findViewById(R.id.recycler_coming);
        layoutManagerComing = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mRecyclerViewComing.setLayoutManager(layoutManagerComing);


        initListener();


        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/movie/moviehot6", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseString = response.body().string();
                movieHotList = new Gson().fromJson(responseString, new TypeToken<List<Movie>>() {
                }.getType());


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterHot = new MovieHotAdapter(getActivity(), movieHotList, user);
                        mRecyclerViewHot.setAdapter(adapterHot);
                        mRecyclerViewHot.setItemAnimator(new DefaultItemAnimator());

                    }
                });
            }
        });

        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/movie/moviecoming6", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseString = response.body().string();
                movieComingList = new Gson().fromJson(responseString, new TypeToken<List<MovieComing>>() {
                }.getType());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterComing = new MovieComingAdapter(getActivity(), movieComingList);
                        mRecyclerViewComing.setAdapter(adapterComing);
                        mRecyclerViewComing.setItemAnimator(new DefaultItemAnimator());
                    }
                });
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = (SearchView) getActivity().findViewById(R.id.search_movie);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(true);


        //搜索框搜索
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                intent.putExtra("searchText", s);
                intent.putExtra("currentuser", user);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (isAdded()) {//判断Fragment已经依附Activity
            user = (User) getArguments().getSerializable("currentuser");
        }
    }

    private void initListener() {
        hotall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                //Toast.makeText(getActivity(),"全部热映",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), HotAllActivity.class);
                intent.putExtra("currentuser", user);
                startActivity(intent);

            }
        });

        comingall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                //Toast.makeText(getActivity(),"全部即将上映",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), ComingAllActivity.class);
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });

        comedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", comedy.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", action.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", love.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", science.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        suspense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", suspense.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        crime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", crime.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        fantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", fantasy.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", music.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        gay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", gay.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        risk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", risk.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });
        war.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                Intent intent = new Intent(getActivity().getApplicationContext(), CategoryActivity.class);
                intent.putExtra("movieCategory", war.getText());
                intent.putExtra("currentuser", user);
                startActivity(intent);
            }
        });

    }

}
