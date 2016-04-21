package com.gettyimages.gettydemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gettyimages.gettydemo.R;
import com.gettyimages.gettydemo.ItemMutator;
import com.squareup.picasso.Picasso;

/**
 * Created by namseok on 16. 4. 21..
 */
public class CustomListAdapter extends CustomAdapter {

    public CustomListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_item, null);

            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(itemMutator != null) {
            ItemMutator imageItem = itemMutator.get(position);
            if(imageItem.getTitle() != null && imageItem.getTitle().length() > 0) {
                holder.titleView.setText(imageItem.getTitle());
            } else {
                holder.titleView.setText("No Title");
            }
            Picasso.with(mContext).load(imageItem.getUrl()).into(holder.imageView);
        }

        return convertView;
    }
}

