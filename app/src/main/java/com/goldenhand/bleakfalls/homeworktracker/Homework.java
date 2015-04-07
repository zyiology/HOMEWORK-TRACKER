package com.goldenhand.bleakfalls.homeworktracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Homework implements Serializable {
    String mName;
    String mSubjectName;
    Date mDueDate;
    Date mRemindDate;
    boolean mDone;

    public Homework(String mName, String mSubjectName, Date mDueDate, Date mRemindDate, boolean mDone) {
        this.mName = mName;
        this.mSubjectName = mSubjectName;
        this.mDueDate = mDueDate;
        this.mRemindDate = mRemindDate;
        this.mDone = mDone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSubjectName() {
        return mSubjectName;
    }

    public void setSubjectName(String mSubjectName) {
        this.mSubjectName = mSubjectName;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public void setDueDate(Date mDueDate) {
        this.mDueDate = mDueDate;
    }

    public Date getRemindDate() {
        return mRemindDate;
    }

    public void setRemindDate(Date mRemindDate) {
        this.mRemindDate = mRemindDate;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean mDone) {
        this.mDone = mDone;
    }
}
