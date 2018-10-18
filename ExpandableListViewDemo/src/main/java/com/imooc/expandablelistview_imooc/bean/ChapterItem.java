package com.imooc.expandablelistview_imooc.bean;

/**
 * Created by baidu on 2018/6/10.
 */

public class ChapterItem {
    private int id;
    private String name;
    private int pid;

    public static final String TABLE_NAME = "tb_chapter_item";
    public static final String COL_ID = "_id";
    public static final String COL_PID = "pid";
    public static final String COL_NAME = "name";

    public ChapterItem() {
    }

    public ChapterItem(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pId) {
        this.pid = pId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
