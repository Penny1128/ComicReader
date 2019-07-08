package com.xkcd.comicreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xkcd.comicreader.model.Comic;
import com.xkcd.comicreader.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;


public class ComicDatabaseHelper extends SQLiteOpenHelper {

    public ComicDatabaseHelper(Context context) {
        super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // test create sql: create table test (id integer primary key autoincrement, json text);
        // add: insert into test(json) values("1111111");
        String sql = "create table if not exists "+Constant.TABLE_FAVOR+ Constant.CREATE_TABLE;
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        String sql = "drop table if exists "+Constant.TABLE_FAVOR;
        String sql = "drop table if exists favor";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert(Comic comic, Integer status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("id", comic.getNum());
        value.put("img", comic.getImg());
        value.put("title", comic.getTitle());
        value.put("status", status);
        db.insert(Constant.TABLE_FAVOR,null,value);
        db.close();
    }

    public void delete(Integer comicId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("favor","id=?",new String[]{comicId.toString()});
        db.close();
    }

    public void updateStatus(Integer comicId, Integer status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValue = new ContentValues();
        newValue.put("status",status);
        db.update("favor", newValue , "id=?", new String[] {comicId.toString()});
        db.close();
    }

    public void updateComic(Integer comicId, Comic comic){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValue = new ContentValues();
        newValue.put("img",comic.getImg());
        newValue.put("title", comic.getTitle());
        db.update("favor", newValue , "id=?", new String[] {comicId.toString()});
        db.close();
    }

    // improve later: make the sql as same as Comic object, then use set to get Comic object
    // so this function could be used in show comic image and detailed information
    public HashMap<String, String> getOneComic(Integer comicId){
        HashMap<String, String> comicInfos = new HashMap<String, String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("favor", new String[] { "img",
                "title" }, "id=?", new String[] { comicId.toString() }, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                comicInfos.put("title", cursor.getString(1));
                comicInfos.put("img_link", cursor.getString(0));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return comicInfos;
    }

    /**
     * UNREAD_STATUS 2, return unread List; FAVOR_STATUS 3, return favor list
     * @param status  integer between 1-3
     * @return show title list , formated as {comidid}:{comictitle}
     */
    public ArrayList<String> getComicList(Integer status){
        ArrayList<String> favorComics = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("favor", new String[] { "id",
                "title" }, "status=?", new String[] { status.toString() }, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String comicTile = cursor.getString(0)+": "+ cursor.getString(1);
                favorComics.add(comicTile);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return favorComics;
    }


}
