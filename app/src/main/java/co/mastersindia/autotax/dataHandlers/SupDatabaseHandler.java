package co.mastersindia.autotax.dataHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import co.mastersindia.autotax.model.Sup_Model;

/**
 * Created by Pandu on 9/2/2017.
 */

public class SupDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "supplierManager";

    // Contacts table name
    private static final String TABLE_SUPPLIERS = "suppliers";

    // Contacts Table Columns names
    private static final String KEY_ID="id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_GSTIN="gstin";
    private static final String KEY_BNAME = "bname";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";


    public SupDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SUPPLIERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TYPE + " TEXT,"
                + KEY_GSTIN + " TEXT,"
                + KEY_BNAME + " TEXT,"
                + KEY_CONTACT + " TEXT,"
                + KEY_MAIL + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIERS);

        // Create tables again
        onCreate(db);
    }
    // Adding new contact
    public void addSupplier(Sup_Model idm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, idm.getId());
        values.put(KEY_TYPE, idm.getType());
        values.put(KEY_GSTIN, idm.getGstin());
        values.put(KEY_BNAME, idm.getBname());
        values.put(KEY_CONTACT, idm.getContact());
        values.put(KEY_MAIL, idm.getMail());
        values.put(KEY_NAME, idm.getName());
        values.put(KEY_ADDRESS, idm.getAddress());
        // Inserting Row
        db.insert(TABLE_SUPPLIERS, null, values);
        db.close(); // Closing database connection
    }

    public Sup_Model getSupplier(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SUPPLIERS, new String[] { KEY_ID, KEY_TYPE,KEY_GSTIN, KEY_BNAME, KEY_CONTACT, KEY_MAIL, KEY_NAME, KEY_ADDRESS }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Sup_Model supDataModel = new Sup_Model(Integer.parseInt(cursor.getString(0)), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5), cursor.getString(6), cursor.getString(7));

        return supDataModel;
    }

    // Getting All Contacts
    public List<Sup_Model> getAllSuppliers() {
        List<Sup_Model> contactList = new ArrayList<Sup_Model>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SUPPLIERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Sup_Model contact = new Sup_Model();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setType(cursor.getString(1));
                contact.setGstin(cursor.getString(2));
                contact.setBname(cursor.getString(3));
                contact.setContact(cursor.getString(4));
                contact.setMail(cursor.getString(5));
                contact.setName(cursor.getString(6));
                contact.setAddress(cursor.getString(7));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Getting contacts Count
    public int getSuppliersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SUPPLIERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    // Updating single contact
    public int updateSupplier(Sup_Model idm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, idm.getType());
        values.put(KEY_GSTIN, idm.getGstin());
        values.put(KEY_BNAME, idm.getBname());
        values.put(KEY_CONTACT, idm.getContact());
        values.put(KEY_MAIL, idm.getMail());
        values.put(KEY_NAME, idm.getName());
        values.put(KEY_ADDRESS, idm.getAddress());

        // updating row
        return db.update(TABLE_SUPPLIERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(idm.getId()) });
    }

    // Deleting single contact
    public void deleteSupplier(Sup_Model idm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUPPLIERS, KEY_ID + " = ?",
                new String[] { String.valueOf(idm.getId()) });
        db.close();
    }

}
