package co.mastersindia.autotax.dataHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import co.mastersindia.autotax.model.ItemDataModel;
import co.mastersindia.autotax.model.Order_model;


/**
 * Created by Pandu on 9/2/2017.
 */

public class OrderDataHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "orderManager";

    // Contacts table name
    private static final String TABLE_ORDERS = "orders";

    // Contacts Table Columns names
    private static final String KEY_ID="id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QTY="qty";
    private static final String KEY_DATE = "date";
    private static final String KEY_SUPID = "sup_id";
    private static final String KEY_STATUS = "status";


    public OrderDataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_QTY + " REAL,"
                + KEY_DATE + " TEXT,"
                + KEY_SUPID + " INTEGER,"
                + KEY_STATUS + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);

        // Create tables again
        onCreate(db);
    }
    // Adding new contact
    public void addOrder(Order_model idm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, idm.getId());
        values.put(KEY_NAME, idm.getName());
        values.put(KEY_QTY, idm.getQty());
        values.put(KEY_DATE, idm.getDate());
        values.put(KEY_SUPID, idm.getSup_id());
        values.put(KEY_STATUS, idm.getStatus());
        // Inserting Row
        db.insert(TABLE_ORDERS, null, values);
        db.close(); // Closing database connection
    }

    public Order_model getOrder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, new String[] { KEY_ID, KEY_NAME,KEY_QTY, KEY_DATE, KEY_SUPID, KEY_STATUS}, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Order_model supDataModel = new Order_model(Integer.parseInt(cursor.getString(0)), cursor.getString(1),Double.parseDouble(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(4)),cursor.getString(5));

        return supDataModel;
    }

    // Getting All Contacts
    public List<Order_model> getAllOrders() {
        List<Order_model> contactList = new ArrayList<Order_model>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Order_model contact = new Order_model();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setQty(Double.parseDouble(cursor.getString(2)));
                contact.setDate(cursor.getString(3));
                contact.setSup_id(Integer.parseInt(cursor.getString(4)));
                contact.setStatus(cursor.getString(5));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Getting contacts Count
    public int getOrdersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    // Updating single contact
    public int updateOrder(Order_model idm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, idm.getName());
        values.put(KEY_QTY, idm.getQty());
        values.put(KEY_DATE, idm.getDate());
        values.put(KEY_SUPID, idm.getSup_id());
        values.put(KEY_STATUS, idm.getStatus());

        // updating row
        return db.update(TABLE_ORDERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(idm.getId()) });
    }

    // Deleting single contact
    public void deleteOrder(Order_model idm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, KEY_ID + " = ?",
                new String[] { String.valueOf(idm.getId()) });
        db.close();
    }
    public List<Order_model> getOrderBysupplier(int id) {
        List<Order_model> contactList = new ArrayList<Order_model>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " WHERE `" + KEY_SUPID + "` = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Order_model contact = new Order_model();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setQty(Double.parseDouble(cursor.getString(2)));
                contact.setDate(cursor.getString(3));
                contact.setSup_id(Integer.parseInt(cursor.getString(4)));
                contact.setStatus(cursor.getString(5));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }
}
