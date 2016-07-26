package com.gigaspaces.vaultforandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gigaspaces.vaultforandroid.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ListParentClass> mParents;


    private static class ViewHolder {
        TextView mItemTextView;
    }

    public ListViewAdapter(Context context, ArrayList<ListParentClass> parents) {
        mParents = (parents == null) ? new ArrayList<ListParentClass>() : parents;
        mContext = context;
    }

    @Override
    public int getCount() {
        return (mParents == null ) ? 0 : mParents.size();
    }

    @Override
    public ListParentClass getItem(int position) {
        return (mParents == null || position < 0) ? null : mParents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListParentClass item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.main_list_group, parent, false);
            viewHolder.mItemTextView = (TextView) convertView.findViewById(R.id.groupTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mItemTextView.setText(item.getTitle());

        return convertView;
    }

    public void setParents(ArrayList<ListParentClass> parents) {
        mParents = (parents == null) ? new ArrayList<ListParentClass>() : parents;;
        notifyDataSetChanged();
    }

}
