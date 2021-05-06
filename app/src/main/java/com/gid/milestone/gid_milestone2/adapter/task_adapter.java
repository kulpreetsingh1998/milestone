package com.gid.milestone.gid_milestone2.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gid.milestone.gid_milestone2.R;

public class task_adapter extends RecyclerView.ViewHolder {

        public LinearLayout task_layoutt;
        public TextView timet;
        public TextView subjectt;
        public TextView contentt;
        public task_adapter(@NonNull View itemView) {
            super(itemView);
            task_layoutt = itemView.findViewById(R.id.task_layout);
            timet = itemView.findViewById(R.id.time);
            subjectt = itemView.findViewById(R.id.subject);
            contentt = itemView.findViewById(R.id.content);

        }
    public void setTxtTime(String string) {
        timet.setText(string);
    }
    public void setTxtTitle(String string) { subjectt.setText(string); }
    public void setTxtContent(String string) {
            contentt.setText(string);
        }

    }

