package com.example.final_project_admin.db;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_admin.HomeActivity;
import com.example.final_project_admin.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList teacher_id, classType_id, classTime_id, duration_id;
    private Activity activity;
    public CustomAdapter(Activity activity, Context context, ArrayList teacher_id, ArrayList classType_id, ArrayList classTime_id, ArrayList duration_id) {
        this.activity = activity;
        this.context = context;
        this.teacher_id = teacher_id;
        this.classType_id = classType_id;
        this.classTime_id = classTime_id;
        this.duration_id = duration_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.teacher.setText(String.valueOf(teacher_id.get(position)));
        holder.classType.setText(String.valueOf(classType_id.get(position)));
        holder.classTime.setText(String.valueOf(classTime_id.get(position)));
        holder.duration.setText(String.valueOf(duration_id.get(position)));
    }



    @Override
    public int getItemCount() {
        return teacher_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView teacher, classType, classTime, duration;
        CardView classCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher = itemView.findViewById(R.id.teachertxt);
            classTime = itemView.findViewById(R.id.timetxt);
            classType = itemView.findViewById(R.id.classTypetxt);
            duration = itemView.findViewById(R.id.durationtxt);
            classCard = itemView.findViewById(R.id.class_card);
        }
    }

}
