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
public class CustomGridAdapter extends CustomAdapter {

    public CustomGridAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.gridview_item, null);

            holder = new ViewHolder();
            holder.idView = (TextView) convertView.findViewById(R.id.id);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(itemMutator != null) {
            ItemMutator imageItem = itemMutator.get(position);
            if(imageItem.getTitle() != null && imageItem.getTitle().length() > 0) {
                holder.idView.setText(imageItem.getTitle());
            } else {
                holder.idView.setText("No Title");
            }
            Picasso.with(mContext).load(imageItem.getUrl()).into(holder.imageView);
        }

        return convertView;
    }
}

