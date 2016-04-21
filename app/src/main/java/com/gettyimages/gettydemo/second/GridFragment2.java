package com.gettyimages.gettydemo.second;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;

import com.gettyimages.gettydemo.second.MainActivity2;
import com.gettyimages.gettydemo.R;

/**
 * Created by namseok on 16. 4. 17..
 */
public class GridFragment2 extends Fragment {

    public GridView mGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview_layout, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridView);
        mGridView.setAdapter(MainActivity2.mCustomGridAdapter);
        mGridView.setOnScrollListener(new LVOnScrollListener());
        return view;
    }

    public final class LVOnScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

            try {
                if (((MainActivity2) getActivity()).mIsExecuting == false && mGridView.getLastVisiblePosition() == mGridView.getAdapter().getCount() - 1
                        && mGridView.getChildAt(mGridView.getChildCount() - 1).getBottom() <= mGridView.getHeight()) {

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
