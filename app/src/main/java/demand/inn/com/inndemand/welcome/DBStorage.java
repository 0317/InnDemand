package demand.inn.com.inndemand.welcome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.constants.CartData;

/**
 * Created by akash
 */

public class DBStorage extends SQLiteOpenHelper {

    // If change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "InnDemand.db";
    public static final String TABLE_NAME = "restaurant";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_DESC = "Desc";
    public static final String COLUMN_PRICE = "Price";
    public static final String COLUMN_TYPE = "Type";


    public DBStorage(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_DESC  +
                "TEXT," + COLUMN_PRICE + "TEXT," + COLUMN_TYPE + "TEXT"+")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        //create table again
        onCreate(db);
    }

    public void insertData(CartData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, cartData.getName());
        contentValues.put(COLUMN_DESC, cartData.getDesc());
        contentValues.put(COLUMN_PRICE, cartData.getPrice());
        contentValues.put(COLUMN_TYPE, cartData.getType());

        // Inserting Row
        db.insert(TABLE_NAME, null, contentValues);

        db.close(); // Closing database connection
    }

    public boolean updateData(Integer id, String name, String email, String price, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESC, email);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_TYPE, type);
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteData (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
    }

    public List<CartData> getAllData(){
        List<CartData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartData data = new CartData();

                data.setName(cursor.getString(1));
                data.setDesc(cursor.getString(2));
                data.setRupees(cursor.getString(3));

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }
}
