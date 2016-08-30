package demand.inn.com.inndemand.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import demand.inn.com.inndemand.constants.BarlistData;
import demand.inn.com.inndemand.constants.CartData;
import demand.inn.com.inndemand.constants.HotelData;
import demand.inn.com.inndemand.constants.ListData;
import demand.inn.com.inndemand.constants.TabData;
import demand.inn.com.inndemand.constants.Translate;
import demand.inn.com.inndemand.constants.Utils;
import demand.inn.com.inndemand.model.AppetiserData;
import demand.inn.com.inndemand.model.BarDataModel;

/**
 * Created by akash
 */

public class DBHelper extends SQLiteOpenHelper {

    // If change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "Inndemand";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    /*
     * Creating Different Tables as per requirement
     * Getting Table Name/Columns from Utils.FeedEntry Class
     */
    String CREATE_TABLE = "CREATE TABLE " + Utils.FeedEntry.TABLE_MISC + "("
            + Utils.FeedEntry.COLUMN_ID + " INTEGER PRIMARY KEY,"
            + Utils.FeedEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_DESC + TEXT_TYPE  + ")";

    String CREATE_TABLE_RESTAURANTLIST = "CREATE TABLE "
            + Utils.FeedEntry.TABLE_RESTAURANTLIST + "("
            + Utils.FeedEntry.COLUMN_RID + " INTEGER PRIMARY KEY,"
            + Utils.FeedEntry.COLUMN_RNAME + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_RDESC + TEXT_TYPE + ")";

    String CREATE_TABLE_BARLIST = "CREATE TABLE " + Utils.FeedEntry.TABLE_BARLIST + "("
            + Utils.FeedEntry.COLUMN_BID + " INTEGER PRIMARY KEY,"
            + Utils.FeedEntry.COLUMN_BNAME + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_BDESC + TEXT_TYPE  + ")";

    String CREATE_TABLE_RESTAURANTS = "CREATE TABLE " + Utils.FeedEntry.TABLE_RESTLISTS + "("
            + Utils.FeedEntry.COLUMN_RRID + " INTEGER PRIMARY KEY,"
            + Utils.FeedEntry.COLUMN_RRNAME + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_RRDESC + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_RRCATEGORY + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_RRAMOUNT + TEXT_TYPE +COMMA_SEP
            + Utils.FeedEntry.COLUMN_RRFOOD + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_RRSUBCATEGORY + TEXT_TYPE + ")";

    String CREATE_TABLE_BAR = "CREATE TABLE " + Utils.FeedEntry.TABLE_BAR + "("
            + Utils.FeedEntry.COLUMN_BLID + " INTEGER PRIMARY KEY,"
            + Utils.FeedEntry.COLUMN_BLNAME + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_BLDESC + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_BLCATEGORY + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_BLAMOUNT + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_BLFOOD + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_BLSUBCATEGORY + TEXT_TYPE + ")";

    String CREATE_TABLE_CATEGORY = "CREATE TABLE " + Utils.FeedEntry.TABLE_CATEGORY + "("
            + Utils.FeedEntry.COLUMN_CTID + " INTEGER PRIMARY KEY,"
            + Utils.FeedEntry.COLUMN_CTNAME + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_CTTYPE + TEXT_TYPE  + ")";

    String CREATE_TABLE_CATEGORY_BAR = "CREATE TABLE " + Utils.FeedEntry.TABLE_BRCATEGORY + "("
            + Utils.FeedEntry.COLUMN_BRTID + " INTEGER PRIMARY KEY,"
            + Utils.FeedEntry.COLUMN_BRNAME + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_BRTYPE + TEXT_TYPE  + ")";

    String CREATE_TABLE_CART= "CREATE TABLE " + Utils.FeedEntry.TABLE_CART + "("
            + Utils.FeedEntry.COLUMN_CID + " INTEGER PRIMARY KEY,"
            + Utils.FeedEntry.COLUMN_CNAME + TEXT_TYPE + COMMA_SEP
            + Utils.FeedEntry.COLUMN_CAMOUNT + TEXT_TYPE  + ")";

