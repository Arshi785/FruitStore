package com.dell.fruitstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class DbHelper extends SQLiteOpenHelper {
private static final int VERSION=1;

//create Table
public static final String FRUIT_TABLE_NAME="FRUIT_TABLE";
    public static final String FRUIT_NAME_KEY="FRUIT_NAME";
    public static final String FRUIT_PRICE_KEY="FRUIT_PRICE";
    public static final String FRUIT_QUANTITY_KEY="FRUIT_QUANTITY";

    private static final String CREATE_FRUIT_TABLE =
            "CREATE TABLE " + FRUIT_TABLE_NAME + "( " +
                    FRUIT_NAME_KEY + " TEXT PRIMARY KEY NOT NULL, " +
                    FRUIT_PRICE_KEY + " INTEGER NOT NULL, " +
                    FRUIT_QUANTITY_KEY + " TEXT NOT NULL, " +
                    "UNIQUE (" + FRUIT_NAME_KEY + ")" +
                    ");";

    private static final String DROP_FRUIT_TABLE = "DROP TABLE IF EXISTS " + FRUIT_TABLE_NAME;
    private static final String SELECT_FRUIT_TABLE = "SELECT * FROM " + FRUIT_TABLE_NAME;

    public DbHelper(Context context) {
        super(context,"FruitData.db", null, VERSION);
    }

    long addFruit(String fruitName, int fruitPrice, String fruitQuantity){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FRUIT_NAME_KEY,fruitName);
        values.put(FRUIT_PRICE_KEY,fruitPrice);
        values.put(FRUIT_QUANTITY_KEY,fruitQuantity);

        return database.insert(FRUIT_TABLE_NAME,null,values);
    }

    Cursor getClassTable(){
        SQLiteDatabase database = this.getReadableDatabase();

        return database.rawQuery(SELECT_FRUIT_TABLE,null);
    }
    int deleteClass(String fruitName){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(FRUIT_TABLE_NAME,FRUIT_NAME_KEY+"=?",new String[]{String.valueOf(fruitName)});
    }
    long updateClass(String fruitName,int fruitPrice, String fruitQuantity){
        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FRUIT_NAME_KEY,fruitName);
        values.put(FRUIT_PRICE_KEY,fruitPrice);
        values.put(FRUIT_QUANTITY_KEY,fruitQuantity);
        return database.update(FRUIT_TABLE_NAME,values,FRUIT_NAME_KEY+"=?",new String[]{fruitName});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FRUIT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_FRUIT_TABLE);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}