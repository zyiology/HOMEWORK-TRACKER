package com.goldenhand.bleakfalls.homeworktracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;



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

    /*static {
        addItem(new Homework("Assignment 1","Math",new GregorianCalendar(2015,4,4),new GregorianCalendar(2015,4,4),false,false,mCurrentID));
        addItem(new Homework("Practice Quiz","Physics",new GregorianCalendar(2015,4,5), new GregorianCalendar(2015,8,2),false,false,mCurrentID));
    }*/

    public static void addItem(Homework homework) {
        mHomeworkList.add(homework);
        HOMEWORK_MAP.put(homework.getId().toString(), homework);
        mCurrentID++;
    }

    public static void sortItems() {
        ArrayList<Homework> notDoneHomeworkList = new ArrayList<>();
        ArrayList<Homework> doneHomeworkList = new ArrayList<>();
        for (int i=0;i<mHomeworkList.size();i++) {
            Homework tempItem = mHomeworkList.get(i);
            if (tempItem.isDone()) {
                doneHomeworkList.add(tempItem);
            } else {
                notDoneHomeworkList.add(tempItem);
            }
        }
        HomeworkComparator homeworkComparator = new HomeworkComparator();
        Collections.sort(doneHomeworkList, homeworkComparator);
        Collections.sort(notDoneHomeworkList, homeworkComparator);
        notDoneHomeworkList.addAll(doneHomeworkList);
        mHomeworkList = notDoneHomeworkList;
    }


    public static class HomeworkComparator implements Comparator<Homework> {
        public int compare(Homework a, Homework b) {
            return a.getName().compareToIgnoreCase(b.getName());
        }
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
        boolean mRemind;
        Integer id;

        public Homework(String mName, String mSubjectName, GregorianCalendar mDueDate, GregorianCalendar mRemindDate, boolean mDone, boolean mRemind, Integer id) {
            this.mName = mName;
            this.mSubjectName = mSubjectName;
            this.mDueDate = mDueDate;
            this.mRemindDate = mRemindDate;
            this.mDone = mDone;
            this.mRemind = mRemind;
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

        public boolean isRemind() {
            return mRemind;
        }

        public void setRemind(boolean mRemind) {
            this.mRemind = mRemind;
        }
    }
}
