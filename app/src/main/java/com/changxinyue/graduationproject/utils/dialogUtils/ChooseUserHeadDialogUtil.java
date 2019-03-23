package com.changxinyue.graduationproject.utils.dialogUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.TextView;


import com.changxinyue.graduationproject.R;
import com.changxinyue.graduationproject.fragments.SubmitFragment;
import com.changxinyue.graduationproject.properties.ProjectProperties;
import com.changxinyue.graduationproject.utils.PermissionUtil;

import java.io.File;

public class ChooseUserHeadDialogUtil {
    public static void showDialog(final Context context, final String phone_number, final long timeStamp) {
        PermissionUtil.requestPower(context, ((Activity) context), "android.permission.CAMERA");
        PermissionUtil.requestPower(context, ((Activity) context), "android.permission.WRITE_EXTERNAL_STORAGE");
        View view = View.inflate(context, R.layout.dialog_select_photo, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.setView(view).create();
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent photo_manager = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                photo_manager.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((Activity) context).startActivityForResult(photo_manager, ProjectProperties.FROM_GALLERY);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            private Uri mUri;

            // 调用照相机
            @Override
            public void onClick(View v) {
                String path = context.getFilesDir() + File.separator + "images" + File.separator;
                File file = new File(path + phone_number + timeStamp + ".jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //步骤二：Android 7.0及以上获取文件 Uri
                    mUri = FileProvider.getUriForFile(context, "com.changxinyue.graduationproject", file);
                } else {
                    //步骤三：获取文件Uri
                    mUri = Uri.fromFile(file);
                }
                //步骤四：调取系统拍照
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                ((Activity) context).startActivityForResult(intent, ProjectProperties.FROM_CAMERA);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showSelectSubmitDialog(final Context context, final SubmitFragment fragment, final String phone_number, final long timeStamp) {
        PermissionUtil.requestPower(context, ((Activity) context), "android.permission.CAMERA");
        PermissionUtil.requestPower(context, ((Activity) context), "android.permission.WRITE_EXTERNAL_STORAGE");
        View view = View.inflate(context, R.layout.dialog_select_submit, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.setView(view).create();
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent photo_manager = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                photo_manager.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                if (fragment != null){
                    fragment.startActivityForResult(photo_manager, ProjectProperties.FROM_GALLERY);
                }else{
                    ((Activity)context).startActivityForResult(photo_manager, ProjectProperties.FROM_GALLERY);
                }
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            private Uri mUri;

            // 调用照相机
            @Override
            public void onClick(View v) {
                String path = context.getFilesDir() + File.separator + "images" + File.separator;
                File file = new File(path + phone_number + timeStamp + ".jpg");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //步骤二：Android 7.0及以上获取文件 Uri
                    mUri = FileProvider.getUriForFile(context, "com.changxinyue.graduationproject", file);
                } else {
                    //步骤三：获取文件Uri
                    mUri = Uri.fromFile(file);
                }
                //步骤四：调取系统拍照
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                if (fragment != null){
                    fragment.startActivityForResult(intent, ProjectProperties.FROM_CAMERA);
                }else{
                    ((Activity)context).startActivityForResult(intent, ProjectProperties.FROM_CAMERA);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
