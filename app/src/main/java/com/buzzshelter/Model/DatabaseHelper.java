package com.buzzshelter.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Database Helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Keep only 1 DB active at a time
    private static DatabaseHelper sInstance;

    //DB Version
    private static final int DATABASE_VERSION = 1;

    //db name
    private static final String DATABASE_NAME = "buzzshelter.db";

    //table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_SHELTERS = "shelters";

    //shared-FKs
    private static final String KEY_SHELTER_NAME = "shelter_name";

    //USERS Table - column names
    private static final String KEY_USER_ID = "user_id";
    private static final String USER_NAME = "user_name";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_ACCOUNT_TYPE = "user_account_type";
    private static final String USER_NUMBER_BEDS_CLAIMED = "user_number_beds_claimed";

    //SHELTERS Table - column names
    private static final String SHELTER_CAPACITY = "shelter_capacity";
    private static final String SHELTER_RESTRICTIONS = "shelter_restrictions";
    private static final String SHELTER_LONGITUDE = "shelter_longitude";
    private static final String SHELTER_LATITUDE = "shelter_latitude";
    private static final String SHELTER_ADDRESS = "shelter_address";
    private static final String SHELTER_PHONE_NUMBER = "shelter_phone_number";
    private static final String SHELTER_VACANCY = "shelter_vacancy";


    //Table Create Statements

    //create table user
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "( " +
            KEY_USER_ID + " TEXT PRIMARY KEY, " +
            USER_NAME + " TEXT, " +
            USER_PASSWORD + " TEXT, " +
            USER_ACCOUNT_TYPE + " TEXT, " +
            USER_NUMBER_BEDS_CLAIMED + " INTEGER, " +
            KEY_SHELTER_NAME + " TEXT " +
            "); ";

    //create table shelters
    private static final String CREATE_TABLE_SHELTERS = "CREATE TABLE " + TABLE_SHELTERS + "(" +
            KEY_SHELTER_NAME + " TEXT PRIMARY KEY, " +
            SHELTER_CAPACITY + " TEXT, " +
            SHELTER_RESTRICTIONS + " TEXT, " +
            SHELTER_LONGITUDE + " TEXT, " +
            SHELTER_LATITUDE + " TEXT, " +
            SHELTER_ADDRESS + " TEXT, " +
            SHELTER_PHONE_NUMBER + " TEXT, " +
            SHELTER_VACANCY + " INTEGER " +
            "); ";

    /**
     * Get instance of database helper
     * @param context application context
     * @return the databasehelper
     */
    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        sqldb.execSQL(CREATE_TABLE_USERS);
        sqldb.execSQL(CREATE_TABLE_SHELTERS);

    }

    //DANGER. DROPS ALL TABLES
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHELTERS);
        onCreate(db);
        db.close();

    }

    /**
     * Creates user in database
     * @param user the user to create
     */
    public void createUSER(User user) {
        if(user == null) {
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(Objects.equals(user.getId(), "") || (user.getId() == null)) {
            return;
        }
        values.put(KEY_USER_ID, user.getId());
        values.put(USER_NAME, user.getName());
        values.put(USER_PASSWORD, user.getPassword());
        if(user.getAccountType() == null) {
            return;
        }
        values.put(USER_ACCOUNT_TYPE, user.getAccountType().name());
        values.put(USER_NUMBER_BEDS_CLAIMED, user.getNumberBedClaimed());
        values.put(KEY_SHELTER_NAME, user.getLocationBedClaimed());


        //Insert Row
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    /**
     * Fetches unique user
     * @param user_id the id of the user
     * @return the user
     */
    public User fetchSpecificUserByID(String user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                KEY_USER_ID + " = " + "\"" + user_id + "\"" + ";";

        Cursor c = db.rawQuery(selectQuery, null);
        if((c != null) && c.moveToFirst()) {
            c.moveToFirst();
        } else {
            return null;
        }
        AccountType.valueOf("ADMIN");
        User us = new User(c.getString(c.getColumnIndex(KEY_USER_ID)),
                c.getString(c.getColumnIndex(USER_NAME)),
                c.getString(c.getColumnIndex(USER_PASSWORD)),
                AccountType.valueOf(c.getString(c.getColumnIndex(USER_ACCOUNT_TYPE))));
        us.setNumberBedClaimed(c.getInt(c.getColumnIndex(USER_NUMBER_BEDS_CLAIMED)));
        us.setLocationBedClaimed(c.getString(c.getColumnIndex(KEY_SHELTER_NAME)));
        c.close();
        db.close();
        return us;
    }

// --Commented out by Inspection START (4/8/18 10:46 PM):
//    public HashMap<String, User> makeUserHashMap() {
//        HashMap<String, User> users = new HashMap<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String selectQuery = "SELECT * FROM " + TABLE_USERS;
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        //looping through rows and adding to hashmap
//        if(c.moveToFirst()) {
//            do {
//                User u = new User(
//                        c.getString(c.getColumnIndex(KEY_USER_ID)),
//                        c.getString(c.getColumnIndex(USER_NAME)),
//                        c.getString(c.getColumnIndex(USER_PASSWORD)),
//                        AccountType.valueOf(c.getString(c.getColumnIndex(USER_ACCOUNT_TYPE))));
//                u.setLocationBedClaimed(c.getString(c.getColumnIndex(KEY_SHELTER_NAME)));
//                u.setNumberBedClaimed( c.getInt(c.getColumnIndex(USER_NUMBER_BEDS_CLAIMED)));
//                users.put(u.getId(), u);
//
//            } while(c.moveToNext());
//
//        }
//        return users;
//
//    }
// --Commented out by Inspection STOP (4/8/18 10:46 PM)


    /**
     * Updates the user
     * @param user the user to update
     */
    public void updateUSER(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, user.getName());
        values.put(USER_NAME, user.getName());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_ACCOUNT_TYPE, user.getAccountType().toString());
        values.put(USER_NUMBER_BEDS_CLAIMED, user.getNumberBedClaimed());
        values.put(KEY_SHELTER_NAME, user.getLocationBedClaimed());

        //updating row
        //returns the number of rows affected
        db.update(TABLE_USERS, values, KEY_USER_ID + " = ?",
                new String[] {user.getId()});
        db.close();

    }

