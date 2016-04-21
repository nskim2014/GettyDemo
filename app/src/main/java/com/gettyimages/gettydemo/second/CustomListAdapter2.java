package com.gettyimages.gettydemo.second;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gettyimages.gettydemo.ItemMutator;
import com.gettyimages.gettydemo.R;
import com.squareup.picasso.Picasso;

/**
 * Created by namseok on 16. 4. 17..
 */
public class CustomListAdapter2 extends CustomAdapter2 {

    public CustomListAdapter2(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_item, null);

            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.title);
            holder.captionView = (TextView) convertView.findViewById(R.id.caption);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(itemMutator != null) {
            ItemMutator imageItem = itemMutator.get(position);
            holder.titleView.setText(imageItem.getTitle());
            String caption = imageItem.getCaption();
            if(caption == null || caption.equalsIgnoreCase("null")) {
                holder.captionView.setText("NO CAPTION");
            } else {
                holder.captionView.setText(caption);
            }
            Picasso.with(mContext).load(imageItem.getUrl()).into(holder.imageView);
            //holder.imageView.setImageBitmap(imageItem.getBitmap());
        }

        return convertView;
    }
}
