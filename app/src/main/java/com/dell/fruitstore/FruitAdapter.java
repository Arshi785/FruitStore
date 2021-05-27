package com.dell.fruitstore;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ClassViewHolder> {
    ArrayList<FruitItem> fruitItems;
    Context context;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FruitAdapter(Context context, ArrayList<FruitItem> classItems) {
        this.fruitItems = classItems;
        this.context = context;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView fruitName;
        TextView price;
        TextView quantity;

        public ClassViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            fruitName = itemView.findViewById(R.id.fruit_tv);
            price = itemView.findViewById(R.id.price_tv);
            quantity = itemView.findViewById(R.id.quant_tv);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),0,0,"EDIT");
            menu.add(getAdapterPosition(),1,0,"DELETE");
        }
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruititem, parent, false);
        return new ClassViewHolder(itemView, onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.fruitName.setText(fruitItems.get(position).getFruitName());
       holder.price.setText(String.valueOf(fruitItems.get(position).getFruitPrice()));
        holder.quantity.setText(String.valueOf(fruitItems.get(position).getFruitQuant()));

    }

    @Override
    public int getItemCount() {
        return fruitItems.size();
    }
}
