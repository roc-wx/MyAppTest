package com.imooc.demo;

import java.util.List;

/**
 * Created by renkangke .
 */
public class LessonResult {
    private int mStatus;
    private List<Lesson> mLessons;

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public List<Lesson> getLessons() {
        return mLessons;
    }

    public void setLessons(List<Lesson> lessons) {
        mLessons = lessons;
    }

    @Override
    public String toString() {
        return "LessonResult{" +
                "mStatus=" + mStatus +
                ", mLessons=" + mLessons +
                '}';
    }

    public static class Lesson{
        private int mID;
        private int mLearnerNumber;
        private String mName;
        private String mSmallPictureUrl;
        private String mBigPictureUrl;
        private String mDescription;

        public int getID() {
            return mID;
        }

        public void setID(int ID) {
            mID = ID;
        }

        public int getLearnerNumber() {
            return mLearnerNumber;
        }

        public void setLearnerNumber(int learnerNumber) {
            mLearnerNumber = learnerNumber;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getSmallPictureUrl() {
            return mSmallPictureUrl;
        }

        public void setSmallPictureUrl(String smallPictureUrl) {
            mSmallPictureUrl = smallPictureUrl;
        }

        public String getBigPictureUrl() {
            return mBigPictureUrl;
        }

        public void setBigPictureUrl(String bigPictureUrl) {
            mBigPictureUrl = bigPictureUrl;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        @Override
        public String toString() {
            return "Lesson{" +
                    "mID=" + mID +
                    ", mLearnerNumber=" + mLearnerNumber +
                    ", mName='" + mName + '\'' +
                    ", mSmallPictureUrl='" + mSmallPictureUrl + '\'' +
                    ", mBigPictureUrl='" + mBigPictureUrl + '\'' +
                    ", mDescription='" + mDescription + '\'' +
                    '}';
        }
    }
}
