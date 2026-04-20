package com.example.apnatiffin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Messadapter extends RecyclerView.Adapter<Messadapter.Myholder> {
    private final Context context;
    private final List<Model> list;
    private final OnItemClickListener listener;

    // Define interface for item click
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Constructor
    public Messadapter(Context context, List<Model> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.file, parent, false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        Model model = list.get(position);
        holder.messname.setText(model.getMessname());
        holder.location.setText(model.getLocation());
        holder.img.setImageResource(model.getImg());

        // Set click listener
        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // ViewHolder class
    public static class Myholder extends RecyclerView.ViewHolder {
        TextView messname, location;
        ImageView img;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            messname = itemView.findViewById(R.id.text2);
            location = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.img);
        }
    }
}
