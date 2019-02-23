package men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers;

import static men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.DatabaseContract.*;
import static men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.DatabaseContract.RestaurantColumns.*;
import static men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.DatabaseContract.MenuColumns.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "bigfood";

    public final static String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + RESTAURANT_TABLE + " ( "
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RestaurantColumns.NAME + " TEXT NOT NULL, "
            + RestaurantColumns.ADDRESS + " TEXT NOT NULL);";

    public final static String CREATE_MENU_TABLE = "CREATE TABLE " + MENU_TABLE + " ( "
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RESTAURANT_ID + " INTEGER NOT NULL, "
            + MenuColumns.NAME + " TEXT NOT NULL, "
            + MenuColumns.DESCRIPTION + " TEXT NOT NULL, "
            + MenuColumns.PRICE + " TEXT NOT NULL, "
            + "FOREIGN KEY (" + RESTAURANT_ID + ") REFERENCES " + RESTAURANT_TABLE + "(" + _ID + "));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESTAURANT_TABLE);
        db.execSQL(CREATE_MENU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MENU_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESTAURANT_TABLE);
        onCreate(db);
    }
}
