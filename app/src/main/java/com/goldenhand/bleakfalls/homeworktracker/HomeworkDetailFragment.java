package com.goldenhand.bleakfalls.homeworktracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.goldenhand.bleakfalls.homeworktracker.dummy.DummyContent;

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
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
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
        if (mHomework != null) {
            //System.out.println("DISPLAYED!");
            EditText nameEditText = (EditText) rootView.findViewById(R.id.homework_name);
            nameEditText.setText(mHomework.getName());
        }

        final EditText mNameEditText = (EditText) rootView.findViewById(R.id.homework_name);

        Button mSaveChangesButton = (Button) rootView.findViewById(R.id.save_changes);
        mSaveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO
                for (int i=0;i<homeworkContent.mHomeworkList.size();i++) {
                    if (homeworkContent.mHomeworkList.get(i).getId().equals(getArguments().getString(ARG_ITEM_ID))) {
                        homeworkContent.mHomeworkList.get(i).setName(mNameEditText.getText().toString());
                    }
                }
            }
        });

        return rootView;
    }
}
