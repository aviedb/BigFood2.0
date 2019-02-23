package men.ngopi.aviedb.android_java_md_boilerplate;

public class Menu {
    private int id;
    private String menuName;
    private String menuDescription;
    private String menuPrice;
    private int restaurantId;

    public Menu(String name, String desc, String price, int restaurantId) {
        this.menuName = name;
        this.menuDescription = desc;
        this.menuPrice = price;
        this.restaurantId = restaurantId;
    }

    public Menu(int id, String name, String desc, String price, int restaurantId) {
        this.id = id;
        this.menuName = name;
        this.menuDescription = desc;
        this.menuPrice = price;
        this.restaurantId = restaurantId;
    }

    public int getId() {
        return this.id;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public String getMenuDescription() {
        return this.menuDescription;
    }

    public String getMenuPrice() {
        return this.menuPrice;
    }

    public int getRestaurantId() {
        return this.restaurantId;
    }
}
