package men.ngopi.aviedb.android_java_md_boilerplate;

import java.util.ArrayList;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableMenuHelper;

public class Restaurant {
    private int id;
    private String restaurantName;
    private String restaurantAddress;
    private ArrayList<Menu> menus;

    public Restaurant(String name, String address) {
        this.restaurantName = name;
        this.restaurantAddress = address;
    }

    public Restaurant(int id, String name, String address) {
        this.id = id;
        this.restaurantName = name;
        this.restaurantAddress = address;
    }

    public int getId() {
        return this.id;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    public String getRestaurantAddress() {
        return this.restaurantAddress;
    }

    public ArrayList<Menu> fetchMenus(String searchQuery) {
        return this.menus = TableMenuHelper.getInstance().fetchMenusByRestaurantId(id, searchQuery);
    }

    public ArrayList<Menu> fetchMenus() {
        return this.menus = TableMenuHelper.getInstance().fetchMenusByRestaurantId(id, "");
    }
}
