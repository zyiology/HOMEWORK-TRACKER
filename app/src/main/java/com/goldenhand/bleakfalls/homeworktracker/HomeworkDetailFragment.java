package com.goldenhand.bleakfalls.homeworktracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import com.goldenhand.bleakfalls.homeworktracker.dummy.DummyContent;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
        final Button mRemindDateButton = (Button) rootView.findViewById(R.id.homework_remind_date);

        if (mHomework != null) {
            mNameEditText.setText(mHomework.getName());
            mSubjNameEditText.setText(mHomework.getSubjectName());

            mDueDateButton.setText("Due Date: " + String.valueOf(mHomework.getDueDate().get(Calendar.DAY_OF_MONTH)) + '/' + String.valueOf(mHomework.getDueDate().get(Calendar.MONTH)) + '/' + String.valueOf(mHomework.getDueDate().get(Calendar.YEAR)));
            mRemindDateButton.setText("Reminder Date: " + String.valueOf(mHomework.getRemindDate().get(Calendar.DAY_OF_MONTH)) + '/' +  String.valueOf(mHomework.getRemindDate().get(Calendar.MONTH)) + '/' + String.valueOf(mHomework.getRemindDate().get(Calendar.YEAR)));
        }

        Button mSaveChangesButton = (Button) rootView.findViewById(R.id.save_changes);
        mSaveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO
                for (int i=0;i<homeworkContent.mHomeworkList.size();i++) {
                    if (homeworkContent.mHomeworkList.get(i).getId().toString().equals(getArguments().getString(ARG_ITEM_ID))) {
                        homeworkContent.mHomeworkList.get(i).setName(mNameEditText.getText().toString());
                        homeworkContent.mHomeworkList.get(i).setName(mSubjNameEditText.getText().toString());
                    }
                }
            }
        });

        final DatePickerDialog dueDatePickerDialog = new DatePickerDialog(getActivity(), DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                GregorianCalendar newDate = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                for (int i=0;i<homeworkContent.mHomeworkList.size();i++) {
                    if (homeworkContent.mHomeworkList.get(i).getId().toString().equals(getArguments().getString(ARG_ITEM_ID))) {
                        homeworkContent.mHomeworkList.get(i).setDueDate(newDate);
                    }
                }
                mDueDateButton.setText("Due Date: " + String.valueOf(mHomework.getDueDate().get(Calendar.DAY_OF_MONTH)) + '/' + String.valueOf(mHomework.getDueDate().get(Calendar.MONTH)) + '/' + String.valueOf(mHomework.getDueDate().get(Calendar.YEAR)));
            }
        },mHomework.getDueDate().get(Calendar.YEAR),mHomework.getDueDate().get(Calendar.MONTH),mHomework.getDueDate().get(Calendar.DAY_OF_MONTH));

        mDueDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dueDatePickerDialog.show();
            }
        });

        mRemindDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        return rootView;
    }

}
