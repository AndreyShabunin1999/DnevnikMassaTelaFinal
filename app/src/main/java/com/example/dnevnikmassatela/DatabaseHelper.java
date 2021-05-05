package com.example.dnevnikmassatela;

import android.animation.TypeConverter;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class  DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 14;
    // Database Name
    private static final String DATABASE_NAME = "Dnevnik.db";
    // User table name
    private static final String TABLE_USER = "User";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_DATA = "user_data";
    private static final String COLUMN_USER_WEIGHT = "user_weight";
    private static final String COLUMN_USER_HEIGHT = "user_height";
    private static final String COLUMN_USER_GENDER = "user_gender";
    private static final String COLUMN_DATA_REG = "datareg";
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_KKAL = "kkal";
    public static final String COLUMN_NAME_PROD = "nameprod";
    public static final String COLUMN_BREAKFAST_USER = "br_user";
    public static final String COLUMN_PROTEIN = "prot";
    public static final String COLUMN_FAT = "fat";
    public static final String COLUMN_UGLEVOD = "carbs";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_DINNER_USER = "din_user";
    public static final String COLUMN_UZHIN_USER = "uzh_user";
    public static final String COLUMN_PEREKUS_USER = "per_user";

    // create table sql query
    private final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL, "
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_DATA + " TEXT NOT NULL, "
            + COLUMN_USER_WEIGHT + " INTEGER NOT NULL, "
            + COLUMN_USER_HEIGHT + " INTEGER NOT NULL, "
            + COLUMN_USER_GENDER + " TEXT NOT NULL, "
            + COLUMN_DATA_REG +  " TEXT NOT NULL"+")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    DatabaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //создание таблицы измерений веса
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL("create Table TABLE_ZAMER(COLUMN_ZAMER_ID INTEGER PRIMARY KEY AUTOINCREMENT, COLUMN_ZAMER_USER INTEGER, COLUMN_VES INTEGER NOT NULL, COLUMN_DATA_ZAMER TEXT, FOREIGN KEY(COLUMN_ZAMER_USER) REFERENCES User(user_id))");
        //создание таблицы завтрака
        db.execSQL("CREATE TABLE TABLE_BREAKFAST ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BREAKFAST_USER + " INTEGER NOT NULL, "
                + COLUMN_NAME_PROD + " TEXT NOT NULL, "
                + COLUMN_PROTEIN + " DUOBLE NOT NULL, "
                + COLUMN_FAT + " DUOBLE NOT NULL, "
                + COLUMN_UGLEVOD + " DUOBLE NOT NULL, "
                + COLUMN_KKAL + " DUOBLE NOT NULL, "
                + COLUMN_DATA + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_BREAKFAST_USER + ") REFERENCES User(user_id));");
        //создание таблицы обед
        db.execSQL("CREATE TABLE TABLE_DINNER ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DINNER_USER + " INTEGER NOT NULL, "
                + COLUMN_NAME_PROD + " TEXT NOT NULL, "
                + COLUMN_PROTEIN + " DUOBLE NOT NULL, "
                + COLUMN_FAT + " DUOBLE NOT NULL, "
                + COLUMN_UGLEVOD + " DUOBLE NOT NULL, "
                + COLUMN_KKAL + " DUOBLE NOT NULL, "
                + COLUMN_DATA + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_DINNER_USER + ") REFERENCES User(user_id));");
        //создание таблицы ужин
        db.execSQL("CREATE TABLE TABLE_UZHIN ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_UZHIN_USER + " INTEGER NOT NULL, "
                + COLUMN_NAME_PROD + " TEXT NOT NULL, "
                + COLUMN_PROTEIN + " DUOBLE NOT NULL, "
                + COLUMN_FAT + " DUOBLE NOT NULL, "
                + COLUMN_UGLEVOD + " DUOBLE NOT NULL, "
                + COLUMN_KKAL + " DUOBLE NOT NULL, "
                + COLUMN_DATA + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_UZHIN_USER + ") REFERENCES User(user_id));");
        //создание таблицы Перекус
        db.execSQL("CREATE TABLE TABLE_PEREKUS ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PEREKUS_USER + " INTEGER NOT NULL, "
                + COLUMN_NAME_PROD + " TEXT NOT NULL, "
                + COLUMN_PROTEIN + " DUOBLE NOT NULL, "
                + COLUMN_FAT + " DUOBLE NOT NULL, "
                + COLUMN_UGLEVOD + " DUOBLE NOT NULL, "
                + COLUMN_KKAL + " DUOBLE NOT NULL, "
                + COLUMN_DATA + " TEXT NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_PEREKUS_USER + ") REFERENCES User(user_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Удаление таблицы USER
        db.execSQL(DROP_USER_TABLE);
        //Удаление таблицы TABLE_ZAMER
        db.execSQL("drop Table if exists TABLE_ZAMER");
        //Удаление таблицы TABLE_BREAKFAST
        db.execSQL("drop Table if exists TABLE_BREAKFAST");
        //Удаление таблицы TABLE_DINNER
        db.execSQL("drop Table if exists TABLE_DINNER");
        //Удаление таблицы TABLE_UZHIN
        db.execSQL("drop Table if exists TABLE_UZHIN");
        //Удаление таблицы TABLE_PEREKUS
        db.execSQL("drop Table if exists TABLE_PEREKUS");
        // Создать таблицы опять
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_DATA, user.getData());
        values.put(COLUMN_USER_WEIGHT, user.getWeight());
        values.put(COLUMN_USER_HEIGHT, user.getHeight());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_DATA_REG, user.getDatareg());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    //Вставка в завтрак
    public Boolean insertBreakfast(Integer id_user, String nameprod, double protein, double fat, double carbs, double kkal, String data)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("br_user", id_user);
        contentValues.put("nameprod", nameprod);
        contentValues.put("prot", protein);
        contentValues.put("fat", fat);
        contentValues.put("carbs", carbs);
        contentValues.put("kkal", kkal);
        contentValues.put("data", data);
        long result=DB.insert("TABLE_BREAKFAST", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    //Вставка в обед
    public Boolean insertDinner(Integer id_user, String nameprod, double protein, double fat, double carbs, double kkal, String data)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("din_user", id_user);
        contentValues.put("nameprod", nameprod);
        contentValues.put("prot", protein);
        contentValues.put("fat", fat);
        contentValues.put("carbs", carbs);
        contentValues.put("kkal", kkal);
        contentValues.put("data", data);
        long result=DB.insert("TABLE_DINNER", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    //Вставка в обед
    public Boolean insertUzhin(Integer id_user, String nameprod, double protein, double fat, double carbs, double kkal, String data)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("uzh_user", id_user);
        contentValues.put("nameprod", nameprod);
        contentValues.put("prot", protein);
        contentValues.put("fat", fat);
        contentValues.put("carbs", carbs);
        contentValues.put("kkal", kkal);
        contentValues.put("data", data);
        long result=DB.insert("TABLE_UZHIN", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    //Вставка в перекус
    public Boolean insertPerekus(Integer id_user, String nameprod, double protein, double fat, double carbs, double kkal, String data)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("per_user", id_user);
        contentValues.put("nameprod", nameprod);
        contentValues.put("prot", protein);
        contentValues.put("fat", fat);
        contentValues.put("carbs", carbs);
        contentValues.put("kkal", kkal);
        contentValues.put("data", data);
        long result=DB.insert("TABLE_PEREKUS", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    //Вставка в замер
    public Boolean insertuserdata(Integer id_user, String ves, String data)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COLUMN_ZAMER_USER", id_user);
        contentValues.put("COLUMN_VES", ves);
        contentValues.put("COLUMN_DATA_ZAMER", data);
        long result=DB.insert("TABLE_ZAMER", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        //  db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};


        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    public Integer ves;
    public Integer getVes() {
        return ves;
    }

    public Integer uId;
    public Integer getuId() {
        return uId;
    }

    public Integer vusota;
    public Integer getVusota() {
        return vusota;
    }

    public Integer checkWeight (String email) {
        // array of columns to fetch
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_WEIGHT,COLUMN_USER_HEIGHT,COLUMN_USER_ID};

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            ves = cursor.getInt(0);
            vusota = cursor.getInt(1);
            uId = cursor.getInt(2);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return ves;
    }

    public String pol;
    public String getPol() {
        return pol;
    }

    public String dr;
    public String getDR() {
        return dr;
    }



    public String DataAndPol (String email) {
        // array of columns to fetch
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_DATA,COLUMN_USER_GENDER};

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            dr = cursor.getString(0);
            pol = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return pol;
    }


    public String dareg;
    public String getDareg() {
        return dareg;
    }

    public String checkdatareg (String email) {
        // array of columns to fetch
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_DATA_REG};

        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            dareg = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return dareg;
    }


    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Integer i = getuId();
        String q = "Select COLUMN_DATA_ZAMER from TABLE_ZAMER where COLUMN_ZAMER_ID = (SELECT MAX(COLUMN_ZAMER_ID)  FROM TABLE_ZAMER) AND COLUMN_ZAMER_USER = " + i;
        Cursor cursor = DB.rawQuery(q, null);
        return cursor;
    }

    public Cursor getV ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Integer i = getuId();
        String q = "Select COLUMN_VES, COLUMN_DATA_ZAMER from TABLE_ZAMER where COLUMN_ZAMER_ID = (SELECT MAX(COLUMN_ZAMER_ID)  FROM TABLE_ZAMER) AND COLUMN_ZAMER_USER = " + i;
        Cursor cursor = DB.rawQuery(q, null);
        return cursor;
    }

    public Cursor getKkal ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Integer i = getuId();
        String q = "Select SUM(kkal) from TABLE_BREAKFAST where data = date('now') AND br_user = " + i;
        Cursor cursor = DB.rawQuery(q, null);
        return cursor;
    }
}