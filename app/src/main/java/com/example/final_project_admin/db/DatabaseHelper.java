package com.example.final_project_admin.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "YogaApp.db";
    private static final int DATABASE_VERSION = 1;

    // Classes table
    public static final String TABLE_CLASSES = "Classes";
    public static final String COLUMN_CLASS_ID = "class_id";
    public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CAPACITY = "capacity";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TEACHER = "teacher";
    public static final String COLUMN_DESCRIPTION = "description";

    // Schedules table
    public static final String TABLE_SCHEDULES = "Schedules";
    public static final String COLUMN_SCHEDULE_ID = "schedule_id";
    public static final String COLUMN_SCHEDULE_CLASS_ID = "class_id";  // Foreign key to Classes
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_SCHEDULE_TEACHER = "teacher";
    public static final String COLUMN_COMMENTS = "comments";

    // SQL statements to create tables
    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE " + TABLE_CLASSES + " ("
            + COLUMN_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DAY_OF_WEEK + " TEXT NOT NULL, "
            + COLUMN_TIME + " TEXT NOT NULL, "
            + COLUMN_CAPACITY + " INTEGER, "
            + COLUMN_DURATION + " INTEGER, "
            + COLUMN_PRICE + " REAL, "
            + COLUMN_TYPE + " TEXT, "
            + COLUMN_TEACHER + " TEXT NOT NULL, "
            + COLUMN_DESCRIPTION + " TEXT);";

    private static final String CREATE_TABLE_SCHEDULES = "CREATE TABLE " + TABLE_SCHEDULES + " ("
            + COLUMN_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SCHEDULE_CLASS_ID + " INTEGER, "
            + COLUMN_DATE + " TEXT NOT NULL, "
            + COLUMN_SCHEDULE_TEACHER + " TEXT NOT NULL, "
            + COLUMN_COMMENTS + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_SCHEDULE_CLASS_ID + ") REFERENCES " + TABLE_CLASSES + "(" + COLUMN_CLASS_ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLASSES);
        db.execSQL(CREATE_TABLE_SCHEDULES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
        onCreate(db);
    }

    // CRUD operations for Classes

    // Add new class
    public boolean addClass(String dayOfWeek, String time, int capacity, int duration, double price, String type, String teacher, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_OF_WEEK, dayOfWeek);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_CAPACITY, capacity);
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_TEACHER, teacher);
        values.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_CLASSES, null, values);
        if (result == -1){
            db.close();
            return false;
        }else {
            db.close();
            return true;
        }
    }

    public boolean updateClass(int ID, String dayOfWeek, String time, int capacity, int duration, double price, String type, String teacher, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_OF_WEEK, dayOfWeek);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_CAPACITY, capacity);
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_TEACHER, teacher);
        values.put(COLUMN_DESCRIPTION, description);

        // Update the row where class_id matches the given ID
        int rowsUpdated = db.update(TABLE_CLASSES, values, COLUMN_CLASS_ID + " = ?", new String[]{String.valueOf(ID)});
        db.close();

        // If at least one row was updated, return true; otherwise, return false
        return rowsUpdated > 0;
    }

    public boolean deleteClass(int classId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CLASSES, COLUMN_CLASS_ID + " = ?", new String[]{String.valueOf(classId)});
        db.close();
        return result > 0;
    }

    public Cursor getClassDetails(int classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_CLASS_ID, COLUMN_DAY_OF_WEEK, COLUMN_TIME, COLUMN_CAPACITY, COLUMN_DURATION, COLUMN_PRICE, COLUMN_TYPE, COLUMN_TEACHER, COLUMN_DESCRIPTION};
        String selection = COLUMN_CLASS_ID + " = ?";
        String[] selectionArgs = {String.valueOf(classId)};
        return db.query(TABLE_CLASSES, columns, selection, selectionArgs, null, null, null);
    }

    // Retrieve all classes
    public Cursor getAllClasses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_CLASSES, null, null, null, null, null, null);
    }

    // CRUD operations for Schedules

    // Add new schedule instance
    public long addSchedule(int classId, String date, String teacher, String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCHEDULE_CLASS_ID, classId);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_SCHEDULE_TEACHER, teacher);
        values.put(COLUMN_COMMENTS, comments);

        long id = db.insert(TABLE_SCHEDULES, null, values);
        db.close();
        return id;
    }

    // Retrieve schedules for a specific class
    public Cursor getSchedulesForClass(int classId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_SCHEDULE_CLASS_ID + " = ?";
        String[] selectionArgs = {String.valueOf(classId)};
        return db.query(TABLE_SCHEDULES, null, selection, selectionArgs, null, null, null);
    }

    // Update schedule
    public int updateSchedule(int scheduleId, String date, String teacher, String comments) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_SCHEDULE_TEACHER, teacher);
        values.put(COLUMN_COMMENTS, comments);

        String whereClause = COLUMN_SCHEDULE_ID + " = ?";
        String[] whereArgs = {String.valueOf(scheduleId)};
        int rowsUpdated = db.update(TABLE_SCHEDULES, values, whereClause, whereArgs);
        db.close();
        return rowsUpdated;
    }

    // Delete schedule
    public int deleteSchedule(int scheduleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_SCHEDULE_ID + " = ?";
        String[] whereArgs = {String.valueOf(scheduleId)};
        int rowsDeleted = db.delete(TABLE_SCHEDULES, whereClause, whereArgs);
        db.close();
        return rowsDeleted;
    }
}
