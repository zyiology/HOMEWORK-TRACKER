package com.goldenhand.bleakfalls.homeworktracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * A list fragment representing a list of Homeworks. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link HomeworkDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class HomeworkListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;



    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeworkListFragment() {
    }

    public static HomeworkAdapter mHomeworkAdapter;
    private static ReminderReceiver reminderReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: onSavedInstanceState and getIntent
        //HomeworkContent.sortItems();
        mHomeworkAdapter = new HomeworkAdapter(getActivity(), R.layout.activity_homework_item, HomeworkContent.mHomeworkList);
        setListAdapter(mHomeworkAdapter);


        /*setListAdapter(new ArrayAdapter<HomeworkList.HomeworkItem>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                HomeworkList.homeworkItemList));*/
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        mCallbacks.onItemSelected(HomeworkContent.mHomeworkList.get(position).getId().toString(

        ));

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        //mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private class HomeworkAdapter extends ArrayAdapter<HomeworkContent.Homework> {
        Context mContext;
        private ArrayList<HomeworkContent.Homework> mHomeworks = new ArrayList<>();

        public HomeworkAdapter(Context context, int resource, ArrayList<HomeworkContent.Homework> homeworks) {
            super(context, resource, homeworks);
            mContext = context;
            mHomeworks = homeworks;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = new ViewHolder();

            if (row==null) {
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                row = inflater.inflate(R.layout.activity_homework_item, parent, false);
                holder.titleTextView = (TextView) row.findViewById(R.id.title);
                holder.detailsTextView = (TextView) row.findViewById(R.id.details);
                holder.doneIconImageView = (ImageView) row.findViewById(R.id.doneIcon);
                holder.remindIconImageView = (ImageView) row.findViewById(R.id.remindIcon);
                holder.backgroundRelativeLayout = (RelativeLayout) row.findViewById(R.id.list_item);
                row.setTag(holder);
            }
            else {
                holder = (ViewHolder) row.getTag();
            }

            final HomeworkContent.Homework currentHomework = mHomeworks.get(position);

            holder.titleTextView.setText(currentHomework.getName());
            holder.detailsTextView.setText(currentHomework.getSubjectName());

            Calendar UTCDueTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));//CONVERT TIME TO UTC SO IT MATCHES INTERNAL CLOCK
            Date localDueTime = currentHomework.getDueDate().getTime();
            UTCDueTime.setTime(localDueTime);
            if (currentHomework.isDone()) {//done = no colour
                holder.doneIconImageView.setImageResource(R.drawable.checkboxfilled);
            } else {
                holder.doneIconImageView.setImageResource(R.drawable.checkboxempty);
                System.out.println("SETTING COLOURS");
                System.out.println(UTCDueTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
                System.out.println(UTCDueTime.get(Calendar.YEAR));
                if (UTCDueTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() > 604800000) {//1 week = green
                    holder.backgroundRelativeLayout.setBackgroundColor(getResources().getColor(R.color.translucent_green));
                } else if (UTCDueTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis() > 259200000) {//3 days = yellow
                    holder.backgroundRelativeLayout.setBackgroundColor(getResources().getColor(R.color.translucent_yellow));
                } else {//less than 3 days = red
                    holder.backgroundRelativeLayout.setBackgroundColor(getResources().getColor(R.color.translucent_red));
                }
            }
            holder.doneIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentHomework.isDone()) {
                        currentHomework.setDone(false);
                    } else {
                        currentHomework.setDone(true);
                    }
                    HomeworkContent.sortItems();
                    clear();
                    addAll(HomeworkContent.mHomeworkList);
                    notifyDataSetChanged();
                }
            });

            if (currentHomework.isRemind()) {
                holder.remindIconImageView.setImageResource(R.drawable.clock);
            } else {
                holder.remindIconImageView.setImageResource(R.drawable.noclock);
            }
            holder.remindIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentHomework.isRemind()) {
                        currentHomework.setRemind(false);
                    } else {
                        currentHomework.setRemind(true);
                        for (int i = 0; i < HomeworkContent.mHomeworkList.size(); i++) {
                            if (HomeworkContent.mHomeworkList.get(i).isRemind()) {
                                //Intent alarmIntent = new Intent(getActivity(), ReminderReceiver.class);
                                Intent alarmIntent = new Intent("setNotification");
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                                Calendar tempAlarmTime = new GregorianCalendar(TimeZone.getTimeZone("UTC"));//CONVERT TIME TO UTC SO IT MATCHES INTERNAL CLOCK
                                Date localTimeZoneAlarm = currentHomework.getRemindDate().getTime();
                                tempAlarmTime.setTime(localTimeZoneAlarm);

                                alarmManager.set(AlarmManager.RTC_WAKEUP, tempAlarmTime.getTimeInMillis(), pendingIntent);

                                System.out.println(tempAlarmTime.getTimeInMillis());
                                System.out.println(System.currentTimeMillis());
                            }
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            //else {
            /*
            TextView textView = (TextView) row.findViewById(R.id.text);
            textView.setText(currentJoke.getQuestion());

            ImageView imageView = (ImageView) row.findViewById(R.id.image);
            if (currentJoke.getClicked() == true) {
                imageView.setVisibility(View.INVISIBLE);
            }
            else {
                imageView.setVisibility(View.VISIBLE);
            }*/
            return row;
        }

        class ViewHolder{
            TextView titleTextView;
            TextView detailsTextView;
            ImageView doneIconImageView;
            ImageView remindIconImageView;
            RelativeLayout backgroundRelativeLayout;
        }
    }

    /*public static void refreshFragmentForDueDate(int index, HomeworkContent.Homework newHomework) {
        mHomeworkAdapter.remove(mHomeworkAdapter.getItem(index));
        mHomeworkAdapter.insert(newHomework,index);
        mHomeworkAdapter.notifyDataSetChanged();
    }*/

    public static void refreshFragment() {
        mHomeworkAdapter.notifyDataSetChanged();
    }
}
