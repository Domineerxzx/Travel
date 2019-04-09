package com.changxinyue.graduationproject.services;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.changxinyue.graduationproject.database.MyOpenHelper;
import com.changxinyue.graduationproject.handlers.AdminLoginHandler;
import com.changxinyue.graduationproject.handlers.AdminRegisterHandler;
import com.changxinyue.graduationproject.handlers.UserLoginHandler;
import com.changxinyue.graduationproject.handlers.UserRegisterHandler;
import com.changxinyue.graduationproject.properties.ProjectProperties;
import com.changxinyue.graduationproject.utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class NetworkService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void userLogin(Context context, String phone_number, String password, ServiceConnection serviceConnection) {
            NetworkService.this.userLogin(context, phone_number, password, serviceConnection);
        }

        public void userGetRequestCode(Context context, String user_phone_number, ServiceConnection serviceConnection) {
            NetworkService.this.userGetRequestCode(context, user_phone_number, serviceConnection);
        }

        public void userRegister(Context context, String phone_number, String request_code, String password, String nickname, ServiceConnection serviceConnection) {
            NetworkService.this.userRegister(context, phone_number, request_code, password, nickname, serviceConnection);
        }

        public void adminLogin(Context context, String phone_number, String password, ServiceConnection serviceConnection) {
            NetworkService.this.adminLogin(context, phone_number, password, serviceConnection);
        }

        public void adminRegister(Context context, String phone_number, String request_code, String password, String nickname, ServiceConnection serviceConnection) {
            NetworkService.this.adminRegister(context, phone_number, request_code, password, nickname, serviceConnection);
        }

        public void adminGetRequestCode(Context context, String phone_number, ServiceConnection serviceConnection) {
            NetworkService.this.adminGetRequestCode(context, phone_number, serviceConnection);
        }

    }

    private void adminGetRequestCode(final Context context, String phone_number, ServiceConnection serviceConnection) {
        final AdminRegisterHandler adminRegisterHandler = new AdminRegisterHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneNumber", phone_number);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_GET_REQUEST_CODE, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                System.out.println("register" + "------------------------------------" + register_message);
                if (register_message.contains("{\"message\":1}")) {
                    adminRegisterHandler.sendEmptyMessage(ProjectProperties.GET_REQUEST_CODE_SUCCESS);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "获取验证码", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    adminRegisterHandler.sendEmptyMessage(ProjectProperties.GET_REQUEST_CODE_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "获取验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void adminRegister(final Context context, final String phone_number, String request_code, final String password, final String nickname, final ServiceConnection serviceConnection) {
        final AdminRegisterHandler adminRegisterHandler = new AdminRegisterHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneNumber", phone_number);
        builder.add("registerNumber", request_code);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_REGISTER, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                if (register_message.equals("{\"message\":1}")) {
                    updateRegisterInfo(context, phone_number, password, nickname, serviceConnection, adminRegisterHandler);
                    MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                    SQLiteDatabase writableDatabase = myOpenHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("phone_number", phone_number);
                    contentValues.put("password", password);
                    contentValues.put("nickname", nickname);
                    long userInfo = writableDatabase.insert("adminInfo", null, contentValues);
                    System.out.println("SUCCESS-----------------------数据库插入成功" + userInfo);
                    if (userInfo != -1) {
                        writableDatabase.close();
                    } else {
                        System.out.println("error-----------------------数据库插入失败" + userInfo);
                        writableDatabase.close();
                    }
                } else {
                    adminRegisterHandler.sendEmptyMessage(ProjectProperties.REGISTER_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void adminLogin(final Context context, final String phone_number, final String password, ServiceConnection serviceConnection) {
        final AdminLoginHandler adminLoginHandler = new AdminLoginHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userPhone", phone_number);
        builder.add("password", password);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_LOGIN, builder, new Callback() {

            private String userHead;
            private String nickname;

            @Override
            public void onFailure(Call call, IOException e) {
                boolean isCheckSuccess = false;
                MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                SQLiteDatabase db = myOpenHelper.getWritableDatabase();
                Cursor userInfo = db.query("adminInfo", new String[]{"password", "nickname", "user_head"}, "phone_number = ?", new String[]{phone_number}, null, null, null);
                if (userInfo != null && userInfo.getCount() > 0) {
                    while (userInfo.moveToNext()) {
                        String pw = userInfo.getString(0);
                        if (password.equals(pw)) {
                            isCheckSuccess = true;
                            SharedPreferences sharedPreferences = context.getSharedPreferences("adminInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("phone_number", phone_number);
                            edit.putString("nickname", userInfo.getString(1));
                            edit.putString("password", password);
                            edit.putString("userHead", userInfo.getString(2));
                            edit.commit();
                            adminLoginHandler.sendEmptyMessage(ProjectProperties.ADMIN_LOGIN_SUCCESS);
                            return;
                        }
                    }
                    if (isCheckSuccess == false) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                        adminLoginHandler.sendEmptyMessage(ProjectProperties.ADMIN_LOGIN_FAILED);
                    }
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "未找到该用户", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String login_message = response.body().string();
                System.out.println("login" + "------------------------------------" + login_message);
                if (login_message.contains("{\"message\":1}")) {

                    MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                    SQLiteDatabase writableDatabase = myOpenHelper.getWritableDatabase();
                    Cursor userInfo = writableDatabase.query("adminInfo", new String[]{"nickname", "user_head"},
                            "phone_number=?", new String[]{phone_number}, null, null, null);
                    if (userInfo != null || userInfo.getCount() > 0) {
                        while (userInfo.moveToNext()) {
                            nickname = userInfo.getString(0);
                            userHead = userInfo.getString(1);
                            System.out.println(nickname);
                        }
                    }
                    writableDatabase.close();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("adminInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("phone_number", phone_number);
                    edit.putString("nickname", nickname);
                    edit.putString("password", password);
                    edit.putString("userHead", userHead);
                    edit.commit();
                    adminLoginHandler.sendEmptyMessage(ProjectProperties.ADMIN_LOGIN_SUCCESS);
                } else {
                    adminLoginHandler.sendEmptyMessage(ProjectProperties.ADMIN_LOGIN_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "手机号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void userGetRequestCode(final Context context, String phone_number, ServiceConnection serviceConnection) {
        final UserRegisterHandler userRegisterHandler = new UserRegisterHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneNumber", phone_number);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_GET_REQUEST_CODE, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                System.out.println("register" + "------------------------------------" + register_message);
                if (register_message.contains("{\"message\":1}")) {
                    userRegisterHandler.sendEmptyMessage(ProjectProperties.GET_REQUEST_CODE_SUCCESS);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "获取验证码", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    userRegisterHandler.sendEmptyMessage(ProjectProperties.GET_REQUEST_CODE_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "获取验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void userRegister(final Context context, final String phone_number, String request_code, final String password, final String nickname, final ServiceConnection serviceConnection) {
        final UserRegisterHandler userRegisterHandler = new UserRegisterHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phoneNumber", phone_number);
        builder.add("registerNumber", request_code);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_REGISTER, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                if (register_message.equals("{\"message\":1}")) {
                    updateRegisterInfo(context, phone_number, password, nickname, serviceConnection, userRegisterHandler);
                    MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                    SQLiteDatabase writableDatabase = myOpenHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("phone_number", phone_number);
                    contentValues.put("password", password);
                    contentValues.put("nickname", nickname);
                    long userInfo = writableDatabase.insert("userInfo", null, contentValues);
                    if (userInfo != -1) {
                        writableDatabase.close();
                        System.out.println("SUCCESS-----------------------数据库插入成功" + userInfo);
                    } else {
                        System.out.println("error-----------------------数据库插入失败" + userInfo);
                        writableDatabase.close();
                    }
                } else {
                    userRegisterHandler.sendEmptyMessage(ProjectProperties.REGISTER_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void updateRegisterInfo(final Context context, final String phone_number, final String password, final String nickname, ServiceConnection serviceConnection, final UserRegisterHandler userRegisterHandler) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userPhoneNumber", phone_number);
        builder.add("userNickName", nickname);
        builder.add("userPassword", password);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_UPDATE_REGISTER_INFO, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                if (register_message.equals("{\"message\":1}")) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("nickname", nickname);
                    edit.putString("phone_number", phone_number);
                    edit.putString("password", password);
                    edit.commit();
                    userRegisterHandler.sendEmptyMessage(ProjectProperties.REGISTER_SUCCESS);
                } else {
                    userRegisterHandler.sendEmptyMessage(ProjectProperties.REGISTER_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "更新注册信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void updateRegisterInfo(final Context context, final String phone_number, final String password, final String nickname, ServiceConnection serviceConnection, final AdminRegisterHandler adminRegisterHandler) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userPhoneNumber", phone_number);
        builder.add("userNickName", nickname);
        builder.add("userPassword", password);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_UPDATE_REGISTER_INFO, builder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String register_message = response.body().string();
                if (register_message.equals("{\"message\":1}")) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("adminInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("nickname", nickname);
                    edit.putString("phone_number", phone_number);
                    edit.putString("password", password);
                    edit.commit();
                    adminRegisterHandler.sendEmptyMessage(ProjectProperties.REGISTER_SUCCESS);
                } else {
                    adminRegisterHandler.sendEmptyMessage(ProjectProperties.REGISTER_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "更新注册信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void userLogin(final Context context, final String phone_number, final String password, ServiceConnection serviceConnection) {

        final UserLoginHandler loginHandler = new UserLoginHandler(context, serviceConnection);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userPhone", phone_number);
        builder.add("password", password);
        builder.build();
        HttpUtils.sendOkHttpRequest(ProjectProperties.ADDRESS_LOGIN, builder, new Callback() {

            private String userHead;
            private String nickname;

            @Override
            public void onFailure(Call call, IOException e) {
                boolean isCheckSuccess = false;
                MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                SQLiteDatabase db = myOpenHelper.getWritableDatabase();
                Cursor userInfo = db.query("userInfo", new String[]{"password", "nickname", "user_head"}, "phone_number = ?", new String[]{phone_number}, null, null, null);
                if (userInfo != null && userInfo.getCount() > 0) {
                    while (userInfo.moveToNext()) {
                        String pw = userInfo.getString(0);
                        if (password.equals(pw)) {
                            isCheckSuccess = true;
                            SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("phone_number", phone_number);
                            edit.putString("nickname", userInfo.getString(1));
                            edit.putString("password", password);
                            edit.putString("userHead", userInfo.getString(2));
                            edit.commit();
                            loginHandler.sendEmptyMessage(ProjectProperties.USER_LOGIN_SUCCESS);
                            return;
                        }
                    }
                    if (isCheckSuccess == false) {
                        loginHandler.sendEmptyMessage(ProjectProperties.USER_LOGIN_FAILED);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "未找到该用户", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String login_message = response.body().string();
                System.out.println("login" + "------------------------------------" + login_message);
                if (login_message.contains("\"message\":1")) {

                    MyOpenHelper myOpenHelper = new MyOpenHelper(context);
                    SQLiteDatabase writableDatabase = myOpenHelper.getWritableDatabase();
                    Cursor userInfo = writableDatabase.query("userInfo", new String[]{"nickname", "user_head"},
                            "phone_number=?", new String[]{phone_number}, null, null, null);
                    if (userInfo != null || userInfo.getCount() > 0) {
                        while (userInfo.moveToNext()) {
                            nickname = userInfo.getString(0);
                            userHead = userInfo.getString(1);
                            System.out.println(nickname);
                        }
                    }
                    writableDatabase.close();
                    SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("phone_number", phone_number);
                    edit.putString("nickname", nickname);
                    edit.putString("password", password);
                    edit.putString("userHead", userHead);
                    edit.commit();
                    loginHandler.sendEmptyMessage(ProjectProperties.USER_LOGIN_SUCCESS);
                } else {
                    loginHandler.sendEmptyMessage(ProjectProperties.USER_LOGIN_FAILED);
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "手机号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}
