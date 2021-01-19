package cn.edu.cqu.caijimovie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.customize_widget.SimpleRatingViewLarge;
import cn.edu.cqu.caijimovie.customize_widget.XLHRatingBar;
import cn.edu.cqu.caijimovie.entities.Movie;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RatingActivity extends AppCompatActivity {
    ImageButton imageButtonBack;
    EditText textComment;
    TextView shareTextButton;
    Button btnShare;
    Float floatRateNum = 1.0f;
    int intRateNum = 1;
    String shareText;
    private User user;
    private Movie movie;

    //初始化
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        user = (User) getIntent().getSerializableExtra("currentuser");
        movie = (Movie) getIntent().getSerializableExtra("onClickMovie");


        //Share按钮1
        shareTextButton = findViewById(R.id.rating_textView_share);
        shareTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textComment = findViewById(R.id.rating_multiAutoCompleteTextView);
                shareText = textComment.getText().toString().trim();
                //Todo:协同UID、MID上传到服务器,如果成功则finish(),失败则返回错误信息.
                if (shareText.equals("") || shareText == null) {
                    Toast.makeText(RatingActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    RequestBody formBody = new FormBody.Builder()
                            .add("content", shareText)
                            .add("uid", user.getUid() + "")
                            .add("mid", movie.getMovieid() + "")
                            .add("rate", intRateNum + "")
                            .build();

                    HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/comment/insert/", formBody, new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RatingActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(RatingActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                                        setResult(RESULT_OK);
                                        finish();
                                    } else {
                                        Toast.makeText(RatingActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                        }
                    });
                }


            }
        });


        //评分控件及其功能的初始化
        final XLHRatingBar xlhRatingBar = findViewById(R.id.rating_BarRated);
        xlhRatingBar.setNumStars(5);
        xlhRatingBar.setRating(1);
        xlhRatingBar.setRatingView(new SimpleRatingViewLarge());
        xlhRatingBar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(float rating, int numStars) {
                floatRateNum = xlhRatingBar.getRating();
                if (floatRateNum < 2) {
                    xlhRatingBar.setRating(1);
                    intRateNum = 1;
                } else {
                    intRateNum = (int) Math.floor(floatRateNum);
                    intRateNum *= 2;
                }
            }
        });


        //ToolBar返回按钮
        imageButtonBack = findViewById(R.id.rating_info_back);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
