package com.vaca.chatmygirl.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vaca.chatmygirl.R;


public class ChatFileSendHolder extends RecyclerView.ViewHolder {
    public ImageView iv_icon;
    public TextView tv_time;
    public TextView fileName;

    public ChatFileSendHolder(@NonNull View itemView) {
        super(itemView);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        tv_time = itemView.findViewById(R.id.tv_chat_time);
        fileName = itemView.findViewById(R.id.file_name);
    }
}
