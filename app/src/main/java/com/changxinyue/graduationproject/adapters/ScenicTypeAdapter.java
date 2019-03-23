package com.changxinyue.graduationproject.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.beans.ScenicTypeInfo;

import java.util.List;

public class ScenicTypeAdapter extends BaseAdapter {

    private Context context;
    private List<ScenicTypeInfo> scenicTypeInfoList;

    public ScenicTypeAdapter(Context context, List<ScenicTypeInfo> scenicTypeInfoList) {
        this.context = context;
        this.scenicTypeInfoList = scenicTypeInfoList;
    }

    public List<ScenicTypeInfo> getScenicTypeInfoList() {
        return scenicTypeInfoList;
    }

    public void setScenicTypeInfoList(List<ScenicTypeInfo> scenicTypeInfoList) {
        this.scenicTypeInfoList = scenicTypeInfoList;
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ScenicTypeAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ScenicTypeAdapter.ViewHolder();
            convertView = View.inflate(context, R.layout.item_scenic_type, null);
            viewHolder.et_scenic_type_name = convertView.findViewById(R.id.et_scenic_type_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ScenicTypeAdapter.ViewHolder) convertView.getTag();
        }
        if (scenicTypeInfoList.get(position).getType_name() != null) {
            viewHolder.et_scenic_type_name.setText(String.valueOf(scenicTypeInfoList.get(position).getType_name()));
        }
        viewHolder.et_scenic_type_name.setSingleLine(true);
        viewHolder.et_scenic_type_name.setImeOptions(EditorInfo.IME_ACTION_DONE);
        viewHolder.et_scenic_type_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                scenicTypeInfoList.get(position).setType_name(viewHolder.et_scenic_type_name.getText().toString().trim());
                viewHolder.et_scenic_type_name.clearFocus();
                return false;
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private EditText et_scenic_type_name;
    }
}
