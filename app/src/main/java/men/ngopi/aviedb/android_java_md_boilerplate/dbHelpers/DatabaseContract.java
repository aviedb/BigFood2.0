package men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers;

import android.provider.BaseColumns;

public class DatabaseContract {
    static final String RESTAURANT_TABLE = "restaurant_table";
    static final String MENU_TABLE = "menu_table";

    static final class RestaurantColumns implements BaseColumns {
        static final String NAME = "name";
        static final String ADDRESS = "address";
    }

    static final class MenuColumns implements BaseColumns {
        static final String NAME = "name";
        static final String DESCRIPTION = "description";
        static final String PRICE = "price";
        static final String RESTAURANT_ID = "restaurant_id";
    }
}
