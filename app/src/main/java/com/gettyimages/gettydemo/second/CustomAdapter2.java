package com.gettyimages.gettydemo.second;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gettyimages.gettydemo.ItemMutator;

import java.util.List;

/**
 * Created by namseok on 16. 4. 17..
 */
public class CustomAdapter2 extends BaseAdapter {

    public Context mContext;
    public List<ItemMutator> itemMutator;
    public LayoutInflater layoutInflater;

    public CustomAdapter2(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<ItemMutator> itemMutator) {
        this.itemMutator = itemMutator;
        notifyDataSetChanged();
    }

    public void addData(List<ItemMutator> itemMutator) {
        if(this.itemMutator == null) {
            return;
        }
        for(ItemMutator list: itemMutator) {
            this.itemMutator.add(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(itemMutator == null) {
            return 30;
        } else {
            return itemMutator.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(itemMutator == null) {
            return 0;
        } else {
            return itemMutator.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    static class ViewHolder {
        TextView idView;
        TextView titleView;
        TextView captionView;
        ImageView imageView;
    }

}
