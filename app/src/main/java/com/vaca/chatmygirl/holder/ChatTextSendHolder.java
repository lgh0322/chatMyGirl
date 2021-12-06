package com.vaca.chatmygirl.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vaca.chatmygirl.R;


public class ChatTextSendHolder extends RecyclerView.ViewHolder {
    public ImageView iv_icon;
    public TextView tv_time;
    public TextView tv_content;


    public ChatTextSendHolder(@NonNull View itemView) {
        super(itemView);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        tv_time = itemView.findViewById(R.id.tv_chat_time);
        tv_content = itemView.findViewById(R.id.tv_content);
    }
}
