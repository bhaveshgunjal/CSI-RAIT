package google.bhavesh.csi.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import google.bhavesh.csi.PojoData.Workshop;

/**
 * Created by Archana on 3/11/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";


    private static final String TABLE_WORKSHOPS = "contacts";
Context context;

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";

    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKSHOPS_TABLE = "CREATE TABLE " + TABLE_WORKSHOPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_WORKSHOPS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKSHOPS);

        // Create tables again
        onCreate(db);

    }
    public void addWorkshop(Workshop workshop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, workshop.getName()); //  Name
        values.put(KEY_DESCRIPTION, workshop.getDescription());//description

        // Inserting Row
        db.insert(TABLE_WORKSHOPS, null, values);
        db.close(); // Closing database connection
    }
    public Workshop getWorkshop(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WORKSHOPS, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESCRIPTION }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Workshop workshop = new Workshop(cursor.getString(0),
                cursor.getString(1));
        // return contact
        return workshop;
    }
    public List<Workshop> getAllWorkshops() {

        List<Workshop> workshopsList = new ArrayList<Workshop>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WORKSHOPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Workshop workshop = new Workshop ("Ravi", "9100000000");
                workshop.setID(Integer.parseInt(cursor.getString(0)));
                workshop.setName(cursor.getString(1));
                workshop.setDescription(cursor.getString(2));
                // Adding contact to list
                workshopsList.add(workshop);
            } while (cursor.moveToNext());
        }

        // return contact list
        return workshopsList;
    }



}
