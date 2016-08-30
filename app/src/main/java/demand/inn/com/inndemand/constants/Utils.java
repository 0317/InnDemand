package demand.inn.com.inndemand.constants;


import android.provider.BaseColumns;

/**
 * All common methods are defined here as a public static.
 *
 */

public class Utils  {

    private Utils(){

    }

    public static class FeedEntry implements BaseColumns {

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

        //COLUMNS for Restaurant Items
        public static final String TABLE_RESTLISTS = "restlists";
        public static final String COLUMN_RRID = "id";
        public static final String COLUMN_RRNAME = "name";
        public static final String COLUMN_RRDESC = "desc";
        public static final String COLUMN_RRCATEGORY = "categoryname";
        public static final String COLUMN_RRAMOUNT = "amount";
        public static final String COLUMN_RRFOOD = "food";
        public static final String COLUMN_RRSUBCATEGORY = "subcategory";

        //COLUMNS for Bar Items
        public static final String TABLE_BAR = "barlists";
        public static final String COLUMN_BLID = "id";
        public static final String COLUMN_BLNAME = "name";
        public static final String COLUMN_BLDESC = "desc";
        public static final String COLUMN_BLCATEGORY = "categorybar";
        public static final String COLUMN_BLAMOUNT = "amount";
        public static final String COLUMN_BLFOOD = "food";
        public static final String COLUMN_BLSUBCATEGORY = "subcategory";

    }
}