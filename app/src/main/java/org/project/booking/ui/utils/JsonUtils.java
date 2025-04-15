package org.project.booking.ui.utils;

import android.content.Context;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.project.booking.ui.models.Hostel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<Hostel> loadHostelsFromAsset(Context context, String filename) {
        List<Hostel> hostelList = new ArrayList<>();

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Hostel>>() {}.getType();
            hostelList = gson.fromJson(json, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return hostelList;
    }
}
