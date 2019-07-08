package com.xkcd.comicreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.xkcd.comicreader.db.ComicDatabaseHelper;
import com.xkcd.comicreader.server.BitmapCache;
import com.xkcd.comicreader.util.Constant;

import java.util.HashMap;

public class ComicActivity extends Activity {

    private TextView comicTitle;
    private NetworkImageView comicImg;
    private ComicDatabaseHelper comicBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);

        comicImg = findViewById(R.id.comic_img);
        comicTitle = findViewById(R.id.comic_title);

        // get the comicId from intent
        Intent intent = getIntent();
        Integer comicId = Integer.valueOf(intent.getStringExtra("comic_id"));

        comicBaseHelper = new ComicDatabaseHelper(ComicActivity.this);

        // get comic infos from db
        HashMap<String, String> comicInfos = comicBaseHelper.getOneComic(comicId);

        comicTitle.setText("Comic title: "+comicInfos.get("title"));

        // show picture
        RequestQueue requestQueue = Volley.newRequestQueue(ComicActivity.this);

        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());

        // default/error image setting
        comicImg.setDefaultImageResId(R.drawable.ic_launcher_foreground);
        comicImg.setErrorImageResId(R.drawable.ic_launcher_foreground);
        comicImg.setImageUrl(comicInfos.get("img_link"), imageLoader);

    }
}
