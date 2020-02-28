package com.example.share.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.share.Bean.Community;
import com.example.share.Bean.Post;
import com.example.share.R;
import com.example.share.activity.Accept;
import com.example.share.activity.Login;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class MyPushCollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> mPosts;
    private Context mContext;

    //正常状态等于0
    private final int Normal_Type = 0;
    private final int Foot_Type = 1;

    private int Max_Num = 15; //预加载的数据一共15条

    private boolean isFootView = true;  //是否有footView

    public MyPushCollectionAdapter(List<Post> mPosts, Context mContext){
        this.mPosts = mPosts;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //获取到普通的item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_push_collection,viewGroup,false);
        View footView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if (i == Foot_Type) {
            return new MyPushCollectionAdapter.RecyclerViewHolder(footView, Foot_Type);
        }else {
            return new MyPushCollectionAdapter.RecyclerViewHolder(view,Normal_Type);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isFootView && (getItemViewType(i)) == Foot_Type) {
            final MyPushCollectionAdapter.RecyclerViewHolder recyclerViewHolder = (MyPushCollectionAdapter.RecyclerViewHolder) viewHolder;//进行强制转换
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
            final MyPushCollectionAdapter.RecyclerViewHolder recyclerViewHolder = (MyPushCollectionAdapter.RecyclerViewHolder) viewHolder;//进行强制转换
            final Post post = mPosts.get(i);
            recyclerViewHolder.mMyPushCollectionUsername.setText(post.getUsername());
            recyclerViewHolder.mMyPushCollectionNickname.setText(post.getNickname());
            recyclerViewHolder.mPushCollectionContent.setText(post.getContent());
            recyclerViewHolder.mMyPushCollectionTime.setText(post.getCreatedAt());


            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击之后拿到此时的一个位置
                    int position = recyclerViewHolder.getAdapterPosition();

                    //如果用户登录
                    if (BmobUser.getCurrentUser(BmobUser.class) != null) {
                        //点击recyclerView会有一个接受的，会跳转 接收  accept

                        //TODO:需要改动
                        Intent intent = new Intent(mContext,Accept.class);

                        //并且要把参数带进去
                        intent.putExtra("username",post.getUsername());
                        intent.putExtra("content",post.getContent());
                        intent.putExtra("time",post.getCreatedAt());
                        intent.putExtra("id",mPosts.get(position).getObjectId());
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

    @Override
    public int getItemViewType(int position) {
        if (position == Max_Num - 1){
            return Foot_Type;  //底部type
        }else {
            return Normal_Type;
        }
    }
    @Override
    public int getItemCount() {
        if (mPosts.size() < Max_Num) {
            return mPosts.size();
        }
        return Max_Num;
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {


        public TextView mMyPushCollectionUsername;
        public  TextView mMyPushCollectionNickname;
        public  TextView mLoading;
        public TextView mMyPushCollectionTime;
        public TextView mPushCollectionContent;

        public RecyclerViewHolder(View itemView, int view_type) {
            super(itemView);
            //进行控件的绑定
            if (view_type == Normal_Type) {
                mMyPushCollectionUsername = itemView.findViewById(R.id.my_push_collection_username);
                mMyPushCollectionNickname = itemView.findViewById(R.id.my_push_collection_nickname);
                mMyPushCollectionTime = itemView.findViewById(R.id.my_push_collection_time);
                mPushCollectionContent = itemView.findViewById(R.id.my_push_collection_content);
            }else if (view_type == Foot_Type){
                mLoading = itemView.findViewById(R.id.food_text);
            }
        }
    }
}
