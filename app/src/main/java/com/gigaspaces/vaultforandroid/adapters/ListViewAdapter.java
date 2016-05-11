package com.gigaspaces.vaultforandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gigaspaces.vaultforandroid.R;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<ListParentClass> {
    Context mContext;


    private static class ViewHolder {
        TextView mItemTextView;
    }

    public ListViewAdapter(Context context, ArrayList<ListParentClass> parents) {
        super(context, -1, parents);

        mContext = context;
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

    public void updateParents(ArrayList<ListParentClass> listItems) {
        clear();

        if (listItems != null) {
            for (int i = 0; i < listItems.size(); i++) {
                if (listItems.get(i) != null) {
                    insert(listItems.get(i), i);
                }
            }
        }

        notifyDataSetChanged();
    }

}
