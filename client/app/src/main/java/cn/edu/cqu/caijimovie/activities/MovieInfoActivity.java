package cn.edu.cqu.caijimovie.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.adapter.CommentAdapter;
import cn.edu.cqu.caijimovie.adapter.MovieCardAdapter;
import cn.edu.cqu.caijimovie.customize_widget.SimpleRatingView;
import cn.edu.cqu.caijimovie.customize_widget.XLHRatingBar;
import cn.edu.cqu.caijimovie.entities.CommentLike;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class MovieInfoActivity extends AppCompatActivity {
    private Movie mMovie;
    private AppBarLayout mAppBarLayout;
    private NestedScrollView mNestedScrollView;
    private ImageButton back;
    private TextView infoTag, rateDec, rateInt, title, akaYear, description, noComment;
    private XLHRatingBar ratingBar;
    private ImageView poster;
    private User user;
    private Drawable drawablepink, drawablegrey;
    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<CommentLike> commentLikes = new ArrayList<CommentLike>();
    private GridLayoutManager layoutManager;
    private FloatingActionButton fabLike, fabComment;
    private int isLike = 1;

    //private List<Integer> mPicResList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        user = (User) getIntent().getSerializableExtra("currentuser");
        mAppBarLayout = findViewById(R.id.app_bar);
        mNestedScrollView = findViewById(R.id.include);
        mMovie = (Movie) getIntent().getSerializableExtra("onClickMovie");
        setTitle(null);
        fabComment = (FloatingActionButton) findViewById(R.id.fab_comment);
        fabLike = (FloatingActionButton) findViewById(R.id.fab_like);

        noComment = (TextView) findViewById(R.id.noComment);
        recyclerView = (RecyclerView) findViewById(R.id.cRecyclerView);

        layoutManager = new GridLayoutManager(MovieInfoActivity.this, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        drawablepink = getResources().getDrawable(R.drawable.ic_favorite_pink_24dp);
        drawablegrey = getResources().getDrawable(R.drawable.ic_favorite_border_grey_24dp);

        recyclerView.setLayoutManager(layoutManager);
        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/follow/check?uid=" + user.getUid() + "&mid=" + mMovie.getMovieid(), new Callback() {

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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseString.equals("1")) {
                            isLike = 0;
                            fabLike.setImageResource(R.drawable.ic_favorite_pink_24dp);
                        }


                    }
                });


            }
        });


        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/comment/mid?mid=" + mMovie.getMovieid() + "&luid=" + user.getUid(), new Callback() {

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

                commentLikes = new Gson().fromJson(responseString, new TypeToken<List<CommentLike>>() {
                }.getType());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (commentLikes.isEmpty()) {
                            noComment.setVisibility(View.VISIBLE);
                        } else {
                            noComment.setVisibility(View.GONE);
                            adapter = new CommentAdapter(MovieInfoActivity.this, commentLikes, user, mMovie);
                            recyclerView.setAdapter(adapter);
                        }

                    }
                });


            }
        });


        initialize();

        //Todo:ToolBar的返回顶部
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoordinatorLayout.Behavior behavior =
                        ((CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams()).getBehavior();
                if (behavior instanceof AppBarLayout.Behavior) {
                    AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
                    int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
                    if (topAndBottomOffset != 0) {
                        appBarLayoutBehavior.setTopAndBottomOffset(0);
                        mNestedScrollView.fling(0);
                        mNestedScrollView.scrollTo(0, 0);
                    }
                }
                mAppBarLayout.setExpanded(true);

            }
        });
        setSupportActionBar(toolbar);

        //ToolBar返回按钮
        ImageButton imageButtonBack = findViewById(R.id.movie_info_back);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Todo:通过UID和MID来设置Like图标样式,并通过http来上传
        fabLike = findViewById(R.id.fab_like);
        fabLike.setBackgroundColor(325154);

        fabLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                RequestBody formBody = new FormBody.Builder()
                        .add("uid", user.getUid() + "")
                        .add("mid", mMovie.getMovieid() + "")
                        .add("operand", isLike + "")
                        .build();

                HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/follow/updatenum/", formBody, new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MovieInfoActivity.this, "关注失败", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String responseString = response.body().string();


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isLike == 1) {
                                    isLike = 0;
                                    fabLike.setImageDrawable(drawablepink);
                                    //fabLike.setImageResource(R.drawable.ic_favorite_pink_24dp);
                                    Toast.makeText(MovieInfoActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                                } else if (isLike == 0) {
                                    isLike = 1;
                                    fabLike.setImageDrawable(drawablegrey);
                                    Toast.makeText(MovieInfoActivity.this, "取消关注成功", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });


                    }
                });
            }
        });

        //Todo:进入评分页面,如果评过....就先算了
        FloatingActionButton fabComment = findViewById(R.id.fab_comment);
        fabComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieInfoActivity.this, RatingActivity.class);
                intent.putExtra("currentuser", user);
                intent.putExtra("onClickMovie", mMovie);
                startActivityForResult(intent, 1);

            }
        });


    }

    //Todo:界面电影相关信息的初始化
    private void initialize() {


        back = (ImageButton) findViewById(R.id.movie_info_back);
        infoTag = (TextView) findViewById(R.id.info_tags);
        rateDec = (TextView) findViewById(R.id.info_ratingTextRatedDec);
        rateInt = (TextView) findViewById(R.id.info_ratingTextRatedInt);
        title = (TextView) findViewById(R.id.info_movie_title);
        akaYear = (TextView) findViewById(R.id.info_movie_title_aka_year);
        poster = (ImageView) findViewById(R.id.card_movie_poster);
        ratingBar = (XLHRatingBar) findViewById(R.id.info_ratingBarRated);
        description = (TextView) findViewById(R.id.include).findViewById(R.id.info_movie_description);
        View toolBar = findViewById(R.id.toolbar_layout);
        View include = findViewById(R.id.include);
        View linear = findViewById(R.id.linear);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                finish();
            }
        });

        Glide.with(this)
                .load(mMovie.getCover()).listener(GlidePalette.with(mMovie.getCover()).use(GlidePalette.Profile.MUTED_DARK).intoBackground(toolBar).intoBackground(include))
                .override(270, 400)
                .error(R.drawable.ic_error_outline_black_24dp)
                .into(poster);

        String rate = String.valueOf(mMovie.getRate());
        String[] s1 = rate.split("\\.");
        String[] district = mMovie.getDistrict().split("/");
        String moviedis = "";
        for (String i : district) {
            moviedis += i.split("_")[1] + " ";
        }
        akaYear.setText(mMovie.getOthername().split("/")[0] + "(" + mMovie.getShowtime() + ")");
        infoTag.setText(moviedis + "/" + mMovie.getCategory().replace("/", " ") + "/片长：" + mMovie.getLength() + "分钟/" + mMovie.getDirector().replace("/", " ") + "/" + mMovie.getActor().replace("/", " "));
        rateInt.setText(s1[0] + ".");
        rateDec.setText(s1[1]);
        title.setText(mMovie.getTitle().split(" ")[0]);
        ratingBar.setNumStars(5);
        ratingBar.setRating((float) mMovie.getRate() / 2);
        ratingBar.setEnabled(false);
        ratingBar.setRatingView(new SimpleRatingView());
        String[] descrip = mMovie.getDescription().split("/");
        String descripTxt = "\u3000\u3000";
        for (String i : descrip) {
            descripTxt += i + "\n";
        }
        description.setText(descripTxt);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    refreshComment();
                }
        }

    }

    //刷新评论
    private void refreshComment() {
        HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/comment/mid?mid=" + mMovie.getMovieid() + "&luid=" + user.getUid(), new Callback() {

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

                commentLikes = new Gson().fromJson(responseString, new TypeToken<List<CommentLike>>() {
                }.getType());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (commentLikes.isEmpty()) {
                            noComment.setVisibility(View.VISIBLE);
                        } else {
                            noComment.setVisibility(View.GONE);
                            adapter = new CommentAdapter(MovieInfoActivity.this, commentLikes, user, mMovie);
                            recyclerView.setAdapter(adapter);
                        }

                    }
                });


            }
        });

    }
}
