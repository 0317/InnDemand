package demand.inn.com.inndemand.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.constants.ListData;

/**
 * Created by akash
 */
public class DBHelper extends SQLiteOpenHelper {

    // If change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "innDemand.dbs";

    //Tables name
    public static final String TABLE_RESTAURANTLIST = "restaurantlist";

    //RestaurantList Table - Column names
    public static final String COLUMN_RID = "id";
    public static final String COLUMN_RNAME = "rname";
    public static final String COLUMN_RSTATUS = "rstatus";


    public DBHelper(Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // creating required tables
        // Table Create Statements
        // RestaurantList table create statement
        String CREATE_TABLE_RESTAURANT = "CREATE TABLE " + TABLE_RESTAURANTLIST +
                "(" + COLUMN_RID + "TEXT, " + COLUMN_RNAME + "TEXT" + COLUMN_RSTATUS + "TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_RESTAURANT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO Auto-generated method stub
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+ TABLE_RESTAURANTLIST);

        onCreate(sqLiteDatabase);
    }

    public void insertData(ListData listData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RID, listData.getId());
        contentValues.put(COLUMN_RNAME, listData.getTitle());
        contentValues.put(COLUMN_RSTATUS, listData.getStatus());

        // Inserting Row
        db.insert(TABLE_RESTAURANTLIST, null, contentValues);

        db.close(); // Closing database connection
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from inndemand where id="+id+"", null );
        return cursor;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_RESTAURANTLIST);
        return numRows;
    }

    public boolean updateData(Integer id, String name, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RID, id);
        contentValues.put(COLUMN_RNAME, name);
        contentValues.put(COLUMN_RSTATUS, status);
        db.update(TABLE_RESTAURANTLIST, contentValues, "id = ? ", new String[]
                { Integer.toString(id) } );
        return true;
    }

    public Integer deleteData (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RESTAURANTLIST, "id = ? ", new String[] { Integer.toString(id) });
    }

    public List<ListData> getAllData() {
        List<ListData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANTLIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ListData data = new ListData();

                data.setId(cursor.getString(1));
                data.setTitle(cursor.getString(2));
                data.setStatus(cursor.getString(3));

                // Adding data to list
                dataList.add(data);

            } while (cursor.moveToNext());
        }
        return dataList;
    }
}
