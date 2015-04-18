package com.goldenhand.bleakfalls.homeworktracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Homework implements Serializable {
    String mName;
    String mSubjectName;
    GregorianCalendar mDueDate;
    GregorianCalendar mRemindDate;
    boolean mDone;

    public Homework(String mName, String mSubjectName, GregorianCalendar mDueDate, GregorianCalendar mRemindDate, boolean mDone) {
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

    public GregorianCalendar getDueDate() {
        return mDueDate;
    }

    public void setDueDate(GregorianCalendar mDueDate) {
        this.mDueDate = mDueDate;
    }

    public GregorianCalendar getRemindDate() {
        return mRemindDate;
    }

    public void setRemindDate(GregorianCalendar mRemindDate) {
        this.mRemindDate = mRemindDate;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean mDone) {
        this.mDone = mDone;
    }
}
