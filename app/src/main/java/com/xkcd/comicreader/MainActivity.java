package com.xkcd.comicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.xkcd.comicreader.adapter.*;
import com.xkcd.comicreader.db.ComicDatabaseHelper;
import com.xkcd.comicreader.model.Comic;
import com.xkcd.comicreader.util.*;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String jsonUrl = Constant.UPDATE_URL;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Button newestBtn;
    private Button favorBtn;
    private Button shopBtn;
    private Button showBtn;
    private Button dbBtn;

    private ComicDatabaseHelper comicBaseHelper;
    private ArrayList<String> initData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comicBaseHelper = new ComicDatabaseHelper(MainActivity.this);

        initViews();
        initDatas();

        // set adaper and layout manager for recycler view
        ComicViewAdapter adapter = new ComicViewAdapter(this, initData);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        // set recycler view item onClick listener
        adapter.setOnItemClickListener(new ComicViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                String title = initData.get(position);
                String[] subStrings = title.split(":");
                Integer comicId = Integer.valueOf(subStrings[0]); // get comicid
                Toast.makeText(MainActivity.this,"Clicked: "+ comicId,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ComicActivity.class);
                intent.putExtra("comic_id", comicId.toString()); // put string (be careful, integer is not ok)
                startActivity(intent);
            }
        });

        // sets for swipe refresh layout
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        // set swipe refresh listener for swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Collections.reverse(Constant.NEW_DATA);

                        ArrayList<String> newDatas = new ArrayList<String>();

                        for(String json: Constant.NEW_DATA){
                            Comic comic = ParseJson.jsonToObj(json);
                            newDatas.add(comic.getNum()+": "+comic.getTitle());
                        }
                        adapter.addItem(newDatas);
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "Refresh finished!", Toast.LENGTH_SHORT).show();

                    }
                }, 1000);

            }
        });
    }

    /**
     *
     * init views
     */
    private void initViews() {

        // buttons
        newestBtn = this.findViewById(R.id.btn_newest);
        favorBtn = this.findViewById(R.id.btn_favor);
        shopBtn = this.findViewById(R.id.btn_shop);
        showBtn = this.findViewById(R.id.btn_pic);
        dbBtn = this.findViewById(R.id.btn_db);

        // recycler list view and swipe fresh layout
        recyclerView = this.findViewById(R.id.comic_view);
        swipeRefreshLayout = this.findViewById(R.id.swipe_fresh_layout);

        newestBtn.setOnClickListener(this);
        favorBtn.setOnClickListener(this);
        shopBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
        dbBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.btn_newest:
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(jsonUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Comic comicInfo = ParseJson.jsonToObj(s);
                        Toast.makeText(MainActivity.this, comicInfo.getNum() + ":" + comicInfo.getTitle(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MainActivity.this, "Error" + volleyError, Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);

                break;
            case R.id.btn_favor:
                intent.setClass(MainActivity.this, FavorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_shop:
                intent.setClass(MainActivity.this, ShopActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pic:
                intent.setClass(MainActivity.this, ShowDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_db:
                intent.setClass(MainActivity.this, DBDemoActivity.class);
                startActivity(intent);
                break;
        }
    }

        /**
         * init datas for recyclerview
         */
        private void initDatas () {
            // init unread comic from db
            initData = comicBaseHelper.getComicList(Constant.UNREAD_STATUS);

        }



}
