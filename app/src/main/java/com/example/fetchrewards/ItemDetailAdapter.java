package com.example.fetchrewards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.itemDetailViewHolder> {

    Context context;
    ArrayList<Item> itemsDetailList;


    public ItemDetailAdapter(Context context, ArrayList<Item> itemsDetailList){
        this.itemsDetailList = itemsDetailList;
        this.context = context;
    }
    @NonNull
    @Override
    public itemDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_card_view, parent, false);
        itemDetailViewHolder itemDetailViewHolder = new itemDetailViewHolder(view);
        return itemDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemDetailViewHolder holder, int position) {
        // Assigning the values of the fields of Item Objects to the card View.
        Item item = itemsDetailList.get(position);
        holder.id.setText("Id: "+item.id);
        holder.listId.setText("List ID: "+item.listid);
        holder.name.setText("Name: "+item.name);

    }

    @Override
    public int getItemCount() {
        return itemsDetailList.size();
    }

    public class itemDetailViewHolder extends RecyclerView.ViewHolder{

        TextView id, name, listId;
        public itemDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idTextViewId);
            name = itemView.findViewById(R.id.nameTextViewId);
            listId = itemView.findViewById(R.id.listIdTextViewId);
        }
    }

}