// --Commented out by Inspection START (4/8/18 10:41 PM):
//    //Delete User
//    public void deleteUSER(String user_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_USERS, KEY_USER_ID + " = ?",
//                new String[] {user_id});
//        db.close();
//
//    }
// --Commented out by Inspection STOP (4/8/18 10:41 PM)


    /**
     * Add a new shelter
     * @param shelter shelter to add
     */
    public void createSHELTER(Shelter shelter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SHELTER_NAME, shelter.getName());
        values.put(SHELTER_CAPACITY, shelter.getCapacity());
        values.put(SHELTER_RESTRICTIONS, shelter.getRestrictions());
        values.put(SHELTER_LONGITUDE, shelter.getLongitude());
        values.put(SHELTER_LATITUDE, shelter.getLatitude());
        values.put(SHELTER_ADDRESS, shelter.getAddress());
        values.put(SHELTER_PHONE_NUMBER, shelter.getPhoneNumber());
        values.put(SHELTER_VACANCY, shelter.getVacancy());

        //Insert Row
        db.insert(TABLE_SHELTERS, null, values);
        db.close();
    }

    /**
     * Fetch unique shelter by name
     * @param shelter_name shelter name
     * @return the shelter
     */
    public Shelter fetchSpecificShelterByName(String shelter_name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SHELTERS + " WHERE " +
                KEY_SHELTER_NAME + " = " + "\"" + shelter_name +"\"";

        Cursor c = db.rawQuery(selectQuery, null);

        if((c != null) && c.moveToFirst()) {
            c.moveToFirst();
        }
        else {
            return null;
        }

        Shelter s = new Shelter(c.getString(c.getColumnIndex(KEY_SHELTER_NAME)),
                c.getString(c.getColumnIndex(SHELTER_CAPACITY)),
                c.getString(c.getColumnIndex(SHELTER_RESTRICTIONS)),
                c.getString(c.getColumnIndex(SHELTER_LONGITUDE)),
                c.getString(c.getColumnIndex(SHELTER_LATITUDE)),
                c.getString(c.getColumnIndex(SHELTER_ADDRESS)),
                c.getString(c.getColumnIndex(SHELTER_PHONE_NUMBER))
                );
        s.setVacancy(c.getInt(c.getColumnIndex(SHELTER_VACANCY)));

        c.close();
        db.close();
        return s;
    }

    /**
     * Updates the shelter
     * @param shelter new shelter
     */
    public void updateSHELTER(Shelter shelter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SHELTER_NAME, shelter.getName());
        values.put(SHELTER_CAPACITY, shelter.getCapacity());
        values.put(SHELTER_RESTRICTIONS, shelter.getRestrictions());
        values.put(SHELTER_LONGITUDE, shelter.getLongitude());
        values.put(SHELTER_LATITUDE, shelter.getLatitude());
        values.put(SHELTER_ADDRESS, shelter.getAddress());
        values.put(SHELTER_PHONE_NUMBER, shelter.getPhoneNumber());
        values.put(SHELTER_VACANCY, shelter.getVacancy());

        //updating row
        //returns the number of rows affected
        db.update(TABLE_SHELTERS, values, KEY_SHELTER_NAME + " = ?",
                new String[]{shelter.getName()});
        db.close();
    }

    /**
     * Creates a shelter hash map
     * @return the shelter hash map
     */
    public Map<String, Shelter> makeShelterHashMap() {
        Map<String, Shelter> shelters = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_SHELTERS;
        Cursor c = db.rawQuery(selectQuery, null);

        //looping through rows and adding to hashmap
        if(c.moveToFirst()) {
            do {
                Shelter s = new Shelter(
                        c.getString(c.getColumnIndex(KEY_SHELTER_NAME)),
                        c.getString(c.getColumnIndex(SHELTER_CAPACITY)),
                        c.getString(c.getColumnIndex(SHELTER_RESTRICTIONS)),
                        c.getString(c.getColumnIndex(SHELTER_LONGITUDE)),
                        c.getString(c.getColumnIndex(SHELTER_LATITUDE)),
                        c.getString(c.getColumnIndex(SHELTER_ADDRESS)),
                        c.getString(c.getColumnIndex(SHELTER_PHONE_NUMBER))
                );
                s.setVacancy(c.getInt(c.getColumnIndex(SHELTER_VACANCY)));

                shelters.put(s.getName(), s);
            } while(c.moveToNext());

        }
        return shelters;

    }



// --Commented out by Inspection START (4/8/18 10:41 PM):
//    //delete shelter
//    public void deleteSHELTER(String shelter_name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.delete(TABLE_SHELTERS, KEY_SHELTER_NAME + " = ?",
//                new String[] {shelter_name});
//        db.close();
//
//    }
// --Commented out by Inspection STOP (4/8/18 10:41 PM)


    /**
     * Closes the DB
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if((db != null) && db.isOpen()) {
            db.close();
        }
    }

    /**
     * Testing method
     * @param instance the instance to mock
     */
    public static void setInstance(DatabaseHelper instance) {
        sInstance = instance;
    }

}








































