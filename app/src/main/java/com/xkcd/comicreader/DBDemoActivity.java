package com.xkcd.comicreader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xkcd.comicreader.db.ComicDatabaseHelper;
import com.xkcd.comicreader.model.Comic;
import com.xkcd.comicreader.util.Constant;
import com.xkcd.comicreader.util.ParseJson;

import java.util.ArrayList;

public class DBDemoActivity extends Activity implements View.OnClickListener {

    private Button addBtn;
    private Button delBtn;
    private Button updateBtn;
    private Button inquireBtn;

    private ComicDatabaseHelper comicBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        comicBaseHelper = new ComicDatabaseHelper(DBDemoActivity.this);

        addBtn = findViewById(R.id.db_add);
        delBtn = findViewById(R.id.db_del);
        updateBtn = findViewById(R.id.db_update);
        inquireBtn = findViewById(R.id.db_inquire);

        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        inquireBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Comic comic = ParseJson.jsonToObj(Constant.NEW_DATA.get(0)); // test data

        switch (view.getId()){
            case R.id.db_add:

                ArrayList<String> unreadComics = new ArrayList<>();
                unreadComics.addAll(Constant.INIT_DATA);
                unreadComics.addAll(Constant.MORE_DATA);
                ArrayList<String> favorComics = Constant.FAVOR_DATA;
                ArrayList<String> readComics = Constant.OLD_DATA;
                ArrayList<String> newComics = Constant.NEW_DATA;

                for (String s : readComics){
                    comicBaseHelper.insert(ParseJson.jsonToObj(s), Constant.INIT_STATUS);
                }

                for (String s : unreadComics){
                    comicBaseHelper.insert(ParseJson.jsonToObj(s), Constant.UNREAD_STATUS);
                }
                for (String s : favorComics){
                    comicBaseHelper.insert(ParseJson.jsonToObj(s), Constant.FAVOR_STATUS);
                }
                for (String s : newComics){
                    comicBaseHelper.insert(ParseJson.jsonToObj(s), Constant.UNREAD_STATUS);
                }
                Toast.makeText(DBDemoActivity.this, "ADD "+ comic.getNum(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.db_del:
                comicBaseHelper.delete(comic.getNum());
                Toast.makeText(DBDemoActivity.this, "DELETE "+comic.getNum(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.db_update:
                comicBaseHelper.updateStatus(comic.getNum(), Constant.FAVOR_STATUS); // update to favor
                Toast.makeText(DBDemoActivity.this, "UPDATE" + comic.getNum(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.db_inquire:
                ArrayList<String> inquireFavors = comicBaseHelper.getComicList(Constant.INIT_STATUS);
                Toast.makeText(DBDemoActivity.this, inquireFavors.toString(), Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
