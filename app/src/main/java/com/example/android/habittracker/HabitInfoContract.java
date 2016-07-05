package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Suraj Singh on 6/28/2016.
 */
public final class HabitInfoContract {
    public HabitInfoContract() {
    }

    public static abstract class HabitTable implements BaseColumns {
        public static final String TABLE_NAME = "Habit";
        public static final String COLUMN_NAME_HABITID = "habitid";
        public static final String COLUMN_NAME_HABITNAME = "habitname";
        public static final String COLUMN_NAME_STARTDATE = "startdate";
        public static final String COLUMN_NAME_ENDDATE = "enddate";
        public static final String COLUMN_NAME_HOURSPERDAY = "hoursperday";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = "INT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HabitTable.TABLE_NAME + " (" +
                    HabitTable._ID + " INTEGER PRIMARY KEY," +
                    HabitTable.COLUMN_NAME_HABITID + INT_TYPE + COMMA_SEP +
                    HabitTable.COLUMN_NAME_HABITNAME + TEXT_TYPE + COMMA_SEP +
                    HabitTable.COLUMN_NAME_STARTDATE + TEXT_TYPE + COMMA_SEP +
                    HabitTable.COLUMN_NAME_ENDDATE + TEXT_TYPE + COMMA_SEP +
                    HabitTable.COLUMN_NAME_HOURSPERDAY + INT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitTable.TABLE_NAME;

    public class HabitInfoDbHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "HabitInfo.db";

        public HabitInfoDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            deleteDatabase(db);
            onCreate(db);
        }

        public void deleteDatabase(SQLiteDatabase db) {
            db.execSQL(SQL_DELETE_ENTRIES);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        public void insert(int id, String title, String startDate, String endDate, int hoursPerDay) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(HabitTable.COLUMN_NAME_HABITID, id);
            values.put(HabitTable.COLUMN_NAME_HABITNAME, title);
            values.put(HabitTable.COLUMN_NAME_STARTDATE, startDate);
            values.put(HabitTable.COLUMN_NAME_ENDDATE, endDate);
            values.put(HabitTable.COLUMN_NAME_HOURSPERDAY, hoursPerDay);
            long newRowId;
            newRowId = db.insert(
                    HabitTable.TABLE_NAME,
                    null,
                    values);
        }

        public Cursor read(String habit[]) {
            SQLiteDatabase db = this.getReadableDatabase();

            String[] projection = {
                    HabitTable.COLUMN_NAME_HABITID,
                    HabitTable.COLUMN_NAME_HABITNAME,
                    HabitTable.COLUMN_NAME_STARTDATE,
                    HabitTable.COLUMN_NAME_ENDDATE,
                    HabitTable.COLUMN_NAME_HOURSPERDAY
            };

            String sortOrder =
                    HabitTable.COLUMN_NAME_HOURSPERDAY + " DESC";

            Cursor c = db.query(
                    HabitTable.TABLE_NAME,
                    projection,
                    HabitTable.COLUMN_NAME_HABITNAME,
                    habit,
                    null,
                    null,
                    sortOrder,
                    null
            );
            c.moveToFirst();

            return c;
        }

        public void delete() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(HabitTable.TABLE_NAME, null, null);
        }

        public int update(String habit, int hoursPerDayUpdated) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(HabitTable.COLUMN_NAME_HOURSPERDAY, hoursPerDayUpdated);

            String selection = HabitTable.COLUMN_NAME_HABITNAME;
            String selectionArgs[] = {habit};

            int count = db.update(
                    HabitTable.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            return count;
        }

    }


}

