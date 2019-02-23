package men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import men.ngopi.aviedb.android_java_md_boilerplate.MainActivity;
import men.ngopi.aviedb.android_java_md_boilerplate.Menu;
import men.ngopi.aviedb.android_java_md_boilerplate.RestaurantInfo;

import static men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.DatabaseContract.MENU_TABLE;
import static men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.DatabaseContract.MenuColumns.*;

public class TableMenuHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private static TableMenuHelper instance;

    public static TableMenuHelper getInstance() {
        return instance;
    }

    public TableMenuHelper() {
        instance = this;
        context = MainActivity.getInstance();
    }

    public TableMenuHelper(Context context) {
        instance = this;
        this.context = context;
    }

    public TableMenuHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public TableMenuHelper close() {
        databaseHelper.close();
        return this;
    }

    public ArrayList<Menu> fetchMenusByRestaurantId(int restaurantId) {
        return fetchMenusByRestaurantId(restaurantId, null);
    }

    public ArrayList<Menu> fetchMenusByRestaurantId(int restaurantId, String search) {
        String selection = RESTAURANT_ID + " = '" + restaurantId + "'";

        Log.d("search", search);
        if (search != null) {
            selection = RESTAURANT_ID + " = '" + restaurantId + "' AND (" +
                NAME + " LIKE '%" + search + "%' OR " +
                DESCRIPTION + " LIKE '%" + search + "%')";
        }

        Cursor cursor = database.query(
            MENU_TABLE,
            null,
            selection,
            null,
            null,
            null,
            _ID + " DESC",
            null
        );

        return fetchMenu(cursor);
    }

    public Menu fetchmenusById(int id) {
        Cursor cursor = database.query(
            MENU_TABLE,
            null,
            _ID + " = ?",
            new String[]{ "" + id },
            null,
            null,
            _ID + " DESC",
            null
        );

        return fetchMenu(cursor).get(0);
    }

    public ArrayList<Menu> fetchAllMenus() {
        return this.fetchAllMenus(null);
    }

    public ArrayList<Menu> fetchAllMenus(String search) {
        String selection = null;

        if (search != null) {
            selection = NAME + " LIKE '%" + search + "%' OR " + DESCRIPTION + " LIKE '%" + search + "%'";
        }

        Cursor cursor = database.query(
                MENU_TABLE,
                null,
                selection,
                null,
                null,
                null,
                _ID + " DESC",
                null
        );

        return fetchMenu(cursor);
    }

    public ArrayList<Menu> fetchMenu(Cursor cursor) {
        ArrayList<Menu> result = new ArrayList<Menu>();
        Menu menu;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                menu = new Menu(
                        cursor.getInt(cursor.getColumnIndexOrThrow(_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(RESTAURANT_ID))
                );

                result.add(menu);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        return result;
    }

    public long insert(Menu menu) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(RESTAURANT_ID, menu.getRestaurantId());
        initialValues.put(NAME, menu.getMenuName());
        initialValues.put(DESCRIPTION, menu.getMenuDescription());
        initialValues.put(PRICE, menu.getMenuPrice());

        return database.insert(MENU_TABLE, null, initialValues);
    }

    public int update(Menu menu) {
        ContentValues args = new ContentValues();
        args.put(RESTAURANT_ID, menu.getRestaurantId());
        args.put(NAME, menu.getMenuName());
        args.put(DESCRIPTION, menu.getMenuDescription());
        args.put(PRICE, menu.getMenuPrice());

        return database.update(
                MENU_TABLE,
                args,
                _ID + " = '" + menu.getId() + "'",
                null
        );
    }


    public int delete(int id) {
        return database.delete(MENU_TABLE, _ID + " = '" + id + "'", null);
    }
}
