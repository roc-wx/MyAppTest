package com.imooc.demo.app.model;

/**
 * Function:
 * Create date on 16/8/29.
 *
 * @author Conquer
 * @version 1.0
 */
public class LessonInfo {
    int mID;
    String mName;
    String mPicSmall;
    String mPicBig;
    String mDescription;
    String mLearner;

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPicSmall() {
        return mPicSmall;
    }

    public void setPicSmall(String picSmall) {
        mPicSmall = picSmall;
    }

    public String getPicBig() {
        return mPicBig;
    }

    public void setPicBig(String picBig) {
        mPicBig = picBig;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getLearner() {
        return mLearner;
    }

    public void setLearner(String learner) {
        mLearner = learner;
    }
}
