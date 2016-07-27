package demand.inn.com.inndemand.welcome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.model.ResturantDataModel;

/**
 * Created by akash on 19/7/16.
 */
public class DBList extends SQLiteOpenHelper {

    // If change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Inndemand.dbs";
    public static final String TABLE_NAME = "inndemands";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_RUPEES = "rupees";
    public static final String COLUMN_SUBCATEGORY = "subcategory";
    public static final String COLUMN_FOOD = "food";
    public static final String COLUMN_AVERAGE = "average";

    private HashMap hp;

    public DBList(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"
                + COLUMN_DESC + " TEXT," +COLUMN_RUPEES + " TEXT," + COLUMN_SUBCATEGORY + " TEXT,"
                + COLUMN_FOOD + " TEXT," + COLUMN_AVERAGE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        //create table again
        onCreate(db);
    }

    public void insertData(ResturantDataModel cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, cartData.getName());
        contentValues.put(COLUMN_DESC, cartData.getDescription());
        contentValues.put(COLUMN_RUPEES, cartData.getPrice());
        contentValues.put(COLUMN_SUBCATEGORY, cartData.getSubcategory());
        contentValues.put(COLUMN_FOOD, cartData.getFood());
        contentValues.put(COLUMN_AVERAGE, cartData.getRating());

        // Inserting Row
        db.insert(TABLE_NAME, null, contentValues);

        db.close(); // Closing database connection
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from inndemand where id="+id+"", null );
        return cursor;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateData(Integer id, String name, String email, String price, String subcategory,
                    String food, String average)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESC, email);
        contentValues.put(COLUMN_RUPEES, price);
        contentValues.put(COLUMN_SUBCATEGORY, subcategory);
        contentValues.put(COLUMN_FOOD, food);
        contentValues.put(COLUMN_AVERAGE, average);
        db.update(TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteData (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
    }

    public List<ResturantDataModel> getAllData(){
        List<ResturantDataModel> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

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
                data.setSubcategory(cursor.getString(4));
                data.setFood(cursor.getString(5));
                data.setRating(cursor.getString(6));

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }
}
