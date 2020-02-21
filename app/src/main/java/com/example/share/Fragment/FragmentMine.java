package com.example.share.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.share.Bean.User;
import com.example.share.R;
import com.example.share.activity.Login;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class FragmentMine extends Fragment {

    private Button mLogOut;
    private TextView mMineNickname;
    private TextView mMineUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        //加载个人信息
        getMyInfo();

        initEvent();

    }

    private void initEvent() {

        //监听退出登录的动作
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清掉用户缓存，不在记录到app里面
               BmobUser.logOut();
               //把app finish掉之后要跳转到一个界面

               startActivity(new Intent(getActivity(),Login.class));
               getActivity().finish();
            }
        });
    }

    private void getMyInfo() {
        //加载个人信息
        BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        String objectId = bmobUser.getObjectId();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(objectId, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e ==null) {
                    mMineUsername.setText(user.getUsername());
                    mMineNickname.setText(user.getNickname());

                }else{
                    Toast.makeText(getActivity(), "加载失败...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        mMineUsername = getActivity().findViewById(R.id.mine_username);
        mMineNickname = getActivity().findViewById(R.id.mine_nickname);
        mLogOut = getActivity().findViewById(R.id.login_out);
    }
}
