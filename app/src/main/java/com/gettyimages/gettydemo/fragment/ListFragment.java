package com.gettyimages.gettydemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gettyimages.gettydemo.MainActivity;
import com.gettyimages.gettydemo.R;

/**
 * Created by namseok on 16. 4. 21..
 */
public class ListFragment extends Fragment {

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_layout, container, false);
        mListView = (ListView) view.findViewById(R.id.custom_list);
        mListView.setAdapter(MainActivity.mCustomListAdapter);
        return view;
    }

}
