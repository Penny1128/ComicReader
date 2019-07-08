package com.xkcd.comicreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xkcd.comicreader.adapter.ComicViewAdapter;
import com.xkcd.comicreader.db.ComicDatabaseHelper;
import com.xkcd.comicreader.util.Constant;

import java.util.ArrayList;

public class FavorActivity extends Activity {

    private RecyclerView recyclerView;
    private ArrayList<String> favorComic;

    private ComicDatabaseHelper comicBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);

        recyclerView = findViewById(R.id.favor_view);

        comicBaseHelper = new ComicDatabaseHelper(FavorActivity.this);

        initDatas();

        // set adaper and layout manager for recycler view
        ComicViewAdapter adapter = new ComicViewAdapter(this, favorComic);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        // set item onclick listener
        adapter.setOnItemClickListener(new ComicViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                String title = favorComic.get(position);
                String[] subStrings = title.split(":");
                Integer comicId = Integer.valueOf(subStrings[0]); // get comicid
                Toast.makeText(FavorActivity.this,"Clicked: "+ comicId,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FavorActivity.this, ComicActivity.class);
                intent.putExtra("comic_id", comicId.toString());
                startActivity(intent);
            }
        });
    }

    /**
     * init favor comic datas for recyclerview
     */
    private void initDatas () {
        // init favor data from db
        favorComic = comicBaseHelper.getComicList(Constant.FAVOR_STATUS);

    }
}
