/**
 *
 */
package demand.inn.com.inndemand.constants;

/**
 *
 */
public class JsonKey {

    //Key : Common Key
    public static final String META_DATA = "metadata";
    public static final String DATA = "data";
    public static final String MESSAGE = "messages";
    public static final String VALIDATION = "validate";
    public static final String ERROR = "error";
    public static final String SUCCESS = "success";
    public static final String SESSION = "session";
    public static final String ID = "id";

    //Key : Parse Menu Item (@ method : parseItemDetail)
    public static final String LEVEL1_MENU_ITEM = "level1MenuItem";
    public static final String LEVEL2_MENU_ITEM = "level2MenuItem";
    public static final String LEVEL3_MENU_ITEM = "level3MenuItem";

    public static final String ATTRIBUTES = "@attributes";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String ICON_URL = "android_icon";

    //Key : Parse CatalogList Items (@ method : parseCatalog)
    public static final String ITEMS = "items";
    public static final String TOTAL = "total";

    public static final String BRAND = "brand";
    public static final String IS_BUNDLE_PRODUCT = "is_bundle_product";
    public static final String GENDER = "gender";
    public static final String lAST_NAME = "last_name";
    public static final String FIRST_NAME = "first_name";
    public static final String IMAGE = "image";
    public static final String MAX_SAVING_PERCENTAGE = "max_saving_percentage";
    public static final String ITEM_NAME = "name";
    public static final String PRICE = "price";
    public static final String SKU = "sku";
    public static final String SPECIAL_PRICE = "special_price";
    public static final String STATUS = "status";
    public static final String BUNDLE_DETAILS = "bundle_details";
    public static final String HTML = "html";
    public static final String TEXT = "text";

    //Key : Parse Item Details ()

    public static final String MAX_PRICE = "max_price";
    public static final String ACTIVATED_AT = "activated_at";
    public static final String ATTRIBUTE_SET_ID = "attribute_set_id";
    public static final String BRAND_IMAGE = "brand_image";
    public static final String ID_CATALOG_CONFIG = "id_catalog_config";
    public static final String RATINGS_SINGLE = "ratings_single";
    public static final String RATINGS_TOTAL = "ratings_total";
    public static final String RATINGS_URL = "ratings_url";
    public static final String IMAGE_LIST = "image_list";
    public static final String ITEM_ATTRIBUTES = "attributes";

    public static final String SIZE_CHART = "sizechart";
    public static final String CHART = "chart";
    public static final String ONLY_IMAGE_NO_TABLE = "only_image_no_table";
    public static final String SIZES = "sizes";
    public static final String ROWS = "rows";

    public static final String SIMPLES = "simples";
    public static final String SIMPLES_COUNT = "simples_count";
    public static final String KEY = "key";
    public static final String VARIATIONS = "variations";
    public static final String BREADCRUMB = "breadcrumb";
    public static final String SHORTLIST_SIZE_KEY = "size_key";

    public static final String FACETS = "facets";
    public static final String FACET_CATEGORY = "facet_category";
    public static final String FACET_BRAND = "facet_brand";
    public static final String CURRENT_ACTIVE_BRAND = "current_active_brand";
    public static final String MAPRES = "mapRes";

    public static final String SORTED_BY = "sortedBy";
    public static final String SORT = "sort";
    public static final String SORT_DIRECTION = "sortDirection";
    public static final String SORT_OPTIONS = "sortOptions";

    public static final String SEGMENTS = "segments";
    public static final String SEGMENT = "segment";
    public static final String H_1_TAG_TEXT = "getH1TagText";

    // constants for the card fragments
    public static final String CARD_NUMBER = "ccnum";
    public static final String CARD_CVV = "ccvv";
    public static final String CARD_EXPIRY_MONTH = "ccexpmon";
    public static final String CARD_EXPIRY_YEAR = "ccexpyr";
    public static final String CARD_STORE = "store_cc";
    public static final String CARD_ISSUER_BANK_NAME = "issuer_bank_name";
    public static final String CARD_TYPE = "card-type";
    public static final String CARD_NAME = "ccname";

    // express checkout constants
    public static final String EXPRESS_CHECKOUT_SESSION = "udf4";
    public static final String EXPRESS_CHECKOUT_CVVV = "ccvv";
    public static final String EXPRESS_CHECKOUT_TOKEN = "token";
    public static final String CACHE_EXPIRY_KEY = "cache-control-app";


