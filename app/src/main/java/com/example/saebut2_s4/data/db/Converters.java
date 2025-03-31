package com.example.saebut2_s4.data.db;

import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public static String fromList(List<String> list) {
        return new JSONArray(list).toString(); // Convert List<String> to JSON String
    }

    @TypeConverter
    public static List<String> fromString(String value) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(value);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
