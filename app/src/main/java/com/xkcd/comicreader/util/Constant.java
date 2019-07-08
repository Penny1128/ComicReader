package com.xkcd.comicreader.util;

import java.util.ArrayList;

public class Constant {

    //  http://xkcd.com/{comicId}/info.0.json -> URL_HEAD+Comic.getNum()+URL_TAIL
    //  http://xkcd.com/info.0.json -> URL_HEAD+URL_TAIL

    public static final String URL_HEAD = "https://xkcd.com/";
    public static final String URL_TAIL = "/info.0.json";
    public static final String UPDATE_URL = URL_HEAD+URL_TAIL;
    public static final String SHOP_URL = "https://store.xkcd.com/";

    public static String DB_NAME = "comic.db";
    public static String TABLE_FAVOR = "favor";
    public static Integer DB_VERSION = 1;

    //create table test (id integer primary key autoincrement, json text);
    /**
     * sqlite db table, also could add alt and other useful feature later
     */
    public static final String CREATE_TABLE = "(id integer primary key, " +
                                        "img text, " +
                                        "title text, " +
                                        "status integer)";  // status: 1 means normal status, for all data store (have read); 2 means haven't read; 3 means favor comic
                                                            // when refresh, the data should add status with 2 (haven't read)
                                                            // when add to favor, status should be changed to 1
                                                            // when the comic have been read change it into 1

    public static final Integer INIT_STATUS = 1;
    public static final Integer UNREAD_STATUS = 2;
    public static final Integer FAVOR_STATUS = 3;

    // suppose this is the data get from net
    public static final ArrayList<String> NEW_DATA =
            new ArrayList<String>(){{
                add("{\"month\": \"7\", \"num\": 2172, \"link\": \"\", \"year\": \"2019\", \"news\": \"\", \"safe_title\": \"Lunar Cycles\", \"transcript\": \"\", \"alt\": \"The Antikythera mechanism had a whole set of gears specifically to track the cyclic popularity of skinny jeans and low-rise waists.\", \"img\": \"https://imgs.xkcd.com/comics/lunar_cycles.png\", \"title\": \"Lunar Cycles\", \"day\": \"5\"}");
                add("{\"month\": \"7\", \"num\": 2171, \"link\": \"\", \"year\": \"2019\", \"news\": \"\", \"safe_title\": \"Shadow Biosphere\", \"transcript\": \"\", \"alt\": \"The typical Shadow Biology Department is housed in a building coated in a thin layer of desert varnish which renders it invisible to normal-world university staff.\", \"img\": \"https://imgs.xkcd.com/comics/shadow_biosphere.png\", \"title\": \"Shadow Biosphere\", \"day\": \"3\"}");
                add("{\"month\": \"7\", \"num\": 2170, \"link\": \"\", \"year\": \"2019\", \"news\": \"\", \"safe_title\": \"Coordinate Precision\", \"transcript\": \"\", \"alt\": \"40 digits: You are optimistic about our understanding of the nature of distance itself.\", \"img\": \"https://imgs.xkcd.com/comics/coordinate_precision.png\", \"title\": \"Coordinate Precision\", \"day\": \"1\"}");
                add("{\"month\": \"6\", \"num\": 2169, \"link\": \" [AT THE JULY 28TH MEETING][tab] \\\"Cancel the meeting! Our cover is blown.\\\"\", \"year\": \"2019\", \"news\": \"\", \"safe_title\": \"Predictive Models\", \"transcript\": \"\", \"alt\": \"WE WILL ARREST THE REVOLUTION MEMBERS\", \"img\": \"https://imgs.xkcd.com/comics/predictive_models.png\", \"title\": \"Predictive Models\", \"day\": \"28\"}");
                add("{\"month\": \"6\", \"num\": 2168, \"link\": \"\", \"year\": \"2019\", \"news\": \"\", \"safe_title\": \"Reading in the Original\", \"transcript\": \"\", \"alt\": \"The articles are much shorter, but I assume that's because this version predates the merger with the Hawaiian text that created the modern Hawaiian-Greek hybrid wiki-pedia.\", \"img\": \"https://imgs.xkcd.com/comics/reading_in_the_original.png\", \"title\": \"Reading in the Original\", \"day\": \"26\"}");
                add("{\"month\": \"6\", \"num\": 2167, \"link\": \"\", \"year\": \"2019\", \"news\": \"\", \"safe_title\": \"Motivated Reasoning Olympics\", \"transcript\": \"\", \"alt\": \"[later] I can't believe how bad corruption has become, especially given that our league split off from the statewide one a month ago SPECIFICALLY to protest this kind of flagrantly biased judging.\", \"img\": \"https://imgs.xkcd.com/comics/motivated_reasoning_olympics.png\", \"title\": \"Motivated Reasoning Olympics\", \"day\": \"24\"}");
                }};


}
