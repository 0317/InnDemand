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
import demand.inn.com.inndemand.model.AppetiserData;
import demand.inn.com.inndemand.model.BarDataModel;

/**
 * Created by akash
 */

public class DBHelper extends SQLiteOpenHelper {

    // If change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 10;
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

/*    //Table created for Restaurant items for Inndemand
    public static final String TABLE_RESTLIST = "restaurantlists";
    public static final String COLUMN_RRID = "id";
    public static final String COLUMN_RRNAME = "name";
    public static final String COLUMN_RRDESC = "descr";
    public static final String COLUMN_RRCATEGORY = "categoryname";
    public static final String COLUMN_RRAMOUNT = "amount";*/

    //Table created for Restaurant items for Inndemand
    public static final String TABLE_RESTLISTS = "restaurantlistss";
    public static final String COLUMN_RRID = "id";
    public static final String COLUMN_RRNAME = "name";
    public static final String COLUMN_RRDESC = "descr";
    public static final String COLUMN_RRCATEGORY = "categoryname";
    public static final String COLUMN_RRAMOUNT = "amount";
    public static final String COLUMN_RRTABS = "tabdata";

    //Table created for Bar items for Inndemand
    public static final String TABLE_BAR = "bar";
    public static final String COLUMN_BLID = "id";
    public static final String COLUMN_BLNAME = "name";
    public static final String COLUMN_BLDESC = "desc";
    public static final String COLUMN_BLCATEGORY = "category";
    public static final String COLUMN_BLAMOUNT = "amount";
    public static final String COLUMN_BLFOOD = "food";
    public static final String COLUMN_BLSUBCATEGORY = "subcategory";
//    public static final String COLUMN_BRID= "rid";
//    public static final String COLUMN_BCtID= "cid";

    //Table created for Category(Restaurant) items for Inndemand
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CTID = "id";
    public static final String COLUMN_CTNAME = "name";
    public static final String COLUMN_CTTYPE = "type";

    //Table created for Category(Bar) items for Inndemand
    public static final String TABLE_BRCATEGORY = "barcategory";
    public static final String COLUMN_BRTID = "id";
    public static final String COLUMN_BRNAME = "name";
    public static final String COLUMN_BRTYPE = "type";

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

        String CREATE_TABLE_RESTAURANTS = "CREATE TABLE " + TABLE_RESTLISTS + "("
                + COLUMN_RRID + " INTEGER PRIMARY KEY," + COLUMN_RRNAME + " TEXT,"
                + COLUMN_RRDESC + " TEXT," + COLUMN_RRCATEGORY + " TEXT,"
                + COLUMN_RRAMOUNT + " TEXT," + COLUMN_RRTABS + " TEXT" + ")";

        Log.d("RestaurantDetailsList", "Checks: "+CREATE_TABLE_RESTAURANTS);

        String CREATE_TABLE_BAR = "CREATE TABLE " + TABLE_BAR + "("
                + COLUMN_BLID + " INTEGER PRIMARY KEY," + COLUMN_BLNAME + " TEXT,"
                + COLUMN_BLDESC + " TEXT," + COLUMN_BLCATEGORY + " TEXT,"
                + COLUMN_BLAMOUNT + " TEXT," + COLUMN_BLFOOD + " TEXT,"
                + COLUMN_BLSUBCATEGORY + " TEXT" + ")";

        String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
                + COLUMN_CTID + " INTEGER PRIMARY KEY," + COLUMN_CTNAME + " TEXT,"
                + COLUMN_CTTYPE + " TEXT"  + ")";

        String CREATE_TABLE_CATEGORY_BAR = "CREATE TABLE " + TABLE_BRCATEGORY + "("
                + COLUMN_BRTID + " INTEGER PRIMARY KEY," + COLUMN_BRNAME + " TEXT,"
                + COLUMN_BRTYPE + " TEXT"  + ")";

        String CREATE_TABLE_CART= "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_CID + " INTEGER PRIMARY KEY," + COLUMN_CNAME + " TEXT,"
                + COLUMN_CAMOUNT + " TEXT"  + ")";

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
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MISC);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_RESTAURANTLIST);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BARLIST);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_RESTLISTS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BAR);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BRCATEGORY);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CART);
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
        contentValues.put(COLUMN_RRID, cartData.getId());
        contentValues.put(COLUMN_RRNAME, cartData.getName());
        contentValues.put(COLUMN_RRDESC, cartData.getDescription());
        contentValues.put(COLUMN_RRCATEGORY, cartData.getCategory());
        contentValues.put(COLUMN_RRAMOUNT, cartData.getPrice());
        contentValues.put(COLUMN_RRTABS, cartData.getCategory());

        // Inserting Row
        db.insert(TABLE_RESTLISTS, null, contentValues);

        db.close(); // Closing database connection
    }

    public void insertBarItems(BarDataModel cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BNAME, cartData.getTitle());
        contentValues.put(COLUMN_BDESC, cartData.getDescription());
        contentValues.put(COLUMN_BLCATEGORY, cartData.getCategory());
        contentValues.put(COLUMN_BLAMOUNT, cartData.getPrice());
        contentValues.put(COLUMN_BLFOOD, cartData.getFood());
        contentValues.put(COLUMN_BLSUBCATEGORY, cartData.getSubcategory());

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

    public void insertBarCategory(Translate cartData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BRNAME, cartData.getName());
        contentValues.put(COLUMN_BRTYPE, cartData.getType());

        // Inserting Row
        db.insert(TABLE_BRCATEGORY, null, contentValues);

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

    public Cursor getData(int title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from restaurantlistss where categoryname="+title,
                null);
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

    public AppetiserData getAllDatarls(String title){
        AppetiserData data = new AppetiserData();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTLISTS + " where categoryname = '"
                + title + "'";

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

   /* public AppetiserData getAllDatarls(String title) {
        AppetiserData data = new AppetiserData();



        *//*SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_RESTLISTS, null, COLUMN_RRCATEGORY + " = ?",
                new String[]{title}, null, null, null);
        cursor.moveToFirst();
*//*
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_RESTLISTS, null, "categoryname=?",
                new String[]{title}, null, null, null);
        cursor.moveToFirst();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                data.setName(cursor.getString(1));
                data.setDescription(cursor.getString(2));
                data.setCategory(cursor.getString(3));
                data.setPrice(cursor.getString(4));
                data.setSubcategory(cursor.getString(5));

                // Adding data to list

            } while (cursor.moveToNext());
        }

        return data;
    }
*/
    public List<AppetiserData> getAllDatarl(){
        List<AppetiserData> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESTLISTS;

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

    public List<Translate> getAllBarCategory(){
        List<Translate> dataList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_BRCATEGORY;

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
