package demand.inn.com.inndemand.welcome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by akash
 */

public class DBAdapter {

    public static final String KEY_ROWID = "rowid";
    public static final String KEY_CUSTOMER = "customer";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_SODA = "soda";
    public static final String KEY_WATER = "water";
    public static final String KEY_TOWEL = "towel";
    public static final String KEY_SOAP = "soap";
    public static final String KEY_SEARCH = "searchData";

    private static final String TAG = "DBAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public static final int DATABASE_VERSION = 1;
    private static final String FTS_VIRTUAL_TABLE = "innDemandInfo";
    public static final String DATABASE_NAME = "InnDemand.db";

    //Create a FTS3 Virtual Table for fast searches
    private static final String DATABASE_CREATE =
            "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE + " USING fts3(" +
                    KEY_CUSTOMER + "," +
                    KEY_NAME + "," +
                    KEY_ADDRESS + "," +
                    KEY_SODA + "," +
                    KEY_WATER + "," +
                    KEY_TOWEL + "," +
                    KEY_SOAP + "," +
                    KEY_SEARCH + "," +
                    " UNIQUE (" + KEY_CUSTOMER + "));";

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }
    }

    public DBAdapter(Context ctx){
        this.mCtx = ctx;
    }

    public DBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createDemand(String customer, String name, String address, String soda, String water, String towel, String soap){

        ContentValues initialValues = new ContentValues();
        String searchValue =     customer + " " +
                name + " " +
                address + " " +
                soda + " " +
                water + " " +
                towel + " " +
                soap;

        initialValues.put(KEY_CUSTOMER, customer);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_ADDRESS, address);
        initialValues.put(KEY_SODA, soda);
        initialValues.put(KEY_WATER, water);
        initialValues.put(KEY_TOWEL, towel);
        initialValues.put(KEY_SOAP, soap);
        initialValues.put(KEY_SEARCH, searchValue);

        return mDb.insert(FTS_VIRTUAL_TABLE, null, initialValues);
    }

    public Cursor searchDemand(String inputText) throws SQLException {

        Log.w(TAG, inputText);
        String query = "SELECT docid as _id," +
                KEY_CUSTOMER + "," +
                KEY_NAME + "," +
                KEY_ADDRESS + "," +
                KEY_SODA + "," +
                KEY_WATER + "," +
                KEY_TOWEL + "," +
                KEY_SOAP +
                " from " + FTS_VIRTUAL_TABLE +
                " where " +  KEY_SEARCH + " MATCH '" + inputText + "';";
        Log.w(TAG, query);
        Cursor mCursor = mDb.rawQuery(query,null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean deleteAllDemand(){
        int doneDelete = 0;
        doneDelete = mDb.delete(FTS_VIRTUAL_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }
}
