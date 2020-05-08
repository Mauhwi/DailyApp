package com.example.android.try2.ui.med;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.try2.DB.MedDB.MedData;
import com.example.android.try2.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.MedHolder>{
    private List<MedData> meds = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public MedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View medView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_med, parent, false);
        return new MedHolder(medView);
    }

    public void onBindViewHolder (@NonNull MedHolder holder, final int position) {
        final MedData currentMed = meds.get(position);
        holder.textViewMedName.setText(currentMed.getTitle());
        holder.textViewMedTime.setText(currentMed.getTime());
        //Поиск Id изображения из String
        String imadsh = "pill2";
        int picId = holder.itemView.getContext().getResources()
                .getIdentifier(imadsh, "drawable", holder.itemView.getContext()
                        .getApplicationContext().getPackageName());
        holder.imageMed.setImageResource(picId);
        Log.i(TAG, "Айди: " + picId);
        if (currentMed.getState() == 1) {
            holder.checkboxMed.setChecked(false);
        } else {
            holder.checkboxMed.setChecked(true);
        }

    }

    @Override
    public int getItemCount() {
        return meds.size();
    }

    public void setMeds(List<MedData> meds) {
        this.meds = meds;
        notifyDataSetChanged();
    }

    class MedHolder extends RecyclerView.ViewHolder {
        private TextView textViewMedName;
        private TextView textViewMedTime;
        private CheckBox checkboxMed;
        private ImageView imageMed;


        public MedHolder(@NonNull View itemView) {
            super(itemView);
            textViewMedName = itemView.findViewById(R.id.med_name);
            textViewMedTime = itemView.findViewById(R.id.med_time);
            checkboxMed = itemView.findViewById(R.id.med_checkbox);
            imageMed = itemView.findViewById(R.id.med_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(meds.get(position));
                    }
                }
            });

            checkboxMed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.checkboxViewOnClick(meds.get(position));
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(MedData medData);
        void checkboxViewOnClick(MedData medData);
    }


    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
