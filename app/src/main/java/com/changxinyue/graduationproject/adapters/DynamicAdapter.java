package com.changxinyue.graduationproject.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.beans.SubmitInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DynamicAdapter extends BaseAdapter {

    private Context context;
    private List<SubmitInfo> submitInfoList;
    private List<String> submitImageList;

    public DynamicAdapter(Context context, List<SubmitInfo> submitInfoList) {
        this.context = context;
        this.submitInfoList = submitInfoList;
    }

    @Override
    public int getCount() {
        return submitInfoList.size();
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
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_dynamic, null);
            viewHolder.tv_nickname = convertView.findViewById(R.id.tv_nickname);
            viewHolder.tv_dynamic_content = convertView.findViewById(R.id.tv_dynamic_content);
            viewHolder.rv_dynamic = convertView.findViewById(R.id.rv_dynamic);
            viewHolder.iv_user_head = convertView.findViewById(R.id.iv_user_head);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor submitImageInfoCursor = db.query("submitImageInfo", new String[]{"submit_image"}, "submit_id = ?", new String[]{String.valueOf(submitInfoList.get(position).getSubmit_id())}, null, null, null);
        submitImageList = new ArrayList<String>();
        if(submitImageInfoCursor!=null&&submitImageInfoCursor.getCount()>0){
            while (submitImageInfoCursor.moveToNext()){
                String submitImage = submitImageInfoCursor.getString(0);
                submitImageList.add(submitImage);
            }
        }
        if (submitImageInfoCursor != null) {
            submitImageInfoCursor.close();
        }
        db.close();
        viewHolder.tv_nickname.setText(submitInfoList.get(position).getNickname());
        viewHolder.tv_dynamic_content.setText(submitInfoList.get(position).getSubmit_content());
        Glide.with(context).load(submitInfoList.get(position).getUser_head())
                .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(viewHolder.iv_user_head);
        viewHolder.rv_dynamic.setLayoutManager(gridLayoutManager);
        viewHolder.rv_dynamic.setAdapter(new PhotoWallAdapter(context,submitImageList));
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_nickname;
        private RecyclerView rv_dynamic;
        private ImageView iv_user_head;
        private TextView tv_dynamic_content;
    }

}
