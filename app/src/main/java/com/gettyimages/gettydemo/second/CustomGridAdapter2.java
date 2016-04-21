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
public class CustomGridAdapter2 extends CustomAdapter2 {

    public CustomGridAdapter2(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomAdapter2.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.gridview_item, null);

            holder = new CustomAdapter2.ViewHolder();
            holder.idView = (TextView) convertView.findViewById(R.id.id);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (CustomAdapter2.ViewHolder) convertView.getTag();
        }
        if(itemMutator != null) {
            ItemMutator imageItem = itemMutator.get(position);
            holder.idView.setText(imageItem.getId());
            Picasso.with(mContext).load(imageItem.getUrl()).into(holder.imageView);
            //holder.imageView.setImageBitmap(imageItem.getBitmap());
        }

        return convertView;
    }
}
