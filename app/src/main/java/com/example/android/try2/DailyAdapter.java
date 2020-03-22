package com.example.android.try2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyHolder> {

    @NonNull
    @Override
    public DailyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class DailyHolder extends RecyclerView.ViewHolder {
        private TextView textViewDaily;
        private TextView textViewTime;
        private TextView textViewDescription;

        public DailyHolder(@NonNull View itemView) {
            super(itemView);
            textViewDaily = itemView.findViewById(R.id.daily);
            textViewTime = itemView.findViewById(R.id.dailyTime);
            textViewDescription = itemView.findViewById(R.id.dailyExpand);
        }
    }
}
