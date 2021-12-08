package com.vaca.chatmygirl.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vaca.chatmygirl.R;


public class ChatVideoSendHolder extends RecyclerView.ViewHolder {
    public ImageView iv_icon;
    public TextView tv_time;
    public com.makeramen.roundedimageview.RoundedImageView img_content;


    public ChatVideoSendHolder(@NonNull View itemView) {
        super(itemView);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        tv_time = itemView.findViewById(R.id.tv_chat_time);
        img_content = itemView.findViewById(R.id.iv_iv);
    }
}
