package com.goldenhand.bleakfalls.homeworktracker;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * An activity representing a list of Homeworks. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link HomeworkDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link HomeworkListFragment} and the item details
 * (if present) is a {@link HomeworkDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link HomeworkListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class HomeworkListActivity extends FragmentActivity
        implements HomeworkListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public boolean mTwoPane;
    public static HomeworkContent homeworkContent = new HomeworkContent();
    public static String HOMEWORK_CONTENT = "com.goldenhand.bleakfalls.homeworktracker.homeworklistactivity.homeworkcontent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_list);

        if (findViewById(R.id.homework_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((HomeworkListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.homework_list))
                    .setActivateOnItemClick(true);
        }

        Button mAddHomeworkButton = (Button) findViewById(R.id.add_homework);
        mAddHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HomeworkContent.addItem(new HomeworkContent.Homework("New Homework", "Subject", new GregorianCalendar(), new GregorianCalendar(), false, false, HomeworkContent.mCurrentID));
                        HomeworkListFragment.refreshFragment();
                    }
                });
            }
        });

        if (HomeworkContent.mHomeworkList == new ArrayList<HomeworkContent.Homework>()) {

            try {
                //creating string from input
                FileInputStream iS = openFileInput("homework_list");
                StringBuilder builder = new StringBuilder();
                int ch;
                while ((ch = iS.read()) != -1) {
                    builder.append((char) ch);
                }
                String output = builder.toString();
                System.out.println(output);

                int counter = 0;//counts the number of line separators reached
                String tempString = "";
                String[] tempHwData = new String[7];
                for (int i = 0; i < output.length(); i++) {
                    char temp = output.charAt(i);
                    if (temp == System.getProperty("line.separator").charAt(0)) {
                        tempHwData[counter] = tempString;
                        counter += 1;
                        tempString = "";
                    } else {
                        tempString += temp;
                    }
                    boolean done;
                    boolean remind;
                    if (counter == 7) {
                        SimpleDateFormat dueDateFormat = new SimpleDateFormat("MMM dd yyyy HH mm ss");
                        GregorianCalendar dueDate = new GregorianCalendar();
                        dueDate.setTime(dueDateFormat.parse(tempHwData[2]));
                        SimpleDateFormat remindDateFormat = new SimpleDateFormat("MMM dd yyyy HH mm ss");
                        GregorianCalendar remindDate = new GregorianCalendar();
                        remindDate.setTime(remindDateFormat.parse(tempHwData[3]));
                        if (tempHwData[4].equals("true")) {
                            done = true;
                        } else {
                            done = false;
                        }
                        if (tempHwData[5].equals("true")) {
                            remind = true;
                        } else {
                            remind = false;
                        }
                        HomeworkContent.addItem(new HomeworkContent.Homework(tempHwData[0], tempHwData[1], dueDate, remindDate, done, remind, Integer.parseInt(tempHwData[6])));
                        HomeworkContent.mCurrentID = Integer.parseInt(tempHwData[6]);
                        counter = 0;
                    }
                }
                //System.out.println("constructing");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //TODO:ADD EXTENSION TOO MUCH HW SEE COUNSELLOR
        //TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String filename = "homework_list";
        System.out.println("DESTROYING ACTIVITY");
        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            for (int i=0;i<HomeworkContent.mHomeworkList.size();i++) {
                HomeworkContent.Homework tempHw = HomeworkContent.mHomeworkList.get(i);
                Date dueDate = tempHw.getDueDate().getTime();
                SimpleDateFormat dueDateFormat = new SimpleDateFormat("MMM dd yyyy HH mm ss");
                Date remindDate = tempHw.getRemindDate().getTime();
                SimpleDateFormat remindDateFormat = new SimpleDateFormat("MMM dd yyyy HH mm ss");
                fos.write(tempHw.getName().getBytes());fos.write(System.getProperty("line.separator").getBytes());
                System.out.println(tempHw.getName());
                fos.write(tempHw.getSubjectName().getBytes());fos.write(System.getProperty("line.separator").getBytes());
                fos.write(dueDateFormat.format(dueDate).getBytes());fos.write(System.getProperty("line.separator").getBytes());
                fos.write(remindDateFormat.format(remindDate).getBytes());fos.write(System.getProperty("line.separator").getBytes());
                if (tempHw.isDone()) {
                    fos.write("true".getBytes());
                }
                else {
                    fos.write("false".getBytes());
                }
                fos.write(System.getProperty("line.separator").getBytes());
                if (tempHw.isRemind()) {
                    fos.write("true".getBytes());
                }
                else {
                    fos.write("false".getBytes());
                }
                fos.write(System.getProperty("line.separator").getBytes());
                fos.write(tempHw.getId().toString().getBytes());fos.write(System.getProperty("line.separator").getBytes());
            }
            fos.close();
            System.out.println("OUTPUTTING DATA");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HomeworkContent.mHomeworkList = new ArrayList<HomeworkContent.Homework>();
    }

    /**
     * Callback method from {@link HomeworkListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(HomeworkDetailFragment.ARG_ITEM_ID, id);
            arguments.putSerializable(HOMEWORK_CONTENT, homeworkContent);
            HomeworkDetailFragment fragment = new HomeworkDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.homework_detail_container, fragment).commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, HomeworkDetailActivity.class);
            detailIntent.putExtra(HOMEWORK_CONTENT, homeworkContent);
            detailIntent.putExtra(HomeworkDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
    public NotificationManager getNotificationMgr() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
}
