package com.imooc.demo.model;

/**
 * Function:
 * Create date on 16/8/9.
 *
 * @author Conquer
 * @version 1.0
 */
public class ActivityItem {
    private String mName;
    private Class mActivityClass;

    public ActivityItem(String name, Class activityClass) {
        mName = name;
        mActivityClass = activityClass;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Class getActivityClass() {
        return mActivityClass;
    }

    public void setActivityClass(Class activityClass) {
        mActivityClass = activityClass;
    }
}
