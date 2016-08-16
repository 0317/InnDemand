package demand.inn.com.inndemand.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bumptech.glide.util.Util;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.constants.BarlistData;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.HotelData;
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.constants.TabData;
import demand.inn.com.inndemand.constants.Utils;
import demand.inn.com.inndemand.model.AppetiserData;
import demand.inn.com.inndemand.model.BarDataModel;
import demand.inn.com.inndemand.model.ResturantDataModel;

/**
 * Created by akash
 */

public class DBHelper extends SQLiteOpenHelper {

    // If change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Inndemand";

    //Table created for Misc Info Inndemand
    public static final String TABLE_MISC = "misc";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "desc";

    //Table created for Restaurant List Inndemand
    public static final String TABLE_RESTAURANTLIST = "restaurantlist";
    public static final String COLUMN_RID = "id";
    public static final String COLUMN_RNAME = "name";
    public static final String COLUMN_RDESC = "desc";

    //Table created for Bar List Inndemand
    public static final String TABLE_BARLIST = "barlist";
    public static final String COLUMN_BID = "id";
    public static final String COLUMN_BNAME = "name";
    public static final String COLUMN_BDESC = "desc";

    //TABLE created for Restaurant items for Inndemand
    public static final String TABLE_RESTAURANTS = "restaurants";
    public static final String COLUMN_LID = "id";
    public static final String COLUMN_LNAME = "name";
    public static final String COLUMN_LDESC = "desc";
    public static final String COLUMN_LTYPE = "type";

    //Table created for Restaurant List Items for Inndemand
    public static final String TABLE_RESTAURANT_ITEMS = "restaurantitems";
//    public static final String COLUMN_AMOUNT = "amount";

    //Table created for Bar items for Inndemand
    public static final String TABLE_BAR = "bar";
    public static final String COLUMN_BLID = "id";
    public static final String COLUMN_BLNAME = "name";
    public static final String COLUMN_BLDESC = "desc";
    public static final String COLUMN_BLAMOUNT = "amount";
//    public static final String COLUMN_BRID= "rid";
//    public static final String COLUMN_BCtID= "cid";

    //Table created for Category(Restaurant) items for Inndemand
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CTID = "id";
    public static final String COLUMN_CTNAME = "name";
    public static final String COLUMN_CTTYPE = "type";

    //Table created for Cart for Inndemand
    public static final String TABLE_CART = "cart";
    public static final String COLUMN_CID = "id";
    public static final String COLUMN_CNAME = "name";
    public static final String COLUMN_CAMOUNT = "amount";

