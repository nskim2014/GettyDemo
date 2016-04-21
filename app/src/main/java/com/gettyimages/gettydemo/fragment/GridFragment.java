package com.gettyimages.gettydemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gettyimages.gettydemo.MainActivity;
import com.gettyimages.gettydemo.R;

/**
 * Created by namseok on 16. 4. 21..
 */
public class GridFragment extends Fragment {

    public GridView mGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridview_layout, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridView);
        mGridView.setAdapter(MainActivity.mCustomGridAdapter);
        return view;
    }
}
