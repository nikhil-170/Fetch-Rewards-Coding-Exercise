package com.example.fetchrewards;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;


/*
* Adapter For the main recycler View to set the tilte of the List Ids after grouping by
* and passing list of Item Objects to the Nested Recycler View according to the respective List Id.
* */
public class itemsWithNamesAdapter extends RecyclerView.Adapter<itemsWithNamesAdapter.itemsViewHolder> {
    private Activity activity;
    ArrayList<Item> items = new ArrayList<>();
    Context context;
    Set<Integer> idSet;

    public itemsWithNamesAdapter(Context context, ArrayList<Item> items, Set<Integer> idSet){
        this.items = items;
        this.idSet = idSet;
        this.context = context;

    }

    @NonNull
    @Override
    public itemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_withname_cardview, parent, false);
        itemsViewHolder viewHolder = new itemsViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull itemsViewHolder holder, int position) {

        if(position<5){
            if(idSet.contains(position + 1)){
                holder.textView.setText("List Id No: " + (position  + 1)); // Setting the Title of the Text View
            }

            ArrayList<Item> itemArrayList = new ArrayList<>();
            for(int j =0 ; j< items.size(); j++){
                if(items.get(j).listid == position +1) {
                    itemArrayList.add(items.get(j)); // Building an Array List according to the respective List ID grouping
                }
            }

            ItemDetailAdapter adapter = new ItemDetailAdapter(context, itemArrayList); // Calling the adapter class for the Nested Recycler View

            LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false);
            holder.itemsrecyclerView.setNestedScrollingEnabled(true);
            holder.itemsrecyclerView.hasFixedSize();
            holder.itemsrecyclerView.setLayoutManager(layoutManager);
            holder.itemsrecyclerView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }


    }


    @Override
    public int getItemCount() {
        return idSet.size();

    }

    public class itemsViewHolder extends RecyclerView.ViewHolder{

        RecyclerView itemsrecyclerView;
        TextView textView;

        public itemsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listHeadingID);
            itemsrecyclerView = itemView.findViewById(R.id.itemDetailsRecyclerViewId);


        }
    }
}