package com.xyzlf.share.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xyzlf.share.library.R;
import com.xyzlf.share.library.bean.ChannelEntity;

import java.util.List;

/**
 * Fuction:
 *
 * @author Way Lo
 * @date 2019/7/15
 */
public class AppGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<ChannelEntity> mList;

    public AppGridAdapter(Context context, List<ChannelEntity> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ChannelEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.share_gridview_item,
                parent, false);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ChannelEntity entity = getItem(position);
        holder.imageView.setImageResource(entity.getIcon());
        holder.textView.setText(entity.getName());
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