    //Database Constructor
    public DBHelper(Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_RESTAURANTLIST);
        db.execSQL(CREATE_TABLE_BARLIST);
        db.execSQL(CREATE_TABLE_RESTAURANTS);
        db.execSQL(CREATE_TABLE_BAR);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_CATEGORY_BAR);
        db.execSQL(CREATE_TABLE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ Utils.FeedEntry.TABLE_MISC);
        db.execSQL("DROP TABLE IF EXISTS "+ Utils.FeedEntry.TABLE_RESTAURANTLIST);
        db.execSQL("DROP TABLE IF EXISTS "+ Utils.FeedEntry.TABLE_BARLIST);
        db.execSQL("DROP TABLE IF EXISTS "+ Utils.FeedEntry.TABLE_RESTLISTS);
        db.execSQL("DROP TABLE IF EXISTS "+ Utils.FeedEntry.TABLE_BAR);
        db.execSQL("DROP TABLE IF EXISTS "+ Utils.FeedEntry.TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS "+ Utils.FeedEntry.TABLE_BRCATEGORY);
        db.execSQL("DROP TABLE IF EXISTS "+ Utils.FeedEntry.TABLE_CART);
        //create table again
        onCreate(db);
    }

    public void insertMiscData(HotelData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_NAME, cartData.getTitle());
        contentValues.put(Utils.FeedEntry.COLUMN_DESC, cartData.getDesc());

        // Inserting Row
        db.insert(Utils.FeedEntry.TABLE_MISC, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertRestData(ListData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_RNAME, cartData.getTitle());
        contentValues.put(Utils.FeedEntry.COLUMN_RDESC, cartData.getStatus());

        // Inserting Row
        db.insert(Utils.FeedEntry.TABLE_RESTAURANTLIST, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertBarData(BarlistData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_BNAME, cartData.getTitle());
        contentValues.put(Utils.FeedEntry.COLUMN_BDESC, cartData.getStatus());

        // Inserting Row
        db.insert(Utils.FeedEntry.TABLE_BARLIST, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertRestaurantItsms(AppetiserData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_RRID, cartData.getId());
        contentValues.put(Utils.FeedEntry.COLUMN_RRNAME, cartData.getName());
        contentValues.put(Utils.FeedEntry.COLUMN_RRDESC, cartData.getDescription());
        contentValues.put(Utils.FeedEntry.COLUMN_RRCATEGORY, cartData.getCategory());
        contentValues.put(Utils.FeedEntry.COLUMN_RRAMOUNT, cartData.getPrice());

        // Inserting Row
        db.insert(Utils.FeedEntry.TABLE_RESTLISTS, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertBarItems(BarDataModel cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_BNAME, cartData.getTitle());
        contentValues.put(Utils.FeedEntry.COLUMN_BDESC, cartData.getDescription());
        contentValues.put(Utils.FeedEntry.COLUMN_BLCATEGORY, cartData.getCategory());
        contentValues.put(Utils.FeedEntry.COLUMN_BLAMOUNT, cartData.getPrice());
        contentValues.put(Utils.FeedEntry.COLUMN_BLFOOD, cartData.getFood());
        contentValues.put(Utils.FeedEntry.COLUMN_BLSUBCATEGORY, cartData.getSubcategory());

        // Inserting Row
        db.insert(Utils.FeedEntry.TABLE_BAR, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertCategory(TabData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_CTNAME, cartData.getName());
        contentValues.put(Utils.FeedEntry.COLUMN_CTTYPE, cartData.getType());

        // Inserting Row
        db.insert(Utils.FeedEntry.TABLE_CATEGORY, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertBarCategory(Translate cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_BRNAME, cartData.getName());
        contentValues.put(Utils.FeedEntry.COLUMN_BRTYPE, cartData.getType());

        // Inserting Row
        db.insert(Utils.FeedEntry.TABLE_BRCATEGORY, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertCart(CartData cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_CNAME, cartData.getName());
        contentValues.put(Utils.FeedEntry.COLUMN_CAMOUNT, cartData.getPrice());

        // Inserting Row
        db.insert(Utils.FeedEntry.TABLE_CART, null, contentValues);

        db.close(); // Closing database connection
    }

    public Cursor getData(int title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from restaurantlistss where categoryname="+title,
                null);
        return cursor;
    }

    public Cursor getDetail(String title){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Utils.FeedEntry.TABLE_RESTLISTS, null,
                Utils.FeedEntry.COLUMN_RRCATEGORY + "=?", new String[]{title},
                null, null, null, null);
        return cursor;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Utils.FeedEntry.TABLE_MISC,
                Utils.FeedEntry.TABLE_RESTAURANTLIST);
        return numRows;
    }

    public boolean updateData(Integer id, String name, String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.FeedEntry.COLUMN_NAME, name);
        contentValues.put(Utils.FeedEntry.COLUMN_DESC, email);

        db.update(Utils.FeedEntry.TABLE_MISC, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteData (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Utils.FeedEntry.TABLE_MISC, "id = ? ", new String[] { Integer.toString(id) });
    }

    public List<HotelData> getAllData(){
        List<HotelData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_MISC;

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
        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_RESTAURANTLIST;

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
        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_BARLIST;

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

    public AppetiserData getAllDatarls(String title){
        AppetiserData data = new AppetiserData();

        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_RESTLISTS
                + " where categoryname = '" + title + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                data.setId(cursor.getString(1));
                data.setName(cursor.getString(2));
                data.setDescription(cursor.getString(3));
                data.setCategory(cursor.getString(4));
                data.setPrice(cursor.getString(5));

                // Adding data to list

            } while (cursor.moveToNext());
        }

        return data;
    }

    public List<AppetiserData> getAllDatarl(){
        List<AppetiserData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_RESTLISTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AppetiserData data = new AppetiserData();

                data.setId(cursor.getString(1));
                data.setName(cursor.getString(2));
                data.setDescription(cursor.getString(3));
                data.setCategory(cursor.getString(4));
                data.setPrice(cursor.getString(5));

                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public List<BarDataModel> getAllBarItems(){
        List<BarDataModel> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_BAR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BarDataModel data = new BarDataModel();

                data.setTitle(cursor.getString(1));
                data.setDescription(cursor.getString(2));
                data.setCategory(cursor.getString(3));
                data.setPrice(cursor.getString(4));
                data.setFood(cursor.getString(5));
                data.setSubcategory(cursor.getString(6));


                // Adding data to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        return dataList;
    }

    public List<TabData> getAllCategory(){
        List<TabData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_CATEGORY;

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

    public List<Translate> getAllBarCategory(){
        List<Translate> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_BRCATEGORY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Translate data = new Translate();

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
        String selectQuery = "SELECT  * FROM " + Utils.FeedEntry.TABLE_CART;

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
