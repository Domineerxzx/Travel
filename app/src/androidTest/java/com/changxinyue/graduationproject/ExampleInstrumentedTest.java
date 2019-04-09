package com.changxinyue.graduationproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.changxinyue.graduationproject.beans.SubmitInfo;
import com.changxinyue.graduationproject.database.MyOpenHelper;
import com.changxinyue.graduationproject.utils.dialogUtils.TwoButtonDialog;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.changxinyue.graduationproject", appContext.getPackageName());
    }

    @Test
    public void databaseTest() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(InstrumentationRegistry.getTargetContext());
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone_number", "17709841369");
        contentValues.put("nickname","test");
        contentValues.put("user_head","test");
        contentValues.put("submit_content","test");
        long insert = db.insert("submitInfo", null, contentValues);
        db.close();
    }
}
