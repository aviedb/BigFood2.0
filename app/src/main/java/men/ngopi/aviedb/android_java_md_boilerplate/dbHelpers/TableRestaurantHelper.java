package men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import men.ngopi.aviedb.android_java_md_boilerplate.MainActivity;
import men.ngopi.aviedb.android_java_md_boilerplate.Restaurant;

import static men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.DatabaseContract.*;
import static men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.DatabaseContract.RestaurantColumns.*;

public class TableRestaurantHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private static TableRestaurantHelper instance;

    public static TableRestaurantHelper getInstance() {
        return instance;
    }

    public TableRestaurantHelper() {
        instance = this;
        context = MainActivity.getInstance();
    }

    public TableRestaurantHelper(Context context) {
        instance = this;
        this.context = context;
    }

    public TableRestaurantHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public TableRestaurantHelper close() {
        databaseHelper.close();
        return this;
    }

    public Restaurant fetchRestaurantById(int id) {
        Cursor cursor = database.query(
            RESTAURANT_TABLE,
            null,
            _ID + " = ?",
            new String[]{ "" + id },
            null,
            null,
            _ID + " DESC",
            null
        );

        return fetchRestaurant(cursor).get(0);
    }

    public ArrayList<Restaurant> fetchAllRestaurants() {
        return this.fetchAllRestaurants(null);
    }

    public ArrayList<Restaurant> fetchAllRestaurants(String search) {
        String selection = null;

        if (search != null) {
            if (search.length() > 0) {
                selection = NAME + " LIKE '%" + search + "%' OR " + ADDRESS + " LIKE '%" + search + "%'";
            }
        }

        Cursor cursor = null;
        try {
            database.beginTransaction();
            cursor = database.query(
                RESTAURANT_TABLE,
                null,
                selection,
                null,
                null,
                null,
                _ID + " DESC",
                null
            );

            database.setTransactionSuccessful();
        } catch (SQLiteException err) {
            Log.e("Fetching Post", err.toString());
        }
        database.endTransaction();

        return fetchRestaurant(cursor);
    }

    public ArrayList<Restaurant> fetchRestaurant(Cursor cursor) {
        ArrayList<Restaurant> result = new ArrayList<Restaurant>();
        Restaurant restaurant;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                restaurant = new Restaurant(
                    cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ADDRESS))
                );

                result.add(restaurant);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        return result;
    }

    public long insert(Restaurant restaurant) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, restaurant.getRestaurantName());
        initialValues.put(ADDRESS, restaurant.getRestaurantAddress());
        return database.insert(RESTAURANT_TABLE, null, initialValues);
    }

    public int update(Restaurant restaurant) {
        ContentValues args = new ContentValues();
        args.put(NAME, restaurant.getRestaurantName());
        args.put(ADDRESS, restaurant.getRestaurantAddress());

        return database.update(
                RESTAURANT_TABLE,
                args,
                _ID + " = '" + restaurant.getId() + "'",
                null
        );
    }

    public int delete(int id) {
        return database.delete(RESTAURANT_TABLE, _ID + " = '" + id + "'", null);
    }
}
