package com.goldenhand.bleakfalls.homeworktracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.goldenhand.bleakfalls.homeworktracker.dummy.DummyContent;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A fragment representing a single Homework detail screen.
 * This fragment is either contained in a {@link HomeworkListActivity}
 * in two-pane mode (on tablets) or a {@link HomeworkDetailActivity}
 * on handsets.
 */
public class HomeworkDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    //public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private static HomeworkContent.Homework mHomework;
    private HomeworkContent homeworkContent;

    public static final String ARG_ITEM_ID = "id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeworkDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mHomework = HomeworkContent.HOMEWORK_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }

        if (getArguments().containsKey(HomeworkListActivity.HOMEWORK_CONTENT)) {
            homeworkContent = (HomeworkContent) getArguments().getSerializable(HomeworkListActivity.HOMEWORK_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_homework_detail, container, false);
        // Show the dummy content as text in a TextView.
        final EditText mNameEditText = (EditText) rootView.findViewById(R.id.homework_name);
        final EditText mSubjNameEditText = (EditText) rootView.findViewById(R.id.homework_subj_name);
        final Button mDueDateButton = (Button) rootView.findViewById(R.id.homework_due_date);
        final Button mDueTimeButton = (Button) rootView.findViewById(R.id.homework_due_time);
        final Button mRemindDateButton = (Button) rootView.findViewById(R.id.homework_remind_date);
        final Button mRemindTimeButton = (Button) rootView.findViewById(R.id.homework_remind_time);

        GregorianCalendar dueDate = mHomework.getDueDate();
        GregorianCalendar remindDate = mHomework.getRemindDate();

        if (mHomework != null) {
            mNameEditText.setText(mHomework.getName());
            mSubjNameEditText.setText(mHomework.getSubjectName());

            mDueDateButton.setText("Due Date: " + String.valueOf(dueDate.get(Calendar.DAY_OF_MONTH)) + '/' + String.valueOf(dueDate.get(Calendar.MONTH) + 1) + '/' + String.valueOf(dueDate.get(Calendar.YEAR)));
            mDueTimeButton.setText("Due Time: " + fixTimeForDisplay(String.valueOf(dueDate.get(Calendar.HOUR_OF_DAY))) + ':' + fixTimeForDisplay(String.valueOf(dueDate.get(Calendar.MINUTE))));
            mRemindDateButton.setText("Reminder Date: " + String.valueOf(remindDate.get(Calendar.DAY_OF_MONTH)) + '/' +  String.valueOf(remindDate.get(Calendar.MONTH) + 1) + '/' + String.valueOf(remindDate.get(Calendar.YEAR)));
            mRemindTimeButton.setText("Remind Time: " + fixTimeForDisplay(String.valueOf(remindDate.get(Calendar.HOUR_OF_DAY))) + ':' + fixTimeForDisplay(String.valueOf(remindDate.get(Calendar.MINUTE))));
        }

        Button mSaveChangesButton = (Button) rootView.findViewById(R.id.save_changes);
        mSaveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO
            for (int i=0;i<homeworkContent.mHomeworkList.size();i++) {
                if (homeworkContent.mHomeworkList.get(i).getId().toString().equals(getArguments().getString(ARG_ITEM_ID))) {
                    mHomework.setName(mNameEditText.getText().toString());
                    mHomework.setSubjectName(mSubjNameEditText.getText().toString());
                    homeworkContent.mHomeworkList.set(i,mHomework);
                }
            }
            Intent intent = new Intent(getActivity(),HomeworkListActivity.class);
            startActivity(intent);
            }
        });

        Button mExportHomeworkButton = (Button) rootView.findViewById(R.id.export_homework);
        mExportHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt = new Intent(Intent.ACTION_SEND);
                bt.setType("text/plain");
                bt.putExtra(Intent.EXTRA_STREAM, Uri.parse(mHomework.getSubjectName() + ": " + mHomework.getName() + "\nDue:" + mHomework.getDueDate()));

                PackageManager pm = getActivity().getPackageManager();
                List<ResolveInfo> appsList = pm.queryIntentActivities(bt, 0);

                String packageName = null;
                String className = null;
                boolean found = false;
                for (ResolveInfo info:appsList) {
                    packageName = info.activityInfo.packageName;
                    if (packageName.endsWith("email")) {
                        className = info.activityInfo.name;
                        found = true;
                        break;
                    }
                }
                System.out.println("FOUNDINGS");

                if (found) {
                    bt.setClassName(packageName, className);
                    startActivity(bt);
                }
                else {
                    Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final DatePickerDialog dueDatePickerDialog = new DatePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar newDate = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                for (int i=0;i<homeworkContent.mHomeworkList.size();i++) {
                    if (homeworkContent.mHomeworkList.get(i).getId().toString().equals(getArguments().getString(ARG_ITEM_ID))) {
                        mHomework.setDueDate(newDate);
                        homeworkContent.mHomeworkList.remove(i);
                        homeworkContent.mHomeworkList.add(i,mHomework);
                    }
                }
                mDueDateButton.setText("Due Date: " + String.valueOf(mHomework.getDueDate().get(Calendar.DAY_OF_MONTH)) + '/' + String.valueOf(mHomework.getDueDate().get(Calendar.MONTH)+1) + '/' + String.valueOf(mHomework.getDueDate().get(Calendar.YEAR)));
            }
        },mHomework.getDueDate().get(Calendar.YEAR),mHomework.getDueDate().get(Calendar.MONTH),mHomework.getDueDate().get(Calendar.DAY_OF_MONTH));

        mDueDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dueDatePickerDialog.show();
            }
        });

        final TimePickerDialog dueTimePickerDialog = new TimePickerDialog(getActivity(), TimePickerDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                GregorianCalendar newDate = new GregorianCalendar(mHomework.getDueDate().get(Calendar.YEAR), mHomework.getDueDate().get(Calendar.MONTH), mHomework.getDueDate().get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                for (int i=0;i<homeworkContent.mHomeworkList.size();i++) {
                    if (homeworkContent.mHomeworkList.get(i).getId().toString().equals(getArguments().getString(ARG_ITEM_ID))) {
                        mHomework.setDueDate(newDate);
                        homeworkContent.mHomeworkList.remove(i);
                        homeworkContent.mHomeworkList.add(i, mHomework);
                        mDueTimeButton.setText("Due Time: " + fixTimeForDisplay(String.valueOf(mHomework.getDueDate().get(Calendar.HOUR_OF_DAY))) + ':' + fixTimeForDisplay(String.valueOf(mHomework.getDueDate().get(Calendar.MINUTE))));
                    }
                }
            }
        }, mHomework.getDueDate().get(Calendar.HOUR_OF_DAY), mHomework.getDueDate().get(Calendar.MINUTE),true);

        mDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dueTimePickerDialog.show();
            }
        });

        final DatePickerDialog remindDatePickerDialog = new DatePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar newDate = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                for (int i=0;i<homeworkContent.mHomeworkList.size();i++) {
                    if (homeworkContent.mHomeworkList.get(i).getId().toString().equals(getArguments().getString(ARG_ITEM_ID))) {
                        mHomework.setRemindDate(newDate);
                        homeworkContent.mHomeworkList.remove(i);
                        homeworkContent.mHomeworkList.add(i, mHomework);
                    }
                }
                mRemindDateButton.setText("Remind Date: " + String.valueOf(mHomework.getRemindDate().get(Calendar.DAY_OF_MONTH)) + '/' + String.valueOf(mHomework.getRemindDate().get(Calendar.MONTH) + 1) + '/' + String.valueOf(mHomework.getRemindDate().get(Calendar.YEAR)));
            }
        },mHomework.getRemindDate().get(Calendar.YEAR),mHomework.getRemindDate().get(Calendar.MONTH),mHomework.getRemindDate().get(Calendar.DAY_OF_MONTH));

        mRemindDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                remindDatePickerDialog.show();
            }
        });

        final TimePickerDialog remindTimePickerDialog = new TimePickerDialog(getActivity(), TimePickerDialog.THEME_HOLO_DARK, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                GregorianCalendar newDate = new GregorianCalendar(mHomework.getRemindDate().get(Calendar.YEAR), mHomework.getRemindDate().get(Calendar.MONTH), mHomework.getRemindDate().get(Calendar.DAY_OF_MONTH),hourOfDay,minute);                for (int i=0;i<homeworkContent.mHomeworkList.size();i++) {
                    if (homeworkContent.mHomeworkList.get(i).getId().toString().equals(getArguments().getString(ARG_ITEM_ID))) {
                        mHomework.setRemindDate(newDate);
                        homeworkContent.mHomeworkList.remove(i);
                        homeworkContent.mHomeworkList.add(i,mHomework);
                        mRemindTimeButton.setText("Remind Time: " + fixTimeForDisplay(String.valueOf(mHomework.getRemindDate().get(Calendar.HOUR_OF_DAY))) + ':' + fixTimeForDisplay(String.valueOf(mHomework.getRemindDate().get(Calendar.MINUTE))));
                    }
                }
            }
        }, mHomework.getRemindDate().get(Calendar.HOUR_OF_DAY), mHomework.getRemindDate().get(Calendar.MINUTE),true);

        mRemindTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remindTimePickerDialog.show();
            }
        });

        mDueTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dueTimePickerDialog.show();
            }
        });


        return rootView;
    }

    private String fixTimeForDisplay(String toFix) {
        if (toFix.length() == 1) {
            toFix = "0" + toFix;
        }
        return toFix;
    }
}
