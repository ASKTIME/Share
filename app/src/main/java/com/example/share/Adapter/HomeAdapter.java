package com.example.share.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.share.Bean.Post;
import com.example.share.R;
import com.example.share.activity.Accept;
import com.example.share.activity.Login;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private List<Post> mData;

    //正常状态等于0
    private final int Normal_Type = 0;
    private final int Foot_Type = 1;

    private int Max_Num = 15; //预加载的数据一共15条

    private boolean isFootView = true;  //是否有footView

    //创建构造方法
    public HomeAdapter(Context mContext, List<Post> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //获取到普通的item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ordinary_item,viewGroup,false);
        View footView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if (i == Foot_Type) {
            return new RecyclerViewHolder(footView, Foot_Type);
        }else {
            return new RecyclerViewHolder(view,Normal_Type);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isFootView && (getItemViewType(i)) == Foot_Type) {
            final  RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;//进行强制转换
            //设置底部加载的提示
            recyclerViewHolder.mLoading.setText("加载中...");
            //新建一个线程来通知改变
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Max_Num += 8;
                    notifyDataSetChanged();
                }
                //设置时间间隔为两秒
            },2000);
        }else {
            //这是ordinary_item的内容
            final  RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;//进行强制转换
            final Post post = mData.get(i);
            recyclerViewHolder.mUsername.setText(post.getUsername());
            recyclerViewHolder.mNickname.setText(post.getNickname());
            recyclerViewHolder.mContent.setText(post.getContent());
            recyclerViewHolder.mTime.setText(post.getCreatedAt());


            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击之后拿到此时的一个位置
                    int position = recyclerViewHolder.getAdapterPosition();

                    //如果用户登录
                    if (BmobUser.getCurrentUser(BmobUser.class) != null) {
                        //点击recyclerView会有一个接受的，会跳转 接收  accept
                        Intent intent = new Intent(mContext,Accept.class);
                        //并且要把参数带进去
                        intent.putExtra("username",post.getUsername());
                        intent.putExtra("content",post.getContent());
                        intent.putExtra("time",post.getCreatedAt());
                        intent.putExtra("id",mData.get(position).getObjectId());
                        mContext.startActivity(intent);

                    }else {
                        Toast.makeText(mContext, "请登录", Toast.LENGTH_SHORT).show();
                        //这时候进行登录的跳转
                        mContext.startActivity(new Intent(mContext,Login.class));
                    }
                }
            });
        }
    }


    //因为是本身的方法，所以要重写一下
    @Override
    public int getItemViewType(int position) {
        //进行一下判断,到底部返回底部提示。。。。
        if (position ==(Max_Num - 1)) {
            return Foot_Type;
        }else {
            return Normal_Type;
        }
    }

    @Override
    public int getItemCount() {
        if (mData.size() < Max_Num) {
            return mData.size();
        }
        return Max_Num;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {


        public  TextView mNickname;
        public  TextView mContent;
        public  TextView mTime;
        public  TextView mLoading;
        public TextView mUsername;

        public RecyclerViewHolder(View itemView, int view_type) {
            super(itemView);
            //进行控件的绑定
            if (view_type == Normal_Type) {
                mUsername = itemView.findViewById(R.id.username);
                mNickname = itemView.findViewById(R.id.nickname);
                mContent = itemView.findViewById(R.id.content);
                mTime = itemView.findViewById(R.id.time);
            }else if (view_type == Foot_Type){
                mLoading = itemView.findViewById(R.id.food_text);
            }
        }
    }
}
