package com.goldenhand.bleakfalls.homeworktracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HomeworkContent implements Serializable {

    /**
     * An array of sample (dummy) items.
     */
    public static ArrayList<Homework> mHomeworkList = new ArrayList<Homework>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Homework> HOMEWORK_MAP = new HashMap<String, Homework>();
    public static int mCurrentID = 0;

    static {
        addItem(new Homework("Assignment 1","Math",new GregorianCalendar(2015,4,4),new GregorianCalendar(2015,4,4),false,mCurrentID));
        addItem(new Homework("Practice Quiz","Physics",new GregorianCalendar(2015,4,5), new GregorianCalendar(2015,8,2),false, mCurrentID));
    }

    private static void addItem(Homework homework) {
        mHomeworkList.add(homework);
        HOMEWORK_MAP.put(homework.getId().toString(), homework);
        mCurrentID++;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Homework implements Serializable {
        String mName;
        String mSubjectName;
        GregorianCalendar mDueDate;
        GregorianCalendar mRemindDate;
        boolean mDone;
        Integer id;

        public Homework(String mName, String mSubjectName, GregorianCalendar mDueDate, GregorianCalendar mRemindDate, boolean mDone, Integer id) {
            this.mName = mName;
            this.mSubjectName = mSubjectName;
            this.mDueDate = mDueDate;
            this.mRemindDate = mRemindDate;
            this.mDone = mDone;
            this.id = id;
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

        public Integer getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
