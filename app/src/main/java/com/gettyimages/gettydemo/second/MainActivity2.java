package com.gettyimages.gettydemo.second;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.gettyimages.connectsdk.ConnectSdk;
import com.gettyimages.connectsdk.search.Search;
import com.gettyimages.gettydemo.GettyCrawler;
import com.gettyimages.gettydemo.ItemMutator;
import com.gettyimages.gettydemo.R;

import java.util.List;

/**
 * Created by namseok on 16. 4. 17..
 * Works like http://developers.gettyimages.com/en/trytheapi.html?
 */
public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = MainActivity2.class.getSimpleName();

    private InputMethodManager mInputMethodManager;

    private PagerAdapter2 mPagerAdapter;
    public static CustomListAdapter2 mCustomListAdapter;
    public static CustomGridAdapter2 mCustomGridAdapter;

    public boolean mIsExecuting;
    private int mCurrentPage = 1;
    private String mSearchString = ""; // default phrase
    private String[] mOrderType = {"best", "most_popular", "newest"} ;
    private String mOrder = mOrderType[0];
    private EditText mEditText;
    private ProgressBar mProgressBar;

    private GettyTask mTask;
    private CountDownTimer mCountDownTimer;
    private ConnectSdk connectSdk; //Getty SDK
    private Search search; //Getty SDK
    private static final String API_KEY = "tp82t7kkxux2km648bqvtudv";
    private static final String API_SECRET = "H52CD8BM7p6hgb6kgnRvUJJvZMB9cfAU8RPx22feM368Z";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputMethodManager =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        initView();

        mCustomListAdapter = new CustomListAdapter2(this);
        mCustomGridAdapter = new CustomGridAdapter2(this);
        connectSdk = new ConnectSdk(API_KEY, API_SECRET);
        search = connectSdk.Search();
        mTask = new GettyTask();
        mProgressBar.setVisibility(View.VISIBLE);

        mCountDownTimer = new CountDownTimer(30000, 10000) { // wait for 30 seconds and tick for 10 seconds
            public void onTick(long a) {
                boolean isRun = mTask.getStatus()==AsyncTask.Status.RUNNING;
                Log.d(TAG, "onTick : " + a + " / " + isRun);
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

    private class GettyTask extends AsyncTask<Void, Void, List<ItemMutator>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mCountDownTimer.cancel();
            mCountDownTimer.start();
        }

        @Override
        protected List<ItemMutator> doInBackground(Void... params) {
            return new GettyCrawler().crawlItems2(search, mSearchString, mOrder, mCurrentPage);
        }

        @Override
        protected void onPostExecute(List<ItemMutator> items) {
            Log.d(TAG, "onPostExecute " + mCurrentPage);
            if(items == null || items.size() == 0) {
                Toast.makeText(getApplicationContext(), "No Data or Retrieving Error", Toast.LENGTH_SHORT).show();
            }
            if(mCurrentPage <= 1) {
                mCustomListAdapter.setData(items);
                mCustomGridAdapter.setData(items);
            } else {
                mCustomListAdapter.addData(items);
                mCustomGridAdapter.addData(items);
            }
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

    private void executeTask() {
        if(mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            //mTask.cancel(true);
            Toast.makeText(getApplicationContext(), "Task is still running.\n Do it later.", Toast.LENGTH_SHORT).show();
            return;
        }
        mIsExecuting = true;

        mInputMethodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        mSearchString = mEditText.getText().toString();
        mProgressBar.setVisibility(View.VISIBLE);
        mTask = new GettyTask();
        mTask.execute();
    }

    // onClick event in layout
    public void searchImages(View v) {
        mCurrentPage = 1;
        executeTask();
    }

    private void initView() {
        Spinner spinner = (Spinner)findViewById(R.id.order_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mOrder = mOrderType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mEditText = (EditText)findViewById(R.id.phrase);
        mSearchString = mEditText.getText().toString();
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("ListView"));
        tabLayout.addTab(tabLayout.newTab().setText("GridView"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter2
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

    public void appendData() {
        mCurrentPage++;
        executeTask();
    }

}
