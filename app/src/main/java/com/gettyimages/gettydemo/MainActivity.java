package com.gettyimages.gettydemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gettyimages.gettydemo.adapter.CustomGridAdapter;
import com.gettyimages.gettydemo.adapter.CustomListAdapter;
import com.gettyimages.gettydemo.adapter.PagerAdapter;

import java.util.List;

/**
 * Created by namseok on 16. 4. 21..
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private PagerAdapter mPagerAdapter;
    public static CustomListAdapter mCustomListAdapter;
    public static CustomGridAdapter mCustomGridAdapter;

    public boolean mIsExecuting;
    private ProgressBar mProgressBar;

    private GettyTask mTask;
    private CountDownTimer mCountDownTimer;
    private static final String IMAGES_URL = "http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mCustomListAdapter = new CustomListAdapter(this);
        mCustomGridAdapter = new CustomGridAdapter(this);
        mTask = new GettyTask();
        mProgressBar.setVisibility(View.VISIBLE);

        mCountDownTimer = new CountDownTimer(30000, 10000) { // wait for 30 seconds and tick for 10 seconds
            public void onTick(long a) {
                //boolean isRun = mTask.getStatus()==AsyncTask.Status.RUNNING;
                //for debugging
            }
            public void onFinish() {
                if(mTask.getStatus() == AsyncTask.Status.RUNNING) {
                    Toast.makeText(getApplicationContext(), "Time out ...", Toast.LENGTH_SHORT).show();
                    if(mProgressBar != null) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                    mTask.cancel(true);
                }
            }
        };

        mTask.execute();
    }

    private class GettyTask extends AsyncTask<Void, Void, List<ItemMutator>>  {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCountDownTimer.cancel();
            mCountDownTimer.start();
        }

        @Override
        protected List<ItemMutator> doInBackground(Void... params) {
            return new GettyCrawler().crawlItems(IMAGES_URL);
        }

        @Override
        protected void onPostExecute(List<ItemMutator> elements) {
            Log.d(TAG, "onPostExecute");
            if(elements == null || elements .size() == 0) {
                Toast.makeText(getApplicationContext(), "No Data or Retrieving Error", Toast.LENGTH_SHORT).show();
            }
            mCustomListAdapter.setData(elements);
            mCustomGridAdapter.setData(elements);

            mProgressBar.setVisibility(View.GONE);
            mCountDownTimer.cancel();

            mIsExecuting = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        if(mTask != null) {
            mTask.cancel(true);
        }
    }

    // onClick event in layout
    public void searchImages(View v) {

    }

    private void initView() {
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.top_layout);
        topLayout.setVisibility(View.GONE);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("GridView"));
        tabLayout.addTab(tabLayout.newTab().setText("ListView"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

