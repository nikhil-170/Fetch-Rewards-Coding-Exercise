package com.example.fetchrewards;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

/*
* Item Class that defines the JSON array from the url
* */
public class Item implements Serializable {
    Integer listid, id;
    String name;

    public Item(JSONObject jsonObject) throws  JSONException {
        JSONObject localJSONObject = jsonObject;
        this.name = localJSONObject.getString("name");
        this.id = localJSONObject.getInt("id");
        this.listid = localJSONObject.getInt("listId");
    }

    @Override
    public String toString() {
        return "Item{" +
                "listid=" + listid +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
