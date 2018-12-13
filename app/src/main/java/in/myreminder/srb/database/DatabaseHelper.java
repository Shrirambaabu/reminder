package in.myreminder.srb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import in.myreminder.srb.model.MyAlert;
import in.myreminder.srb.model.MyNotes;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "myreminder";

    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String NOTES_NAME = "note_name";
    private static final String NOTES_PRIORITY = "priority";
    private static final String NOTES_DATE = "notes_date";
    private static final String NOTES_READ = "notes_read";


    private static final String TABLE_ALERT = "reminder";
    private static final String ALERT_ID = "alert_id";
    private static final String ALERT_TIME = "alert_time";
    private static final String ALERT_READ = "alert_read";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + NOTES_NAME + " TEXT," + NOTES_PRIORITY + " TEXT," + NOTES_DATE + " TEXT," + NOTES_READ + " TEXT" + ")";
        String CREATE_ALERT_TABLE = "CREATE TABLE " + TABLE_ALERT + "(" + ALERT_ID + " INTEGER PRIMARY KEY," + ALERT_TIME + " TEXT," + ALERT_READ + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_ALERT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ALERT);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public long addNotes(MyNotes myNotes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTES_NAME, myNotes.getNotesName()); // Notes Name
        values.put(NOTES_PRIORITY, myNotes.getNotesPriority()); // Notes Priority
        values.put(NOTES_DATE, myNotes.getNotesDate()); // Notes date
        values.put(NOTES_READ, myNotes.getNotesRead()); // Notes Read

        // Inserting Row
        long rowInserted = db.insert(TABLE_NOTES, null, values);
        Log.e("SSId", "" + rowInserted);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return rowInserted;
    }
    public long addAlert(MyAlert myAlert) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ALERT_TIME, myAlert.getAlertTime()); // Notes Name
        values.put(ALERT_READ, myAlert.getAlertRead());

        // Inserting Row
        long rowInserted = db.insert(TABLE_ALERT, null, values);
        Log.e("SSId", "" + rowInserted);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return rowInserted;
    }

    public ArrayList<MyAlert> getAllAlerts() {
        ArrayList<MyAlert> myAlertArrayList = new ArrayList<MyAlert>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ALERT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyAlert myAlertModel = new MyAlert();
                myAlertModel.setAlertId(Integer.parseInt(cursor.getString(0)));
                myAlertModel.setAlertTime(cursor.getString(1));
                myAlertModel.setAlertRead(cursor.getString(2));
                myAlertArrayList.add(myAlertModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return myAlertArrayList;
    }

    public ArrayList<MyNotes> getAllNotes() {
        ArrayList<MyNotes> myNotesArrayList = new ArrayList<MyNotes>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyNotes myNotesModel = new MyNotes();
                myNotesModel.setNotesId(Integer.parseInt(cursor.getString(0)));
                myNotesModel.setNotesName(cursor.getString(1));
                myNotesModel.setNotesPriority(cursor.getString(2));
                myNotesModel.setNotesDate(cursor.getString(3));
                myNotesModel.setNotesRead(cursor.getString(4));
                myNotesArrayList.add(myNotesModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return myNotesArrayList;
    }

    public ArrayList<MyNotes> getParticularNote(String selectedDate) {
        ArrayList<MyNotes> myNotesArrayList = new ArrayList<MyNotes>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + NOTES_DATE + "=" + "\"" + selectedDate + "\"";
        Log.e("SS", "" + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyNotes myNotesModel = new MyNotes();
                myNotesModel.setNotesId(Integer.parseInt(cursor.getString(0)));
                myNotesModel.setNotesName(cursor.getString(1));
                myNotesModel.setNotesPriority(cursor.getString(2));
                myNotesModel.setNotesDate(cursor.getString(3));
                myNotesModel.setNotesRead(cursor.getString(4));
                myNotesArrayList.add(myNotesModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return myNotesArrayList;
    }

    public int updateNotes(int notesId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTES_READ, status);// Notes Read

        // updating row
        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(notesId)});
    }
    public int updateAlert(int alertId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ALERT_READ, status);// Notes Read

        // updating row
        return db.update(TABLE_ALERT, values, ALERT_ID + " = ?",
                new String[]{String.valueOf(alertId)});
    }

    public void deleteNotes(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{id});
        db.close();
    }

    public void deleteAlert(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALERT, ALERT_ID + " = ?",
                new String[]{id});
        db.close();
    }

}
