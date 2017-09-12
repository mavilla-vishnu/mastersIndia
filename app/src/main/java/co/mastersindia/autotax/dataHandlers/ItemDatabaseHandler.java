package co.mastersindia.autotax.dataHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import co.mastersindia.autotax.model.ItemDataModel;

/**
 * Created by Pandu on 9/2/2017.
 */

public class ItemDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "itemsManager";

    // Contacts table name
    private static final String TABLE_ITEMS = "items";

    // Contacts Table Columns names
    private static final String KEY_ID="id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FAV="fav";
    private static final String KEY_TYPE = "type";
    private static final String KEY_UNIT = "unit";
    private static final String KEY_PRICE = "price";
    private static final String KEY_CODE = "code";
    private static final String KEY_DESCP = "descp";
    private static final String KEY_IGST = "igst";
    private static final String KEY_CGST = "cgst";
    private static final String KEY_SGST = "sgst";
    private static final String KEY_CESS = "cess";
    private static final String KEY_S_TOT = "s_tot";
    private static final String KEY_S_PRESENT = "s_present";
    private static final String KEY_S_FINAL = "s_final";
    private static final String KEY_DISC="disc";

    public ItemDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_FAV + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_UNIT + " TEXT,"
                + KEY_PRICE + " REAL,"
                + KEY_CODE + " TEXT,"
                + KEY_DESCP + " TEXT,"
                + KEY_IGST + " REAL,"
                + KEY_CGST + " REAL,"
                + KEY_SGST + " REAL,"
                + KEY_CESS + " REAL,"
                + KEY_S_TOT + " REAL,"
                + KEY_S_PRESENT + " REAL,"
                + KEY_S_FINAL + " REAL,"
                + KEY_DISC + " REAL"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

        // Create tables again
        onCreate(db);
    }
    // Adding new contact
    public void addContact(ItemDataModel idm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, idm.getId());
        values.put(KEY_NAME, idm.getName());
        values.put(KEY_FAV, idm.getFav());
        values.put(KEY_TYPE, idm.getType());
        values.put(KEY_UNIT, idm.getUnit());
        values.put(KEY_PRICE, idm.getPrice());
        values.put(KEY_CODE, idm.getCode());
        values.put(KEY_DESCP, idm.getDescp());
        values.put(KEY_IGST, idm.getIgst());
        values.put(KEY_CGST, idm.getCgst());
        values.put(KEY_SGST, idm.getSgst());
        values.put(KEY_CESS, idm.getCess());
        values.put(KEY_S_TOT, idm.getS_tot());
        values.put(KEY_S_PRESENT, idm.getS_present());
        values.put(KEY_S_FINAL, idm.getS_final());
        values.put(KEY_DISC, idm.getDisc());

        // Inserting Row
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }
    // Getting single item
    public ItemDataModel getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, new String[] { KEY_ID, KEY_NAME,KEY_FAV, KEY_TYPE, KEY_UNIT, KEY_PRICE, KEY_CODE, KEY_DESCP, KEY_IGST, KEY_CGST, KEY_SGST, KEY_CESS, KEY_S_TOT, KEY_S_PRESENT, KEY_S_FINAL,KEY_DISC }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ItemDataModel ItemDataModel = new ItemDataModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4),Double.parseDouble(cursor.getString(5)), cursor.getString(6), cursor.getString(7)
                ,Double.parseDouble(cursor.getString(8)),Double.parseDouble(cursor.getString(9)),Double.parseDouble(cursor.getString(10)),Double.parseDouble(cursor.getString(11)),Double.parseDouble(cursor.getString(12)),Double.parseDouble(cursor.getString(13)),Double.parseDouble(cursor.getString(14)),Double.parseDouble(cursor.getString(15)));
        // return item
        return ItemDataModel;
    }

    // Getting All Contacts
    public List<ItemDataModel> getAllitems() {
        List<ItemDataModel> contactList = new ArrayList<ItemDataModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemDataModel contact = new ItemDataModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setFav(cursor.getString(2));
                contact.setType(cursor.getString(3));
                contact.setUnit(cursor.getString(4));
                contact.setPrice(Double.parseDouble(cursor.getString(5)));
                contact.setCode(cursor.getString(6));
                contact.setDescp(cursor.getString(7));
                contact.setIgst(Double.parseDouble(cursor.getString(8)));
                contact.setCgst(Double.parseDouble(cursor.getString(9)));
                contact.setSgst(Double.parseDouble(cursor.getString(10)));
                contact.setCess(Double.parseDouble(cursor.getString(11)));
                contact.setS_tot(Double.parseDouble(cursor.getString(12)));
                contact.setS_present(Double.parseDouble(cursor.getString(13)));
                contact.setS_final(Double.parseDouble(cursor.getString(14)));
                contact.setDisc(Double.parseDouble(cursor.getString(15)));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Getting contacts Count
    public int getItemsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    // Updating single contact
    public int updateitem(ItemDataModel idm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, idm.getName());
        values.put(KEY_FAV, idm.getFav());
        values.put(KEY_TYPE, idm.getType());
        values.put(KEY_UNIT, idm.getUnit());
        values.put(KEY_PRICE, idm.getPrice());
        values.put(KEY_CODE, idm.getCode());
        values.put(KEY_DESCP, idm.getDescp());
        values.put(KEY_IGST, idm.getIgst());
        values.put(KEY_CGST, idm.getCgst());
        values.put(KEY_SGST, idm.getSgst());
        values.put(KEY_CESS, idm.getCess());
        values.put(KEY_S_TOT, idm.getS_tot());
        values.put(KEY_S_PRESENT, idm.getS_present());
        values.put(KEY_S_FINAL, idm.getS_final());
        values.put(KEY_DISC, idm.getDisc());


        // updating row
        return db.update(TABLE_ITEMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(idm.getId()) });
    }

    // Deleting single contact
    public void deleteItem(ItemDataModel idm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[] { String.valueOf(idm.getId()) });
        db.close();
    }

    public List<ItemDataModel> getAllGoods(String fText,String ftype) {
        List<ItemDataModel> contactList = new ArrayList<ItemDataModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS+ " WHERE `"+KEY_TYPE+"` = "+"'Good'"+" ORDER BY "+"`"+fText+"` "+ftype;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemDataModel contact = new ItemDataModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setFav(cursor.getString(2));
                contact.setType(cursor.getString(3));
                contact.setUnit(cursor.getString(4));
                contact.setPrice(Double.parseDouble(cursor.getString(5)));
                contact.setCode(cursor.getString(6));
                contact.setDescp(cursor.getString(7));
                contact.setIgst(Double.parseDouble(cursor.getString(8)));
                contact.setCgst(Double.parseDouble(cursor.getString(9)));
                contact.setSgst(Double.parseDouble(cursor.getString(10)));
                contact.setCess(Double.parseDouble(cursor.getString(11)));
                contact.setS_tot(Double.parseDouble(cursor.getString(12)));
                contact.setS_present(Double.parseDouble(cursor.getString(13)));
                contact.setS_final(Double.parseDouble(cursor.getString(14)));
                contact.setDisc(Double.parseDouble(cursor.getString(15)));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public List<ItemDataModel> getAllServices() {
        List<ItemDataModel> contactList = new ArrayList<ItemDataModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS+ " WHERE `"+KEY_TYPE+"` = "+"'Service'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemDataModel contact = new ItemDataModel();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setFav(cursor.getString(2));
                contact.setType(cursor.getString(3));
                contact.setUnit(cursor.getString(4));
                contact.setPrice(Double.parseDouble(cursor.getString(5)));
                contact.setCode(cursor.getString(6));
                contact.setDescp(cursor.getString(7));
                contact.setIgst(Double.parseDouble(cursor.getString(8)));
                contact.setCgst(Double.parseDouble(cursor.getString(9)));
                contact.setSgst(Double.parseDouble(cursor.getString(10)));
                contact.setCess(Double.parseDouble(cursor.getString(11)));
                contact.setS_tot(Double.parseDouble(cursor.getString(12)));
                contact.setS_present(Double.parseDouble(cursor.getString(13)));
                contact.setS_final(Double.parseDouble(cursor.getString(14)));
                contact.setDisc(Double.parseDouble(cursor.getString(15)));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public boolean checkBarcode(String code){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_ITEMS + " where `" + KEY_CODE + "` = '" + code+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public int getItemByCode(String code) {
        ItemDataModel idm=new ItemDataModel();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String sql ="SELECT `"+KEY_ID+"` FROM "+TABLE_ITEMS+" WHERE `"+KEY_CODE+"` = '"+code+"'";
        Cursor res = db.rawQuery(sql, null);
        int id=-1;
        if(res!=null&&res.moveToFirst())
        id=res.getInt(res.getColumnIndex("id"));
        return id;
    }

}
