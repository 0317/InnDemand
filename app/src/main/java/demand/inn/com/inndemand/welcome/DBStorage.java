package demand.inn.com.inndemand.welcome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.model.ResturantDataModel;

/**
 * Created by akash
 */

public class DBStorage extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "innDemand";

    // Table Names
    private static final String TABLE_HOTEL = "hotel";
    private static final String TABLE_RESTAURANT = "restaurant";
    private static final String TABLE_BAR = "bar";
    private static final String TABLE_SPA = "spa";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    //Restaurant Table - Column names
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_RUPEES = "rupees";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_LANGUAGE = "language";

    //Bar Table - Column names
    public static final String COLUMN_BNAME = "bname";
    public static final String COLUMN_BDESC = "bdesc";
    public static final String COLUMN_BRUPEES = "brupees";
    public static final String COLUMN_BTYPE = "btype";
    public static final String COLUMN_BLANGUAGE = "blanguage";

    //Spa Table - Column names
    public static final String COLUMN_SNAME = "sname";
    public static final String COLUMN_SDESC = "sdesc";
    public static final String COLUMN_SRUPEES = "srupees";
    public static final String COLUMN_STYPE = "stype";
    public static final String COLUMN_SLANGUAGE = "slanguage";

    // Table Create Statements
    // Restaurant table create statement
    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE " + TABLE_RESTAURANT  +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_DESC +
            " TEXT," + COLUMN_RUPEES + " INTEGER," + COLUMN_TYPE + " INTEGER," +COLUMN_LANGUAGE +
            " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

    //Bar table create statement
    private static final String CREATE_TABLE_BAR = "CREATE TABLE " + TABLE_BAR + "(" + KEY_ID +
            " INTEGER PRIMARY KEY," + COLUMN_BNAME + " TEXT," + COLUMN_BDESC + " TEXT," +
            COLUMN_BRUPEES + " INTEGER," + COLUMN_BTYPE + " INTEGER," + COLUMN_BLANGUAGE +
            " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

    //Spa table create statement
    private static final String CREATE_TABLE_SPA = "CREATE TABLE " + TABLE_SPA + "(" + KEY_ID +
            " INTEGER PRIMARY KEY," + COLUMN_SNAME + " TEXT," + COLUMN_SDESC + " TEXT," +
            COLUMN_SRUPEES + " INTEGER," + COLUMN_STYPE + " INTEGER," + COLUMN_SLANGUAGE +
            " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

    public DBStorage(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_BAR);
        db.execSQL(CREATE_TABLE_SPA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPA);

        onCreate(db);
    }

    public void insertData(ResturantDataModel model){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, model.getName());
        contentValues.put(COLUMN_DESC, model.getDescription());
        contentValues.put(COLUMN_RUPEES, model.getPrice());
        contentValues.put(COLUMN_TYPE, model.getFood());

        // Inserting Row
        db.insert(TABLE_RESTAURANT, null, contentValues);
        db.close(); // Closing database connection
    }

    public List<ResturantDataModel> getAllData(){
        List<ResturantDataModel> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ResturantDataModel data = new ResturantDataModel();

                data.setName(cursor.getString(1));
                data.setDescription(cursor.getString(2));
                data.setPrice(cursor.getString(3));
                data.setFood(cursor.getString(4));

                // Adding data to list
                list.add(data);

            }while (cursor.moveToNext());
        }
        return list;
    }
}
