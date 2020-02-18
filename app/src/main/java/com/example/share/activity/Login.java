package com.example.share.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.share.Bean.User;
import com.example.share.MainActivity;
import com.example.share.R;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
public class Login extends AppCompatActivity {

    private EditText mUsername,mPassword;
    private Button mLogin,mRegister;

    private ImageView lookps;

    private AlertDialog alertDialog;

    private Boolean isPswVisible = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login);
        mRegister= findViewById(R.id.register);
       // lookps = findViewById(R.id.lookps);

      /*  lookps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setPswVisible();
            }
        });*/

        //登录监听
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setUsername(mUsername.getText().toString().trim());
                user.setPassword(mPassword.getText().toString().trim());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User o, BmobException e) {
                        if (e == null){
                            Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(Login.this, "登陆失败"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        //注册监听
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });




    }
/*
    private void setPswVisible() {
        isPswVisible = !isPswVisible;
        if (isPswVisible){
            lookps.setImageResource(R.drawable.eyeopen);
            HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
            password.setTransformationMethod(method);
        }else {
            lookps.setImageResource(R.drawable.eyeclose);
            PasswordTransformationMethod method = PasswordTransformationMethod.getInstance();
            password.setTransformationMethod(method);
        }
    }*/


}