    public static final String thanksJSON = "{\n" +
            "\n" +
            "    \"success\": true,\n" +
            "    \"messages\": {\n" +
            "        \"success\": {\n" +
            "            \"msg_thank_you\": \"For placing order with jabong.com!\",\n" +
            "            \"msg_order_nr\": \"Your Order Number is 140207673725833\",\n" +
            "            \"msg_reference\": \"Please note the order number for your reference.\",\n" +
            "            \"msg_next_steps_html\": \"<html> <head> <style>.fl {float: left;}.bodyBg {background-color: #E7FAFF;}h3 {font-family: 'Helvetica'; font-size: 14px;}p {font-family: 'Helvetica'; font-size: 12px;}.nextStepsUlLi {font-family: Helvetica; font-size: 12px;}</style> </head> <body class=\\\"bodyBg fl\\\"><div class=\\\"fl\\\"> <h3>Note for Cash on Delivery orders</h3><p>Our Customer Care Expert will call you on (mobile no.) <br/>within 24 hours to confirm your order before it gets <br />dispatched.</p> <h3 class=\\\"nextStepsUl\\\">Next Steps</h3><ul><li class=\\\"nextStepsUlLi\\\">We have sent an order confirmation email at <br /> <a href=\\\"#\\\">tausif@jabong.com</a><li class=\\\"nextStepsUlLi\\\">Login to <a href=\\\"#\\\">My Account</a> to track your orders.<li class=\\\"nextStepsUlLi\\\">In case of further clarification you can write us back at <br/> care@jabong.com, refer to our <a href=\\\"#\\\">Order FAQs</a> section or <br /> call us at 0124-6128000 </ul></div></body></html>\",\n" +
            "            \"order_summary_block\": {\n" +
            "                \"order_summary_block_title\": \"YOUR ORDER SUMMARY\",\n" +
            "                \"sub_total\": {\n" +
            "                    \"label\": \"SUB TOTAL\",\n" +
            "                    \"value\": 2298\n" +
            "                },\n" +
            "                \"discount\": {\n" +
            "                    \"label\": \"DISCOUNT\",\n" +
            "                    \"value\": 0\n" +
            "                },\n" +
            "                \"special_discount\": {\n" +
            "                    \"label\": \"SPECIAL DISCOUNT\",\n" +
            "                    \"value\": 0\n" +
            "                },\n" +
            "                \"jabong_credits\": {\n" +
            "                    \"label\": \"JABONG CREDITS\",\n" +
            "                    \"value\": 0\n" +
            "                },\n" +
            "                \"shipping_charges\": {\n" +
            "                    \"label\": \"SHIPPING CHARGES\",\n" +
            "                    \"value\": \"0.00\"\n" +
            "                },\n" +
            "                \"cod_charges\": {\n" +
            "                    \"label\": \"COD CHARGES\",\n" +
            "                    \"value\": \"39.00\"\n" +
            "                },\n" +
            "                \"total\": {\n" +
            "                    \"label\": \"TOTAL\",\n" +
            "                    \"value\": \"2367.00\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"shipping_detail_block\": {\n" +
            "                \"shipping_detail_title\": \"SHIPPING DETAIL\",\n" +
            "                \"shipping_detail_delivered_to_text\": \"Your order will be delivered to:\",\n" +
            "                \"shipping_detail_customer_name\": \"Taushif Ahmad\",\n" +
            "                \"shipping_detail_address1\": \"address line 1\",\n" +
            "                \"shipping_detail_address2\": \"Udyog Vihar\",\n" +
            "                \"shipping_detail_mobile\": \"+919999999999\",\n" +
            "                \"shipping_detail_please_note\": \"Please Note\",\n" +
            "                \"shipping_detail_delivery_info\": \"Products with different delivery time may be shipped '' separately.\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    \"session\": {\n" +
            "        \"id\": \"1lsg5ps054gmvhs0uc0ivbv7h5\",\n" +
            "        \"expire\": null,\n" +
            "        \"is_loggedin\": true,\n" +
            "        \"YII_CSRF_TOKEN\": \"7f157d09e45a2f7a50686c4e9da0846d0dbb5896\"\n" +
            "    },\n" +
            "    \"metadata\": {\n" +
            "        \"delay_days\": null,\n" +
            "        \"fb_title\": \"Black Loafers\",\n" +
            "        \"fb_image\": \"http://statichttp://static3.jassets.com/p/Catwalk-Black-Loafers-8232-8733-1-catalog.jpg\",\n" +
            "        \"fb_link\": \"http://indfas.alice.com/mobapi/catalog/detail?sku=CA028SH21EZY\",\n" +
            "        \"fb_link_desk\": \"http://indfas.alice.com/Catwalk-Black-Loafers-3378.html\",\n" +
            "        \"fb_share_text\": \"I just grabbed some cool stuff from Jabong...have you shopped yet from jabong.com?\",\n" +
            "        \"subtotal\": 2298,\n" +
            "        \"discount\": 0,\n" +
            "        \"special_discount\": 0,\n" +
            "        \"jabong_credits\": 0,\n" +
            "        \"order_nr\": \"140207673725833\",\n" +
            "        \"shipping_charges\": \"0.00\",\n" +
            "        \"cod_charges\": \"39.00\",\n" +
            "        \"total_amount\": \"2367.00\",\n" +
            "        \"shipping_address\": {\n" +
            "            \"id_sales_order_address\": \"1743\",\n" +
            "            \"first_name\": \"Taushif\",\n" +
            "            \"last_name\": \"Ahmad\",\n" +
            "            \"address1\": \"address line 1\",\n" +
            "            \"address2\": \"Udyog Vihar\",\n" +
            "            \"city\": \"Gurgaon\",\n" +
            "            \"postcode\": \"122001\",\n" +
            "            \"phone\": \"+919999999999\",\n" +
            "            \"fk_country\": \"105\",\n" +
            "            \"is_billing\": \"0\",\n" +
            "            \"created_at\": \"2014-02-07 18:28:45\",\n" +
            "            \"updated_at\": \"2014-02-07 18:28:45\",\n" +
            "            \"fk_customer_address_region\": \"29\"\n" +
            "        },\n" +
            "        \"userName\": \"Taushif Ahmad\",\n" +
            "        \"paymentMethod\": \"cod_order\"\n" +
            "    }\n" +
            "\n" +
            "}\n";


}
