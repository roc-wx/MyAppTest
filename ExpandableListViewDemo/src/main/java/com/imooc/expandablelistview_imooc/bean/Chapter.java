package com.imooc.expandablelistview_imooc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baidu on 2018/6/10.
 */

public class Chapter {

    private int id;
    private String name;

    public static final String TABLE_NAME = "tb_chapter";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";

    private List<ChapterItem> children = new ArrayList<>();

    public Chapter() {
    }

    public Chapter(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChapterItem> getChildren() {
        return children;
    }

    public void addChild(ChapterItem child) {
        children.add(child);
        child.setPid(getId());
    }

    public void addChild(int id, String childName) {
        ChapterItem chapterItem = new ChapterItem(id, childName);
        chapterItem.setPid(getId());
        children.add(chapterItem);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
