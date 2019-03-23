package com.changxinyue.graduationproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.beans.ScenicTypeInfo;

import java.util.List;

public class TypeNameAdapter extends BaseAdapter {

    private Context context;
    private List<ScenicTypeInfo> scenicTypeInfoList;

    public TypeNameAdapter(Context context, List<ScenicTypeInfo> scenicTypeInfoList) {
        this.context = context;
        this.scenicTypeInfoList = scenicTypeInfoList;
    }

    @Override
    public int getCount() {
        return scenicTypeInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_type_name, null);
            viewHolder.tv_type_name = convertView.findViewById(R.id.tv_type_name);
            viewHolder.v_tag = convertView.findViewById(R.id.v_tag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_type_name.setText(scenicTypeInfoList.get(position).getType_name());
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_type_name;
        private View v_tag;
    }
}
