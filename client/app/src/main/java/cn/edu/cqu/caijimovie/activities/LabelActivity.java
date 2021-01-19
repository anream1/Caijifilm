package cn.edu.cqu.caijimovie.activities;

import android.content.Intent;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.entities.Result;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

public class LabelActivity extends AppCompatActivity {

    //电影标签
    private static String[] movieTags = new String[]{"喜剧", "动作", "剧情", "爱情", "科幻", "悬疑", "犯罪", "同性", "音乐", "战争", "传记", "奇幻", "冒险", "灾难", "武侠"};
    private static String[] movieCountry = new String[]{"中国大陆", "美国", "香港", "印度", "日本", "韩国", "英国", "法国", "德国"};

    private boolean[] tagFlag;
    private boolean[] countryFlag;

    private Button btnConfirm;

    private User user;
    private String labelSelected;
    private Result result;
    private int logstatus = -1;

    private TextView textComedy, textAction, textFeature, textLove, textScience, textSuspense, textCrime, textGay, textMusic, textWar, textBiography, textFantasy, textRisk, textDisaster, textSword;
    private TextView textChina, textAmerica, textHK, textIndia, textJapan, textKorea, textBritish, textFrench, textGerman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        user = (User) getIntent().getSerializableExtra("currentuser");

        tagFlag = new boolean[15];
        Arrays.fill(tagFlag, false);
        tagFlag[0] = true;
        countryFlag = new boolean[9];
        Arrays.fill(countryFlag, false);
        countryFlag[0] = true;
        labelSelected = "";
        initView();
        textComedy.setBackgroundResource(R.drawable.text_round_border_selected);
        textChina.setBackgroundResource(R.drawable.text_round_border_selected);
        initListener();

    }

    private void initView() {
        btnConfirm = (Button) findViewById(R.id.confirm);

        textComedy = findViewById(R.id.textComedy);
        textAction = findViewById(R.id.textAction);
        textFeature = findViewById(R.id.textFeature);
        textLove = findViewById(R.id.textLove);
        textScience = findViewById(R.id.textScience);
        textSuspense = findViewById(R.id.textSuspense);
        textCrime = findViewById(R.id.textCrime);
        textGay = findViewById(R.id.textGay);
        textMusic = findViewById(R.id.textMusic);
        textWar = findViewById(R.id.textWar);
        textBiography = findViewById(R.id.textBiography);
        textFantasy = findViewById(R.id.textFantasy);
        textRisk = findViewById(R.id.textRisk);
        textDisaster = findViewById(R.id.textDisaster);
        textSword = findViewById(R.id.textSword);

        textChina = findViewById(R.id.textChina);
        textAmerica = findViewById(R.id.textAmerica);
        textHK = findViewById(R.id.textHk);
        textIndia = findViewById(R.id.textIndia);
        textJapan = findViewById(R.id.textJapan);
        textKorea = findViewById(R.id.textKorean);
        textBritish = findViewById(R.id.textBritish);
        textFrench = findViewById(R.id.textFrench);
        textGerman = findViewById(R.id.textGermany);

    }


    private void initListener() {
        //确认按钮
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 15; i++) {
                    if (tagFlag[i] == true) {
                        labelSelected = labelSelected + movieTags[i] + "/";
                    }
                }
                for (int i = 0; i < 9; i++) {
                    if (countryFlag[i] == true) {
                        labelSelected = labelSelected + movieCountry[i] + "/";
                    }
                }
                //labelSelected = labelSelected.substring(0,labelSelected.length()-1);
                Log.i("labelSelect", "onClick: " + labelSelected);
                user.setUserLabel(labelSelected);

                RequestBody formBody = new FormBody.Builder()
                        .add("uid", user.getUid() + "")
                        .add("userlabel", labelSelected)
                        .build();


                HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/user/updatelabel", formBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        final String responseString = response.body().string();

                        Result result = new Gson().fromJson(responseString, Result.class);

                        logstatus = result.getReturnValue();
                        Log.i("json", "json对象: " + logstatus);
                        switch (logstatus) {
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("currentuser", user);
                                startActivity(intent);
                                finish();
                                break;
                            case 1:
                                Toast.makeText(LabelActivity.this, "提交失败！", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(LabelActivity.this, "网络错误!", Toast.LENGTH_SHORT).show();
                                break;
                        }


                    }
                });

            }
        });

        textComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[0] == false) {
                    tagFlag[0] = true;
                    textComedy.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[0] = false;
                    textComedy.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[1] == false) {
                    tagFlag[1] = true;
                    textAction.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[1] = false;
                    textAction.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[2] == false) {
                    tagFlag[2] = true;
                    textFeature.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[2] = false;
                    textFeature.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[3] == false) {
                    tagFlag[3] = true;
                    textLove.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[3] = false;
                    textLove.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[4] == false) {
                    tagFlag[4] = true;
                    textScience.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[4] = false;
                    textScience.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textSuspense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[5] == false) {
                    tagFlag[5] = true;
                    textSuspense.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[5] = false;
                    textSuspense.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[6] == false) {
                    tagFlag[6] = true;
                    textCrime.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[6] = false;
                    textCrime.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textGay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[7] == false) {
                    tagFlag[7] = true;
                    textGay.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[7] = false;
                    textGay.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[8] == false) {
                    tagFlag[8] = true;
                    textMusic.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[8] = false;
                    textMusic.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[9] == false) {
                    tagFlag[9] = true;
                    textWar.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[9] = false;
                    textWar.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textBiography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[10] == false) {
                    tagFlag[10] = true;
                    textBiography.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[10] = false;
                    textBiography.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textFantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[11] == false) {
                    tagFlag[11] = true;
                    textFantasy.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[11] = false;
                    textFantasy.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });

        textRisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[12] == false) {
                    tagFlag[12] = true;
                    textRisk.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[12] = false;
                    textRisk.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textDisaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[13] == false) {
                    tagFlag[13] = true;
                    textDisaster.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[13] = false;
                    textDisaster.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textSword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (tagFlag[14] == false) {
                    tagFlag[14] = true;
                    textSword.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    tagFlag[14] = false;
                    textSword.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });


        textChina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[0] == false) {
                    countryFlag[0] = true;
                    textChina.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[0] = false;
                    textChina.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textAmerica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[1] == false) {
                    countryFlag[1] = true;
                    textAmerica.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[1] = false;
                    textAmerica.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textHK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[2] == false) {
                    countryFlag[2] = true;
                    textHK.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[2] = false;
                    textHK.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textIndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[3] == false) {
                    countryFlag[3] = true;
                    textIndia.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[3] = false;
                    textIndia.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textJapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[4] == false) {
                    countryFlag[4] = true;
                    textJapan.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[4] = false;
                    textJapan.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textKorea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[5] == false) {
                    countryFlag[5] = true;
                    textKorea.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[5] = false;
                    textKorea.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textBritish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[6] == false) {
                    countryFlag[6] = true;
                    textBritish.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[6] = false;
                    textBritish.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textFrench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[7] == false) {
                    countryFlag[7] = true;
                    textFrench.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[7] = false;
                    textFrench.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
        textGerman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open Function
                if (countryFlag[8] == false) {
                    countryFlag[8] = true;
                    textGerman.setBackgroundResource(R.drawable.text_round_border_selected);
                } else {
                    countryFlag[8] = false;
                    textGerman.setBackgroundResource(R.drawable.text_round_border);
                    //textComedy.setSelected(true);
                }
            }
        });
    }


}
