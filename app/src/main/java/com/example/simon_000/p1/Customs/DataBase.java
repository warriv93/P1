package com.example.simon_000.p1.Customs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by simon_000 on 2014-09-24.
 */
public class DataBase extends SQLiteOpenHelper{
    private static final String DB_NAME = "Finances";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase database;


    public DataBase(Context ctx){
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    //gör det möjligt att skriva o läsa från tabellerna i databasen
    public void open(){
        database = getWritableDatabase();
    }

//stänger anslutnignen till databasen
    public void close(){
        database.close();
    }



//hämtar info från angiven tabell
    public Cursor getAllRevenues(){
        Cursor c = database.rawQuery("SELECT * from Revenues ORDER BY Date, Category", new String[]{});
        return c;
    }
    public Cursor getAllExpenses(){
        Cursor c = database.rawQuery("SELECT * from Expenses ORDER BY Date, Category", new String[]{});
        return c;
    }

//        public void setUpDb(){
//            database.execSQL("INSERT INTO Revenues (Name, Category, Value, Description, Date)" +
//                    "VALUES (Example, Lön, 120, Jobb hos Mormor, 26-9-2014);");
//            database.execSQL("INSERT INTO Expenses (Name, Category, Value, Description, Date)" +
//                    "VALUES (Example, Lön, 120, Jobb hos Mormor, 26-9-2014);");
//        }
    //skapar tabellerna
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Revenues (Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name VARCHAR(255), Category VARCHAR(255), Value DOUBLE, Description VARCHAR(255), Date VARCHAR(255));");

        sqLiteDatabase.execSQL("CREATE TABLE Expenses (Id INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                "Name VARCHAR(255), Category VARCHAR(255), Value DOUBLE, Description VARCHAR(255), Date VARCHAR(255));");

    }

    public Cursor getDate(){
        Cursor c = database.rawQuery("SELECT * from Revenues", new String[]{});
        return c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
    public Double getValueSum() {

        Cursor cursorrev = database.rawQuery("SELECT SUM(Value) FROM Revenues", null);
        double revSum = 0;
        if (cursorrev.moveToFirst()){
             revSum = cursorrev.getDouble(0);
        }
        Cursor cursorex = database.rawQuery("SELECT SUM(Value) FROM Expenses", null);
        double exSum = 0;
        if (cursorex.moveToFirst()){
            exSum = cursorex.getDouble(0);
        }
        Double Sum = revSum - exSum;

        return Sum;
    }

    //sparar info till angiven tabell
    public void saveRevenues(String revName, String revCate, double revValue, String revDesc, String revDate) {
            ContentValues values = new ContentValues();
            values.put("Name", revName);
            values.put("Category", revCate);
            values.put("Value", revValue);
            values.put("Description", revDesc);
            values.put("Date", revDate);

            database.insert("Revenues", null, values);

    }

    public void saveExpenses(String revName, String revCate, double revValue, String revDesc, String revDate) {
        ContentValues values = new ContentValues();
        values.put("Name", revName);
        values.put("Category", revCate);
        values.put("Value", revValue);
        values.put("Description", revDesc);
        values.put("Date", revDate);

        database.insert("Expenses", null, values);
    }


}
