package com.example.to_do.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do.R;
import com.example.to_do.models.Task;

import java.util.ArrayList;
import java.util.List;

public class AdapterToDoList extends RecyclerView.Adapter<AdapterToDoList.ViewHolderToDoList>{

    private List<Task> tasks = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public ViewHolderToDoList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item,parent,false);
        return new ViewHolderToDoList(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderToDoList holder, int position) {
        Task currentTask = tasks.get(position);
        holder.txtViewName.setText(currentTask.getName());
        holder.txtViewDescription.setText(currentTask.getDescription());
        holder.txtViewDate.setText("Límite: " + currentTask.getEndDate());

        if(currentTask.getStatus() == 1){
            holder.imgViewStatus.setImageResource(R.drawable.menu);
        } else if(currentTask.getStatus() == 2){
            holder.imgViewStatus.setImageResource(R.drawable.regreso);
        } else if(currentTask.getStatus() == 3){
            holder.imgViewStatus.setImageResource(R.drawable.cheque);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTaskAt(int position){
        return tasks.get(position);
    }

    public class ViewHolderToDoList extends RecyclerView.ViewHolder {

        private TextView txtViewName;
        private TextView txtViewDescription;
        private TextView txtViewDate;
        private ImageView imgViewStatus;
        public ViewHolderToDoList(@NonNull View itemView) {
            super(itemView);
            txtViewName = itemView.findViewById(R.id.txtViewName);
            txtViewDescription = itemView.findViewById(R.id.txtViewDescription);
            txtViewDate = itemView.findViewById(R.id.txtViewDate);
            imgViewStatus = itemView.findViewById(R.id.imgStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(tasks.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}