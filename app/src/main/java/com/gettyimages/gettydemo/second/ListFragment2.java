package com.gettyimages.gettydemo.second;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.gettyimages.gettydemo.second.MainActivity2;
import com.gettyimages.gettydemo.R;

/**
 * Created by namseok on 16. 4. 17..
 */
public class ListFragment2 extends Fragment {

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, container, false);
        mListView = (ListView) view.findViewById(R.id.custom_list);
        mListView.setAdapter(MainActivity2.mCustomListAdapter);
        mListView.setOnScrollListener(new GVOnScrollListener());
        return view;
    }

    public final class GVOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            try {
                if (((MainActivity2) getActivity()).mIsExecuting == false && mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1
                        && mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {

                        ((MainActivity2) getActivity()).appendData();

                }
            } catch(NullPointerException e) {
                //ignore -> getBottom can be null
            } catch(ClassCastException ce) {
                // If MainActivity,
            }

        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //do nothing
        }
    }
}
