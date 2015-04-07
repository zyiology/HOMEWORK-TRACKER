package com.goldenhand.bleakfalls.homeworktracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;


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
    private boolean mTwoPane;

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

        Button mAddHomework = (Button) findViewById(R.id.add_homework);
        mAddHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // TODO: If exposing deep links into your app, handle intents here.
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
            HomeworkDetailFragment fragment = new HomeworkDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homework_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, HomeworkDetailActivity.class);
            detailIntent.putExtra(HomeworkDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
