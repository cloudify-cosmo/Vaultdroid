package com.gigaspaces.vaultdroid.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gigaspaces.vaultdroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SecretDataListViewAdapter extends BaseAdapter {
    private ArrayList<Map.Entry<String, String>> mItems;
    private Context mContext;

    public SecretDataListViewAdapter(Context context, Map<String, String> items) {
        mItems = new ArrayList<>();
        if (items != null) {
            mItems.addAll(items.entrySet());
        }

        mContext = context;
    }


    private static class ViewHolder {
        TextView dataKey;
        TextView dataKeyValue;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Map.Entry<String, String> pair = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.secret_data_list_item, parent, false);
            viewHolder.dataKey = (TextView) convertView.findViewById(R.id.dataKey);
            viewHolder.dataKeyValue = (TextView) convertView.findViewById(R.id.dataKeyValue);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.dataKey.setText(pair.getKey());
        viewHolder.dataKeyValue.setText(pair.getValue());

        return convertView;
    }


    public void updateParents(JSONObject data) {
        HashMap<String, String> pairs = new HashMap<>();

        Iterator iterator = data.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            try {
                pairs.put(key, data.getString(key));
            } catch (JSONException e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
        }

        mItems.clear();
        mItems.addAll(pairs.entrySet());

        notifyDataSetChanged();
    }
}
