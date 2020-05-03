package com.example.android.try2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyHolder> {
    private List<DailyData> dailies = new ArrayList<>();
    private onItemClickListener listener;


    @NonNull
    @Override
    public DailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dailyView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily, parent, false);
        return new DailyHolder(dailyView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DailyHolder holder, final int position) {
        final DailyData currentDaily = dailies.get(position);
        holder.textViewDaily.setText(currentDaily.getTitle());
        holder.textViewTime.setText(currentDaily.getTime());
        holder.textViewDescription.setText(currentDaily.getDescription());
        holder.checkBox.setTag(dailies.get(position));
        if (currentDaily.getState() == 1) {
            holder.checkBox.setChecked(false);
        } else {
            holder.checkBox.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return dailies.size();
    }

    public void setDailies(List<DailyData> dailies) {
        this.dailies = dailies;
        notifyDataSetChanged();
    }


    class DailyHolder extends RecyclerView.ViewHolder {
        private TextView textViewDaily;
        private TextView textViewTime;
        private TextView textViewDescription;
        private CheckBox checkBox;


        public DailyHolder(@NonNull View itemView) {
            super(itemView);
            textViewDaily = itemView.findViewById(R.id.daily);
            textViewTime = itemView.findViewById(R.id.dailyTime);
            textViewDescription = itemView.findViewById(R.id.dailyExpand);
            checkBox = itemView.findViewById(R.id.dailyCheckBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(dailies.get(position));
                    }
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.checkboxViewOnClick(dailies.get(position));
                }
            });

        }
    }

    public interface onItemClickListener {
        void onItemClick(DailyData dailyData);
        void checkboxViewOnClick(DailyData dailyData);
    }


    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
