package in.myreminder.srb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myreminder";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String NOTES_NAME = "note_name";
    private static final String NOTES_PRIORITY = "priority";
    private static final String NOTES_DATE = "notes_date";
    private static final String NOTES_READ = "notes_read";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + NOTES_NAME + " TEXT," + NOTES_PRIORITY + " TEXT," + NOTES_DATE + " TEXT," + NOTES_READ + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        // Create tables again
        onCreate(sqLiteDatabase);
    }



}
