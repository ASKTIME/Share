package com.example.share.activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.share.Bean.User;
import com.example.share.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private EditText mNickname;
    private Button mRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mNickname = findViewById(R.id.nickname);
        mRegister = findViewById(R.id.register);


        //对注册进行监听
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(mUsername.getText().toString().trim());
                user.setPassword(mPassword.getText().toString().trim());
                user.setNickname(mNickname.getText().toString().trim());

                if (mUsername.getText().toString().equals("")) {
                    Toast.makeText(Register.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }else if (mPassword.getText().toString().equals("")){
                    Toast.makeText(Register.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else {

                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e==null) {
                                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}