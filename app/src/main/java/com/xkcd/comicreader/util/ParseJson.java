package com.xkcd.comicreader.util;

import android.util.Log;

import com.google.gson.Gson;
import com.xkcd.comicreader.model.Comic;

import java.util.ArrayList;

import static com.xkcd.comicreader.util.Constant.URL_HEAD;
import static com.xkcd.comicreader.util.Constant.URL_TAIL;

public class ParseJson {

    public static Comic jsonToObj(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Comic.class);

    }


    public static ArrayList<String> initJsonLinks(Integer firstNum, Integer finalNum) {
        ArrayList<String> jsonLinks = new ArrayList<String>();

        for (int i = 0; i < (finalNum - firstNum + 1); i++) {
            int currentNum = firstNum + i;
            jsonLinks.add(URL_HEAD + currentNum + URL_TAIL);
            Log.d("Json links", URL_HEAD + currentNum + URL_TAIL);
        }

        return jsonLinks;
    }

}
