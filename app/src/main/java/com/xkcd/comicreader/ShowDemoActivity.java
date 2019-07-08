package com.xkcd.comicreader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.xkcd.comicreader.model.Comic;
import com.xkcd.comicreader.server.BitmapCache;
import com.xkcd.comicreader.util.Constant;
import com.xkcd.comicreader.util.ParseJson;

public class ShowDemoActivity extends Activity {

    private TextView comicInfo;
    private TextView comicTitle;
    private NetworkImageView comicImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        comicImg = findViewById(R.id.show_img);
        comicInfo = findViewById(R.id.show_info);
        comicTitle = findViewById(R.id.show_title);

        Comic showComic = ParseJson.jsonToObj(Constant.NEW_DATA.get(0));

        comicTitle.setText("Comic title: "+showComic.getTitle());
        comicInfo.setText("More Information: "+ showComic.getAlt());

        // show picture

        RequestQueue requestQueue = Volley.newRequestQueue(ShowDemoActivity.this);

        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());

        // default/error image setting
        comicImg.setDefaultImageResId(R.drawable.ic_launcher_foreground);
        comicImg.setErrorImageResId(R.drawable.ic_launcher_foreground);
        comicImg.setImageUrl(showComic.getImg(), imageLoader);

    }
}