    public DBHelper(Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_MISC + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT,"
                + COLUMN_DESC + " TEXT"  + ")";

        String CREATE_TABLE_RESTAURANTLIST = "CREATE TABLE " + TABLE_RESTAURANTLIST + "("
                + COLUMN_RID + " INTEGER PRIMARY KEY," + COLUMN_RNAME + " TEXT,"
                + COLUMN_RDESC + " TEXT"  + ")";

        String CREATE_TABLE_BARLIST = "CREATE TABLE " + TABLE_BARLIST + "("
                + COLUMN_BID + " INTEGER PRIMARY KEY," + COLUMN_BNAME + " TEXT,"
                + COLUMN_BDESC + " TEXT"  + ")";

        String CREATE_TABLE_RESTAURANTS = "CREATE TABLE " + TABLE_RESTAURANTS + "("
                + COLUMN_LID + " INTEGER PRIMARY KEY," + COLUMN_LNAME + " TEXT,"
                + COLUMN_LDESC + " TEXT," + COLUMN_LTYPE + "TEXT"  + ")";

        String CREATE_TABLE_BAR = "CREATE TABLE " + TABLE_BAR + "("
                + COLUMN_BLID + " INTEGER PRIMARY KEY," + COLUMN_BLNAME + " TEXT,"
                + COLUMN_BLDESC + " TEXT," + COLUMN_BLAMOUNT + "TEXT"  + ")";

        String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
                + COLUMN_CTID + " INTEGER PRIMARY KEY," + COLUMN_CTNAME + " TEXT,"
                + COLUMN_CTTYPE + " TEXT"  + ")";

        String CREATE_TABLE_RESTAURANTITEMS = "CREATE TABLE " + TABLE_RESTAURANT_ITEMS + "("
                + Utils.COLUMN_AID + " INTEGER PRIMARY KEY," + Utils.COLUMN_ANAME + " TEXT,"
                + Utils.COLUMN_ADESC + "TEXT," + Utils.COLUMN_ACATEGORY + "TEXT"
               /* + COLUMN_AMOUNT + "TEXT" */ + ")";

        String CREATE_TABLE_CART= "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_CID + " INTEGER PRIMARY KEY," + COLUMN_CNAME + " TEXT,"
                + COLUMN_CAMOUNT + "TEXT"  + ")";

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_RESTAURANTLIST);
        db.execSQL(CREATE_TABLE_BARLIST);
        db.execSQL(CREATE_TABLE_RESTAURANTS);
        db.execSQL(CREATE_TABLE_RESTAURANTITEMS);
        db.execSQL(CREATE_TABLE_BAR);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_MISC);
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_RESTAURANTLIST);
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_BARLIST);
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_BAR);
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_RESTAURANT_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_CART);
        //create table again
        onCreate(db);
    }

    public void insertMiscData(HotelData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, cartData.getTitle());
        contentValues.put(COLUMN_DESC, cartData.getDesc());

        // Inserting Row
        db.insert(TABLE_MISC, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertRestData(ListData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RNAME, cartData.getTitle());
        contentValues.put(COLUMN_RDESC, cartData.getStatus());

        // Inserting Row
        db.insert(TABLE_RESTAURANTLIST, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertRestaurant(ResturantDataModel cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.COLUMN_ANAME, cartData.getTitle());
        contentValues.put(Utils.COLUMN_ADESC, cartData.getDescription());
        contentValues.put(Utils.COLUMN_ACATEGORY, cartData.getCategory());
//        contentValues.put(COLUMN_AMOUNT, cartData.getPrice());

        // Inserting Row
        db.insert(TABLE_RESTAURANT_ITEMS, null, contentValues);

        db.close(); // Closing database connection
    }


    public void insertBarData(BarlistData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BNAME, cartData.getTitle());
        contentValues.put(COLUMN_BDESC, cartData.getStatus());

        // Inserting Row
        db.insert(TABLE_BARLIST, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertRestaurantItsms(AppetiserData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LNAME, cartData.getName());
        contentValues.put(COLUMN_LDESC, cartData.getDescription());
        contentValues.put(COLUMN_LTYPE, cartData.getCategory());

        // Inserting Row
        db.insert(TABLE_RESTAURANTS, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertBarItems(BarDataModel cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BNAME, cartData.getTitle());
        contentValues.put(COLUMN_BDESC, cartData.getDescription());
        contentValues.put(COLUMN_BLAMOUNT, cartData.getPrice());

        // Inserting Row
        db.insert(TABLE_BAR, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertCategory(TabData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CTNAME, cartData.getName());
        contentValues.put(COLUMN_CTTYPE, cartData.getType());

        // Inserting Row
        db.insert(TABLE_CATEGORY, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertCart(CartData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CNAME, cartData.getName());
        contentValues.put(COLUMN_CAMOUNT, cartData.getPrice());

        // Inserting Row
        db.insert(TABLE_CART, null, contentValues);

        db.close(); // Closing database connection
    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from inndemand where id="+id+"", null);
        return cursor;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_MISC, TABLE_RESTAURANTLIST);
        return numRows;
    }

    public boolean updateData(Integer id, String name, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESC, email);

        db.update(TABLE_MISC, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteData (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MISC, "id = ? ", new String[] { Integer.toString(id) });
    }

    public List<HotelData> getAllData(){
        List<HotelData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MISC;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HotelData data = new HotelData();

                data.setTitle(cursor.getString(1));
                data.setDesc(cursor.getString(2));


                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public List<ListData> getAllDatas(){
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

                data.setTitle(cursor.getString(1));
                data.setStatus(cursor.getString(2));


                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }
    public List<BarlistData> getAllDatab(){
        List<BarlistData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BARLIST;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BarlistData data = new BarlistData();

                data.setTitle(cursor.getString(1));
                data.setStatus(cursor.getString(2));

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public List<AppetiserData> getAllDatarl(){
        List<AppetiserData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AppetiserData data = new AppetiserData();

                data.setName(cursor.getString(1));
                data.setDescription(cursor.getString(2));
                data.setCategory("starter");

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public List<BarDataModel> getAllDatabl(){
        List<BarDataModel> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BAR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BarDataModel data = new BarDataModel();

                data.setTitle(cursor.getString(1));
                data.setDescription(cursor.getString(2));
                data.setPrice(cursor.getString(3));

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public List<ResturantDataModel> getAllItems(){
        List<ResturantDataModel> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ResturantDataModel data = new ResturantDataModel();

                data.setName(cursor.getString(1));
                data.setDescription(cursor.getString(2));
                data.setCategory(cursor.getString(3));
//                data.setPrice(cursor.getString(4));

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public List<TabData> getAllCategory(){
        List<TabData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TabData data = new TabData();

                data.setName(cursor.getString(1));
                data.setType(cursor.getString(2));

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public List<CartData> getAllDataCart(){
        List<CartData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartData data = new CartData();

                data.setName(cursor.getString(1));
                data.setPrice(cursor.getString(2));

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }
}
