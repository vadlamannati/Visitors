package com.example.bharadwaj.visitors;

import java.util.Comparator;

/**
 * Created by Bharadwaj on 11/26/17.
 */

public class Visitor{

    private String mName;
    private long mArriveTime;
    private long mLeaveTime;

    public Visitor(String mName, long mArriveTime, long mLeaveTime) {
        this.mName = mName;
        this.mArriveTime = mArriveTime;
        this.mLeaveTime = mLeaveTime;
    }

    public String getmName() {
        return mName;
    }

    public long getmArriveTime() {
        return mArriveTime;
    }

    public long getmLeaveTime() {
        return mLeaveTime;
    }

}
