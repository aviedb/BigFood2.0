package men.ngopi.aviedb.android_java_md_boilerplate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import men.ngopi.aviedb.android_java_md_boilerplate.dbHelpers.TableRestaurantHelper;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    private TableRestaurantHelper restaurantHelper;

    private RecyclerView mRestaurantRecycler;
    private RecyclerView.Adapter mRestaurantAdapter;
    private RecyclerView.LayoutManager mRestaurantLayout;
    private TextInputEditText search;

    private MaterialButton buttonthatgoestousersactivity;

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

        buttonthatgoestousersactivity = findViewById(R.id.goes_to_users);
        buttonthatgoestousersactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, UserActivity.class);
                startActivity(i);
            }
        });

        AndroidNetworking.initialize(getApplicationContext());

        AndroidNetworking.get("http://192.168.43.62:3000/")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonobject = response.getJSONObject(i);
                                Restaurant restaurant = new Restaurant(
                                        jsonobject.getInt("id"),
                                        jsonobject.getString("restaurant"),
                                        jsonobject.getString("alamat")
                                );

                                Log.d("tttt", "onResponse: " + jsonobject.getString("restaurant"));
                                restaurants.add(restaurant);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        mRestaurantAdapter = new RestaurantAdapter(restaurants);
                        mRestaurantRecycler.setAdapter(mRestaurantAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("tttt", "onError: " + anError);
                    }
                });
    }

    public void search() {
        searchQuery = search.getText().toString();
        ArrayList<Restaurant> filtered = new ArrayList<>();

        for (int i = 0; i < this.restaurants.size(); i++) {
            if (this.restaurants.get(i).getRestaurantName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filtered.add(this.restaurants.get(i));
            }
        }

        mRestaurantAdapter = new RestaurantAdapter(filtered);
        mRestaurantRecycler.setAdapter(mRestaurantAdapter);
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

//        this.fetchAllRestaurants();
    }
}
