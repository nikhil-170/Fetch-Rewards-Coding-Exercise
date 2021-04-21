package com.example.fetchrewards;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class itemsFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient(); // Using OKHttp3 Library to perform a GET request on url
    final  String TAG = "demo";
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Item> itemsList = new ArrayList<>();
    Set<Integer> idSet = new HashSet<>();
    itemsWithNamesAdapter adapter;
    Switch aSwitch;
    public itemsFragment() {
        //Empty constructor of items fragment.
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflated the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_items, container, false);

        getActivity().setTitle("Fetch Rewards Coding Exercise");
        recyclerView = view.findViewById(R.id.itemsRecyclerViewId);
        aSwitch = view.findViewById(R.id.switch1);
        aSwitch.setChecked(false); // set the switch to "false" by default
        aSwitch.setText("Complete List");

        getJsonObjects(); //Calling the method to get the JSON objects with the name field null and ""

        //enabled the switch checkedChangeListener , to call two different methods as per user choice
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked == true){
                    buttonView.setText("List without null/empty names");
                    getFilteredJSONObjects(); //calling method to get the filtered JSON objects and adding them to a items ArrayList
                }else{
                    buttonView.setText("Complete List");
                    getJsonObjects();
                }
            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new itemsWithNamesAdapter(getContext(), itemsList, idSet); //setting the primary adapter with the list.
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /*
    * Method to get the JSON objects with the name field null and ""
    * Using OKHttp3 Library to perform a GET request on url
    * */
        void getJsonObjects(){

            Request request = new Request.Builder()
                    .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
                    .build();

            //Making a OkHttp Client Request
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        try {
                            itemsList.clear();
                            JSONArray jsonArray = new JSONArray(response.body().string()); // Fetching the JSON array from the response body of the call back function
                            for(int i =0; i< jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Item itemObject = new Item(jsonObject);
                                itemsList.add(itemObject);
                            }
                            getListIds(itemsList);
                            sortByName();
                            // Notifying the adapter on the Main Thread
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }
            });
        }


    /*
    * Method to get the filtered JSON objects without the name field with null and ""
    * Using OKHttp3 Library to perform a GET request on url
    * */
        void getFilteredJSONObjects(){


            Request request = new Request.Builder()
                    .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        try{
                            itemsList.clear();
                            JSONArray jsonArrayWithoutEmptyNames = new JSONArray(response.body().string()); // Fetching the JSON array from the response body of the call back function
                            for(int i = 0; i<jsonArrayWithoutEmptyNames.length(); ++i){
                                JSONObject jsonObject =  jsonArrayWithoutEmptyNames.getJSONObject(i);
                                Item itemObject = new Item(jsonObject);
                                if(itemObject.name.equals("null") || itemObject.name.equals("")){ // Filtering the objects with name field null & "".
                                    continue;
                                }else{
                                    itemsList.add(itemObject);
                                }
                            }
                            getListIds(itemsList);
                            sortByName();
                            // Notifying the adapter on the Main Thread
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    /*
    * Method to create a HashSet of the List Ids.
    * */
        void getListIds(ArrayList<Item> items){

            for(int i = 0; i< items.size(); i++){
                idSet.add(items.get(i).listid);
            }
        }

        void sortByName(){
            //Sorting the items list according the name field.
            Collections.sort(itemsList, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return  o1.name.compareTo(o2.name);
                }
            });

        }
}