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

import com.example.share.Bean.Community;
import com.example.share.R;
import com.example.share.activity.Accept;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int Normal_Type = 0;
    private final int Foot_Type = 1;

    private int Max_Num = 15; //预加载的数据一共15条
    private boolean isFootView = true;  //是否有footView

    private Context mContext;
    private List<Community> mCommunities;

    //构造方法
    public ChatAdapter( Context mContext,List<Community> mCommunities){
        this.mContext = mContext;
        //交流的数据
        this.mCommunities = mCommunities;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //获取到普通的item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.community_item,viewGroup,false);
        View footView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if (i == Foot_Type) {
            return new ChatAdapter.RecyclerViewHolder(footView, Foot_Type);
        }else {
            return new ChatAdapter.RecyclerViewHolder(view,Normal_Type);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isFootView && (getItemViewType(i)) == Foot_Type) {
            final ChatAdapter.RecyclerViewHolder recyclerViewHolder = (ChatAdapter.RecyclerViewHolder) viewHolder;//进行强制转换
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
            final ChatAdapter.RecyclerViewHolder recyclerViewHolder = (ChatAdapter.RecyclerViewHolder) viewHolder;//进行强制转换
            final Community community = mCommunities.get(i);

            recyclerViewHolder.mCommunity_name.setText(community.getName());
            recyclerViewHolder.mCommunity_info.setText(community.getInfo());
            recyclerViewHolder.mCommunity_user.setText(community.getOnwer());

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerViewHolder.getAdapterPosition();
                    //点击recyclerView会有一个接受的，会跳转 接收  accept
                    Intent intent = new Intent(mContext,Accept.class);
                    //并且要把参数带进去
                    intent.putExtra("community_name",community.getName());
                    intent.putExtra("community_user",community.getUser().getUsername());
                    intent.putExtra("community_info",community.getInfo());
                    intent.putExtra("id",mCommunities.get(position).getObjectId());
                    mContext.startActivity(intent);
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
        if (mCommunities.size() < Max_Num) {
            return mCommunities.size();
        }
        return Max_Num;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {


        public TextView mCommunity_user;
        public  TextView mCommunity_info;
        public  TextView mLoading;
        public TextView mCommunity_name;

        public RecyclerViewHolder(View itemView, int view_type) {
            super(itemView);
            //进行控件的绑定
            if (view_type == Normal_Type) {
                mCommunity_name = itemView.findViewById(R.id.community_name);
                mCommunity_user = itemView.findViewById(R.id.community_user);
                mCommunity_info = itemView.findViewById(R.id.community_info);
            }else if (view_type == Foot_Type){
                mLoading = itemView.findViewById(R.id.food_text);
            }
        }
    }
}
