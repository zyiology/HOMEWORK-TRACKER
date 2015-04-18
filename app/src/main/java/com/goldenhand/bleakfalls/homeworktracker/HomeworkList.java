package com.goldenhand.bleakfalls.homeworktracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HomeworkList {
    Homework mHomeworks;
    /**
     * An array of sample (dummy) items.
     */
    public static List<HomeworkItem> homeworkItemList = new ArrayList<HomeworkItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, HomeworkItem> homeworkItemMap = new HashMap<String, HomeworkItem>();

    static {
        // Add 3 sample items.
        addHomeworkItem(new HomeworkItem("1", "Item 1"));
        addHomeworkItem(new HomeworkItem("2", "Item 2"));
        addHomeworkItem(new HomeworkItem("3", "Item 3"));
    }

    private static void addHomeworkItem(HomeworkItem item) {
        homeworkItemList.add(item);
        homeworkItemMap.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class HomeworkItem {
        public String id;
        public String content;

        public HomeworkItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
