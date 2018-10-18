package com.imooc.demo.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Function:
 * Create date on 16/8/29.
 *
 * @author Conquer
 * @version 1.0
 */
public class LessonResult {
    int mStatus;
    String mMessage;
    List<LessonInfo> mLessonInfos = new ArrayList<>();

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<LessonInfo> getLessonInfos() {
        return mLessonInfos;
    }

    public void setLessonInfos(List<LessonInfo> lessonInfos) {
        mLessonInfos = lessonInfos;
    }

}
