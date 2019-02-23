package men.ngopi.aviedb.android_java_md_boilerplate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.ArrayList;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableRestaurantHelper;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    private TableRestaurantHelper restaurantHelper;

    private RecyclerView mRestaurantRecycler;
    private RecyclerView.Adapter mRestaurantAdapter;
    private RecyclerView.LayoutManager mRestaurantLayout;
    private MaterialButton mAddRestaurant;
    private TextInputEditText search;

    private String searchQuery = "";
    private ArrayList<Restaurant> restaurants = new ArrayList<>();

    public static MainActivity getInstance() {
        return instance;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
        restaurantHelper = new TableRestaurantHelper();

        mRestaurantRecycler = findViewById(R.id.restaurant_recycler);
        mRestaurantLayout = new LinearLayoutManager(this);
        mRestaurantRecycler.setLayoutManager(mRestaurantLayout);

        search = findViewById(R.id.search_restaurant);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search();
                }
                return false;
            }
        });
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (search.getRight() - search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        search();

                        return true;
                    }
                }
                return false;
            }
        });

        mAddRestaurant = findViewById(R.id.btn_add_restaurant);
        mAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddRestaurant.class);
                startActivity(i);
            }
        });
    }

    public void search() {
        searchQuery = search.getText().toString();
        fetchAllRestaurants();
    }

    public void fetchAllRestaurants() {
        restaurantHelper.open();
        this.restaurants = restaurantHelper.fetchAllRestaurants(searchQuery);

        this.mRestaurantAdapter = new RestaurantAdapter(this.restaurants);
        mRestaurantRecycler.setAdapter(this.mRestaurantAdapter);
        restaurantHelper.close();
    }


    @Override
    protected void onStart() {
        super.onStart();

        this.fetchAllRestaurants();
    }
}
