package cn.edu.cqu.caijimovie.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import cn.edu.cqu.caijimovie.R;
import cn.edu.cqu.caijimovie.adapter.CommentAdapter;
import cn.edu.cqu.caijimovie.entities.CommentLike;
import cn.edu.cqu.caijimovie.entities.User;
import cn.edu.cqu.caijimovie.utils.CheckInfoUtils;
import cn.edu.cqu.caijimovie.utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonalSettingActivity extends AppCompatActivity {

    private TextView userNameText;
    private TextView phoneText;
    private User user;
    private String input, inputName;
    private Button buttonNearBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        user = (User) getIntent().getSerializableExtra("currentuser");
        userNameText = findViewById(R.id.set_username);
        phoneText = findViewById(R.id.set_phone);
        userNameText.setText(user.getNickname());
        phoneText.setText(user.getPhone());
        buttonNearBy.findViewById(R.id.nearBy);
        buttonNearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalSettingActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }


    //修改手机号
    public void onClickPhone(View view) {

        final EditText phoneNumber = new EditText(this);

        new AlertDialog.Builder(this).setTitle("设置新的手机号")
                .setIcon(android.R.drawable.stat_sys_phone_call)
                .setView(phoneNumber)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        input = phoneNumber.getText().toString();
                        if (!CheckInfoUtils.isMobile(input)) {
                            Toast.makeText(getApplicationContext(), "手机号输入错误！" + input, Toast.LENGTH_LONG).show();
                        } else {
                            //Todo:提交到服务器 成功就更新本地值,失败则不更新
                            Log.i("新手机号", "onClickPhone: " + input);
                            user.setPhone(input);
                            RequestBody formBody = new FormBody.Builder()
                                    .add("mail", user.getMail())
                                    .add("phone", user.getPhone())
                                    .build();

                            HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/user/updatephone/", formBody, new Callback() {

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(PersonalSettingActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    final String responseString = response.body().string();


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(PersonalSettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            phoneText.setText(input);
                                            Intent intent = new Intent();
                                            intent.putExtra("user", user);
                                            setResult(RESULT_FIRST_USER, intent);

                                        }
                                    });


                                }
                            });

                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

    //修改用户名
    public void onClickUserName(View view) {
        final EditText userName = new EditText(this);
        new AlertDialog.Builder(this).setTitle("设置新的用户名")
                .setIcon(R.drawable.ic_account_circle_grey_80dp)
                .setView(userName)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        inputName = userName.getText().toString();
                        if (inputName == "") {
                            Toast.makeText(getApplicationContext(), "用户名不能为空！" + inputName, Toast.LENGTH_LONG).show();
                        } else {
                            //Todo:提交到服务器 成功就更新本地值,失败则不更新
                            Log.i("新昵称", "onClickPhone: " + inputName);
                            user.setNickname(inputName);
                            RequestBody formBody = new FormBody.Builder()
                                    .add("mail", user.getMail())
                                    .add("nickname", user.getNickname())
                                    .build();

                            HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/user/updatenickname/", formBody, new Callback() {

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(PersonalSettingActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    final String responseString = response.body().string();


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(PersonalSettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            userNameText.setText(inputName);
                                            Intent intent = new Intent();
                                            intent.putExtra("user", user);
                                            setResult(RESULT_FIRST_USER, intent);

                                        }
                                    });


                                }
                            });

                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    //登出
    public void onClickLogOut(View view) {
        Intent intent = new Intent(PersonalSettingActivity.this, SignUpInActivity.class);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();

    }

    //用户反馈
    public void onClickFeedBack(View view) {
        final EditText userName = new EditText(this);
        new AlertDialog.Builder(this).setTitle("输入您的反馈信息")
                .setIcon(R.drawable.ic_feedback_black_24dp)
                .setView(userName)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = userName.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "反馈信息不能为空！" + input, Toast.LENGTH_LONG).show();
                        } else {
                            //Todo:提交到服务器 成功就告诉他成功,失败就告诉他失败
                            RequestBody formBody = new FormBody.Builder()
                                    .add("uid", user.getUid() + "")
                                    .add("feedback", input)
                                    .build();

                            HttpUtils.sendOkHttpRequest("http://192.168.137.1:8090/api/feedback/insert/", formBody, new Callback() {

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "提交失败！", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    final String responseString = response.body().string();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "反馈提交成功！", Toast.LENGTH_LONG).show();

                                        }
                                    });


                                }
                            });

                            Log.i("反馈信息", "onClickPhone: " + input);

                            //userNameText.setText(input);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    //返回
    public void onClickBack(View view) {
        finish();
    }
}
